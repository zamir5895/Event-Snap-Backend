package com.backend.users.S3;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.apache.catalina.authenticator.BasicAuthenticator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
@Configuration
public class StorageConfig {
    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.credentials.sessionToken}")
    private String sessionToken;

    @Value("${cloud.aws.region.static:us-east-1}")
    private String region;

    @Bean
    public AmazonS3 amazonS3() {
        BasicSessionCredentials credentials = new BasicSessionCredentials(accessKey, secretKey, sessionToken);
        System.out.println("Access Key: " + accessKey);
        System.out.println("Secret Key: " + secretKey);
        System.out.println("Session Token: " + sessionToken);
        System.out.println("Region: " + region);
        System.out.println("credentials : " + credentials);


        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().
                withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
        try{
            System.out.println("Creating bucket");
            s3Client.listBuckets();
            System.out.println("Conexión a S3 exitosa.");
        }catch (AmazonS3Exception E){
            System.out.println("Caught AmazonS3Exception: " + E);
        }
        return s3Client;

    }
}
