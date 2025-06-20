package com.mqtt.mqttconfig.controller;
import com.amazonaws.services.iot.client.AWSIotException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mqtt.mqttconfig.dto.CertPayload;
import com.mqtt.mqttconfig.service.MqttPubSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class mqttController {

    @Autowired
    MqttPubSubService service;

    @PostMapping("/publish")
    public String publishMessages(@RequestBody CertPayload payload) throws AWSIotException, JsonProcessingException {
        service.publishMessage(payload);
        return "message published";
     }
}
