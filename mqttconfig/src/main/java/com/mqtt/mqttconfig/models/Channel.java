package com.mqtt.mqttconfig.models;

import lombok.*;


@Data                   // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor      // Generates a no-args constructor
@AllArgsConstructor
public class Channel {

    private String broker_id;
    private boolean is_jwt_auth;
    private String project_id;
    private String client_id;
    private String broker_url;
    private String topic_root;
    private String device_id;
    private int connect_timeout;
    private int retry_interval;
    private int keep_alive_interval;
    private int reconnect_interval;
    private int max_reconnect_attempts;
    private String trust_store;
    private String key_store;
    private String private_key;

    public void setIs_jwt_auth(boolean isJwtAuth) {
        this.is_jwt_auth = isJwtAuth;
    }

    public boolean isIs_jwt_auth() {
        return is_jwt_auth;
    }
}
