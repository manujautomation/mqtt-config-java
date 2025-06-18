package com.mqtt.mqttconfig.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mqtt.mqttconfig.models.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
public class FileUploadController {

    @GetMapping("/download-json")
    public ResponseEntity<byte[]> downloadJson() throws Exception {
        List<User> users = List.of(
                new User("Alice", 30),
                new User("Bob", 25)
        );

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] jsonData = objectMapper.writeValueAsBytes(users);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "users.json");

        return ResponseEntity.ok()
                .headers(headers)
                .body(jsonData);
    }

        @PostMapping("/upload-multiple")
        public ResponseEntity<byte[]> handleFileUpload(@RequestParam("deviceid") MultipartFile device_id,
                                                       @RequestParam("ca_cert") MultipartFile ca_cert_file,
                                                       @RequestParam("client_cert") MultipartFile client_cert_file,
                                                       @RequestParam("client_key") MultipartFile client_key_file) throws IOException {
            List<Channel> channels = new ArrayList<>();

            BufferedReader br = new BufferedReader(new InputStreamReader(device_id.getInputStream()));

                String deviceID = br.readLine();   // Line 1
                String broker_url = br.readLine();  // Line 2

                String ca_cert = new String(ca_cert_file.getBytes(), StandardCharsets.UTF_8).replaceAll("[\\r\\n]+", "");
                String client_cert = new String(client_cert_file.getBytes(), StandardCharsets.UTF_8).replaceAll("[\\r\\n]+", "");
                String client_key = new String(client_key_file.getBytes(), StandardCharsets.UTF_8).replaceAll("[\\r\\n]+", "");


                Channel channel = new Channel(
                        "Amazon Web Services (AWS)",       // broker_id
                        false,                            // is_jwt_auth
                        "927117940591",                   // project_id
                        deviceID,                   // client_id
                        broker_url,  // broker_url
                        "ssil/bbu",                       // topic_root
                        deviceID,                   // device_id
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



    private String cleanFileContent(MultipartFile file) throws IOException, IOException {
        // Read file as string and remove all newline characters
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        return content.replaceAll("[\\r\\n]+", "");
    }
}

