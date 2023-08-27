package com.example.smallP.entity;

import jakarta.persistence.*;

@Entity
@Table(name= "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name= "username")
    private String username;
    @Column(name= "email")
    private String email;
    @Column(name= "password")
    private String password;

    @Column(name= "is_admin")
    private Boolean is_admin;

    @Column(name= "phone")
    private String phone;

    public User(){

    }
    // define constructors

    public User(String username, String email, String password, Boolean is_admin, String phone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.is_admin = is_admin;
        this.phone = phone;
    }


    // define getter/setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(Boolean is_admin) {
        this.is_admin = is_admin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    // define toString

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", is_admin=" + is_admin +
                ", phone='" + phone + '\'' +
                '}';
    }
}
