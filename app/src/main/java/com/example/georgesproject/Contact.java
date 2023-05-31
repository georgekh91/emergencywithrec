package com.example.georgesproject;

public class Contact {

    private String key;
    private String name;
    private String email;

    private Long lastSentEmailTime;

    public Contact() {}

    public Contact(String key, String name, String email) {
        this.key = key;
        this.name = name;
        this.email = email;
    }

    public static Contact get(int position) {
        return null;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getKey() {
        return key;
    }

    public Long getLastSentEmailTime() {
        return lastSentEmailTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setLastSentEmailTime(Long lastSentEmailTime) {
        this.lastSentEmailTime = lastSentEmailTime;
    }
}