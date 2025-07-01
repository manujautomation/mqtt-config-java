package com.mqtt.mqttconfig.controller;
import com.amazonaws.services.iot.client.AWSIotException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mqtt.mqttconfig.dto.CertPayload;

import com.mqtt.mqttconfig.service.CreateThingService;
import com.mqtt.mqttconfig.service.MqttPubSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
public class MQTTController {

    @Autowired
    MqttPubSubService service;

    @Autowired
    CreateThingService createThingService;

    @PostMapping("/publish")
    public String publishMessages(@RequestBody CertPayload payload) throws AWSIotException, JsonProcessingException {
        service.publishMessage(payload);
        return "message published";
     }

     @PostMapping("/register/{thingName}")
    public String CreateThing(@PathVariable String thingName){
        return createThingService.createThingAutomatically(thingName);
    }

    @PostMapping("/retrievemqttjsonfile/{thingName}")
    public ResponseEntity<byte[]> handleFileUpload(@PathVariable String thingName) throws IOException {
        return createThingService.retriveThingInfoFroms3(thingName);
    }
}
