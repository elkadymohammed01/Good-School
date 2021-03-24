package com.example.easyschool.data;

public class User {
    private String name ,email,password ,phone ,level;

    public User(String name, String email, String password, String phone, String level) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.level = level;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
