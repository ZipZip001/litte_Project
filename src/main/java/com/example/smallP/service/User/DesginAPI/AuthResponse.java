package com.example.smallP.service.User.DesginAPI;

public class AuthResponse {

    private Object data;

    public AuthResponse(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    private String access_token;
    private UserData user;

    public AuthResponse (){

    }

    public AuthResponse(String access_token, UserData user) {
        this.access_token = access_token;
        this.user = user;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }
}
