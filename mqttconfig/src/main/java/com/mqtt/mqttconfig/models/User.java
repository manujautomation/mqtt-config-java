package com.mqtt.mqttconfig.models;

public class User {
    private String name;
    private int age;

    // Constructors
    public User() {}
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
