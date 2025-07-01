package com.mqtt.mqttconfig.service;

import com.amazonaws.services.iot.AWSIot;
import com.amazonaws.services.iot.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mqtt.mqttconfig.models.Channel;
import com.mqtt.mqttconfig.models.Fields;
import com.mqtt.mqttconfig.models.Root;
import com.mqtt.mqttconfig.models.Subset;
import com.mqtt.mqttconfig.utils.AwsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CreateThingService {


    private final AWSIot iotClient;

    @Autowired
    AwsConfig awsConfig;

    public CreateThingService(AWSIot iotClient) {
        this.iotClient = iotClient;
    }

    public String createThingAutomatically(String thingName) {
        //check if already thing exists with that name
        if(!describeThing(thingName)) {
            //create thing
            CreateThingResult response = awsConfig.getIotClient().
                    createThing(new CreateThingRequest().withThingName(thingName));
            CreateKeysAndCertificateRequest certRequest = new CreateKeysAndCertificateRequest()
                    .withSetAsActive(true);
            CreateKeysAndCertificateResult certResult = iotClient.createKeysAndCertificate(certRequest);

            String certificateArn = certResult.getCertificateArn();
            String certificateId = certResult.getCertificateId();
            attachCertificateToThing(thingName,certificateArn);

            System.out.println("Certificate ARN: " + certificateArn);
            System.out.println("Public Key:\n" + certResult.getKeyPair().getPublicKey());
            System.out.println("Private Key:\n" + certResult.getKeyPair().getPrivateKey());
            storeCertificatesToS3(thingName,certResult.getCertificatePem(),certResult.getKeyPair().getPublicKey(),
                    certResult.getKeyPair().getPrivateKey());
            return "thing created successfully";
        }
    //thing exists already
        return "thing already exists";
    }

    public void attachCertificateToThing(String thingName, String certificateArn) {
        AttachThingPrincipalRequest request = new AttachThingPrincipalRequest()
                .withThingName(thingName)
                .withPrincipal(certificateArn); // The certificate ARN

        iotClient.attachThingPrincipal(request);

        System.out.println("✅ Certificate attached to Thing: " + thingName);
    }
    public void storeCertificatesToS3(String thingName, String certPem, String publicKey, String privateKey) {
        String bucketName = "certsbucket19";
        uploadToS3(bucketName, thingName + "/certificate.pem", certPem);
        uploadToS3(bucketName, thingName + "/public.key", publicKey);
        uploadToS3(bucketName, thingName + "/private.key", privateKey);
    }

    private void uploadToS3(String bucket, String key, String content) {
        PutObjectRequest putReq = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        awsConfig.s3Client().putObject(putReq, RequestBody.fromString(content));
        System.out.println("✅ Uploaded to S3: " + key);
  }

    public ResponseEntity<byte[]> retriveThingInfoFroms3(String thingName) throws JsonProcessingException {
        String bucketName = "certsbucket19";
         Map<String, String> certData = new HashMap<>();
            certData.put("thingName", thingName);
            certData.put("certificate", getFileFromS3(bucketName, thingName + "/certificate.pem"));
            certData.put("publicKey", getFileFromS3(bucketName, thingName + "/public.key"));
            certData.put("privateKey", getFileFromS3(bucketName, thingName + "/private.key"));

            return  buildJson(certData);
    }

    public ResponseEntity<byte[]>  buildJson(Map<String, String> certData) throws JsonProcessingException {
        List<Channel> channels = new ArrayList<>();

        String ca_cert = certData.get("certificate").replaceAll("[\\r\\n]+", "");
        String client_cert = certData.get("publicKey").replaceAll("[\\r\\n]+", "");
        String client_key = certData.get("privateKey").replaceAll("[\\r\\n]+", "");


        Channel channel = new Channel(
                "Amazon Web Services (AWS)",       // broker_id
                false,                            // is_jwt_auth
                "927117940591",                   // project_id
                certData.get("thingName"),                   // client_id
                "",  // broker_url
                "ssil/bbu",                       // topic_root
                certData.get("thingName"),                   // device_id
                5,
                25,
                15,
                10,
                5,
                ca_cert,
                client_cert,
                client_key
        );

        channels.add(channel);


        Fields fields = new Fields(
                "100.100.100.100:8888",
                0,
                channels
        );

        Subset subset = new Subset("commqtt", fields);

        Root root = new Root(2, List.of(subset));

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] jsonData = objectMapper.writeValueAsBytes(root);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "mqtt.json");

        return ResponseEntity.ok()
                .headers(headers)
                .body(jsonData);
    }

    public String getFileFromS3(String bucketName, String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        try (ResponseInputStream<GetObjectResponse> s3Object = awsConfig.s3Client().getObject(getObjectRequest)) {
            return new String(s3Object.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read file from S3: " + key, e);
        }
    }

    public DescribeThingResult DescribeThingResponse(String thingName){
        DescribeThingRequest describeThingRequest = new DescribeThingRequest();
        describeThingRequest.setThingName(thingName);
        return awsConfig.getIotClient().describeThing(describeThingRequest);
    }

    private boolean describeThing(String thingName) {
        if(thingName == null){
            return false;
        }
        try{
            DescribeThingResponse(thingName);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
