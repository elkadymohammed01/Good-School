package com.example.anew;

public class User {
    private String UserName, Email,Password,phone;
 public User(){

 }

    public User(String userName, String email, String password, String phone) {
        UserName = userName;
        Email = email;
        Password = password;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public String getUserName() {
        return UserName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return Password;
    }
}
