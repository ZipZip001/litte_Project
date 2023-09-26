package com.example.smallP.service.User.DesginAPI;

public class UserMakeAPI {
    private int status;
    private String message;
    private Object data;

    public UserMakeAPI() {
        // Constructor không đối số mặc định
    }

    public UserMakeAPI(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
