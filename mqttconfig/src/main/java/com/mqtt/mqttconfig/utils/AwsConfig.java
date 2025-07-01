package com.mqtt.mqttconfig.utils;

import org.springframework.context.annotation.Bean;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.iot.AWSIot;
import com.amazonaws.services.iot.AWSIotClientBuilder;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsConfig {
    @Bean
    public AWSIot getIotClient(){
        return AWSIotClientBuilder.standard()
                // use the v1 provider:
                .withCredentials(new com.amazonaws.auth.EnvironmentVariableCredentialsProvider())
                .withRegion(Regions.US_EAST_2)
                .build();
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.US_EAST_2)
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build();
    }

}
