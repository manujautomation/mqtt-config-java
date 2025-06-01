package com.mqtt.mqttconfig.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                   // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor      // Generates a no-args constructor
@AllArgsConstructor
public class Subset {
    private String name;
    private Fields fields;
}
