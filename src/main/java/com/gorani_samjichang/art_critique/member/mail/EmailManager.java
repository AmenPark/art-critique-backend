package com.gorani_samjichang.art_critique.member.mail;

import com.gorani_samjichang.art_critique.appConstant.EmailTemplate;
import com.gorani_samjichang.art_critique.common.CommonUtil;
import com.gorani_samjichang.art_critique.common.exceptions.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
public class EmailManager {

    private static final int CODE_LENGTH = 8;
    final CommonUtil commonUtil;
    final JavaMailSender mailSender;
    @Value("${jwt.secret}")
    String salt;

    public boolean validCode(String hashedBeforeString, String hashedString) {
        return hashedString.equals(createCode(hashedBeforeString+salt));
    }


    public String createCode(String hashedBeforeString) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(hashedBeforeString.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception e) {
            throw new MessagingException("Cannot Create Code");
        }
    }

    public String sendVerifyingMessage(String to) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        String hashedBeforeString = commonUtil.generateSecureRandomString(CODE_LENGTH);
        String hashedString = createCode(hashedBeforeString+salt);
        try {
            helper.setTo(to);
            helper.setText(String.format(EmailTemplate.WELCOME.getTemplate(), hashedBeforeString));
            helper.setSubject(EmailTemplate.WELCOME.getSubject());
            mailSender.send(message);
            return hashedString;
        } catch (Exception e) {
            throw new MessagingException(e.getMessage());
        }
    }
}