package com.mqtt.mqttconfig.service;

import com.amazonaws.services.iot.client.AWSIotException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mqtt.mqttconfig.dto.CertPayload;
import com.mqtt.mqttconfig.utils.MqttConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttPubSubService {
    @Autowired
    MqttConfig mqttConfig;

    public void publishMessage(CertPayload payload) throws AWSIotException, JsonProcessingException {
        mqttConfig.connectToIot();
        mqttConfig.publish(payload);
    }
}
