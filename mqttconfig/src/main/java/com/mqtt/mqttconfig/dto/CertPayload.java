package com.mqtt.mqttconfig.dto;

import lombok.Getter;
import lombok.Setter;

public class CertPayload {
    @Getter
    @Setter
    private String certType;
    @Getter
    @Setter
    private String filename;
    @Getter
    @Setter
    private String content;

}
