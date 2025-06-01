package com.mqtt.mqttconfig.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data                   // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor      // Generates a no-args constructor
@AllArgsConstructor
public class Root {
    private int version;
    private List<Subset> subsets;
}
