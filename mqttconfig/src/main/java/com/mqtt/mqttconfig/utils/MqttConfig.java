package com.mqtt.mqttconfig.utils;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mqtt.mqttconfig.dto.CertPayload;
import com.mqtt.mqttconfig.dto.MyMessage;
import com.mqtt.mqttconfig.dto.CertPayload;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
public class MqttConfig {

    String clientEndpoint = "a3v9kg7srit2ky-ats.iot.us-east-2.amazonaws.com";   // use value returned by describe-endpoint --endpoint-type "iot:Data-ATS"
   //thing name
    String clientId = "test_java";
    String awsAccessKeyId="AKIAX4NVY3N5ICE3ZGW7";
    String awsSecretAccessKey="ezkXIZNZ0G0enLoKE2t8L4vzOSSHh/gQz8yd+KMG";
    AWSIotMqttClient client = null;
    // replace with your own client ID. Use unique client IDs for concurrent connections.
    public void connectToIot() throws AWSIotException {
         client = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey, null);
        client.connect();
        System.out.println("connected to IOT");
    }
    public void publish(CertPayload payload) throws AWSIotException, JsonProcessingException {
        String topic = "test_java";
        AWSIotQos qos = AWSIotQos.QOS0;
        long timeout = 3000;                    // milliseconds

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(payload);
        MyMessage message = new MyMessage(topic, qos, jsonPayload);
        client.publish(message, timeout);
    }
}
