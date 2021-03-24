package com.example.anew.data;

import java.util.ArrayList;

public class Server {
    public String name,information,email,view="0",type=null ,love="0",user;                                                                         ;

    public Server(String name, String information, String email,String type ) {
        this.name = name;
        this.information = information;
        this.email = email;
        this.type=type;
        love="0";
    }
    public Server(){}
}
