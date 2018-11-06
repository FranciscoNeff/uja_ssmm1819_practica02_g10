package com.nef.corgi.apppowercorpore;

import java.util.Date;

public class userDTO {
    private String user_name;
    private String email_user;
    private String pass;
    private String sid;
    private Date expires;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    private String dominio;
    private int puerto;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public userDTO(String user_name, String email_user, String pass) {
        this.user_name = user_name;
        this.email_user = email_user;
        this.pass = pass;
    }
public userDTO(String user_name){this.user_name = user_name;}
    public userDTO() {
        this.user_name = "name";
        this.email_user = "email";
        this.pass = "1234";
        // como tanto el dominio como el puerto permanecera invisibles al usuario estos se pasara con un valor predifinido al usuario
        dominio ="dominio";
        puerto=00;
    }

    public void setExpires(String expires) {
    }
}


