package com.mqtt.mqttconfig.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DisplayMessage {

    @GetMapping("/hello")
    public String home() {
        return "how are you";  // This should match the name of the .html file in 'templates'
    }
}
