package com.gorani_samjichang.art_critique.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.sts.StsClient;
import software.amazon.awssdk.services.sts.model.AssumeRoleRequest;
import software.amazon.awssdk.services.sts.model.AssumeRoleResponse;
import software.amazon.awssdk.services.sts.model.Credentials;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class ImageUploadService {

    @Value("${aws.region}")
    private String region;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.sts.role-arn}")
    private String roleArn;

    @Value("${aws.sts.session-name}")
    private String sessionName;

    @Value("${aws.accessKeyId}")
    private String accessKeyId;
    @Value("${aws.secretAccessKey}")
    private String secretAccessKey;

    public StsClient getStsClient() {
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(accessKeyId, secretAccessKey);
        return StsClient.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();
    }

    public Credentials getAssumeRoleCredentials() {
        AssumeRoleRequest roleRequest = AssumeRoleRequest.builder()
                .roleArn(roleArn)
                .roleSessionName(sessionName)
                .build();
        AssumeRoleResponse roleResponse = getStsClient().assumeRole(roleRequest);
        Credentials s3TempCredential = roleResponse.credentials();
        return s3TempCredential;
    }

    public String uploadImage(MultipartFile file, String filename) throws IOException {
        Credentials credentials = getAssumeRoleCredentials();
        AwsSessionCredentials awsCredentials = AwsSessionCredentials.create(credentials.accessKeyId(), credentials.secretAccessKey(), credentials.sessionToken());
        S3Client s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();

        Path tempFile = Files.createTempFile("upload-", filename);
        file.transferTo(tempFile.toFile());

        // S3에 파일 업로드
        String keyName = "images/" + filename;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(keyName)
                .build();

        s3Client.putObject(putObjectRequest, tempFile);

        // 임시 파일 삭제
        Files.delete(tempFile);

        // 업로드된 파일의 S3 경로 반환
        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + keyName;
    }
}
