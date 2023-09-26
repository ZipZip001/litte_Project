package com.example.smallP.service.User.DesginAPI;

public class UserData {
    private String email;
    private String phone;
    private String fullName;
    private String role;
    private String avatar;
    private int id;

    public UserData (){

    }

    public UserData(String email, String phone, String fullName, String role, String avatar) {
        this.email = email;
        this.phone = phone;
        this.fullName = fullName;
        this.role = role;
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
