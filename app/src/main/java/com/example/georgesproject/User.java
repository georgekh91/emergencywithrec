package com.example.georgesproject;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    private String userName;
    private String userEmail;
    private String userPassword;

    public User(String name, String userEmail, String password) {
        this.userName = name;
        this.userEmail = userEmail;
        this.userPassword = password;
    }
    public User(){}
    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String password) {
        this.userPassword = password;
    }

    @Override
    public String toString() {
        return "Startup{" +
                "name='" + userName + '\'' +
                ", statusQuo='" + userEmail + '\'' +
                ", hasSolution=" + userPassword +
                '}';
    }
}

