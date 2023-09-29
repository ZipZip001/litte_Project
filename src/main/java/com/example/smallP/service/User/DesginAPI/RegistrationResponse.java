package com.example.smallP.service.User.DesginAPI;

import com.example.smallP.entity.User;

public class RegistrationResponse {
    private String message;
    private User user;

    public RegistrationResponse(){

    }
    public RegistrationResponse(String message, User user) {
        this.message = message;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
