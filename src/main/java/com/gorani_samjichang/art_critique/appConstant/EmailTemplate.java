package com.gorani_samjichang.art_critique.appConstant;

public enum EmailTemplate {
    WELCOME("Verifying Code Email For Art Critique",
            "<!DOCTYPE html>" +
                    "<html lang=\"en\">" +
                    "<head>" +
                    "    <meta charset=\"UTF-8\">" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                    "    <title>Verification Email</title>" +
                    "</head>" +
                    "<body>" +
                    "    <table width=\"100%%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color: #f4f4f4; padding: 20px;\">" +
                    "        <tr>" +
                    "            <td align=\"center\">" +
                    "                <table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color: #ffffff; padding: 20px;\">" +
                    "                    <tr>" +
                    "                        <td align=\"center\" style=\"padding: 10px 0;\">" +
                    "                            <h1 style=\"margin: 0; font-size: 24px;\">Verification Email</h1>" +
                    "                        </td>" +
                    "                    </tr>" +
                    "                    <tr>" +
                    "                        <td align=\"center\" style=\"padding: 20px 0;\">" +
                    "                            <p style=\"margin: 0; font-size: 16px;\">Hello,</p>" +
                    "                            <p style=\"margin: 0; font-size: 16px;\">Please use the verification code below to complete your email verification.</p>" +
                    "                        </td>" +
                    "                    </tr>" +
                    "                    <tr>" +
                    "                        <td align=\"center\" style=\"padding: 20px 0;\">" +
                    "                            <span style=\"font-size: 24px; font-weight: bold; color: #ffffff; background-color: #007bff; padding: 10px 20px; display: inline-block; border-radius: 4px;\">%s</span>" +
                    "                        </td>" +
                    "                    </tr>" +
                    "                    <tr>" +
                    "                        <td align=\"center\" style=\"padding: 20px 0;\">" +
                    "                            <p style=\"margin: 0; font-size: 16px;\">Thank you.<br>Art Critique</p>" +
                    "                        </td>" +
                    "                    </tr>" +
                    "                </table>" +
                    "            </td>" +
                    "        </tr>" +
                    "    </table>" +
                    "</body>" +
                    "</html>");

    private final String subject;
    private final String template;


    EmailTemplate(String subject, String template) {
        this.subject=subject;
        this.template = template;
    }

    public String getTemplate() {
        return this.template;
    }
    public String getSubject(){
        return this.subject;
    }
}
