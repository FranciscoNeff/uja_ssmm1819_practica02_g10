package com.nef.corgi.apppowercorpore;

import java.util.Date;

public class userDTO {
    private String user_name;
    private String email_user;
    private String pass;
    private String sid;
    private Date expires;
    private String dominio;
    private int puerto;

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
    public userDTO (){}
    public userDTO(String user_name, String email_user, String pass) {
        this.user_name = "name";
        this.email_user = "email";
        this.pass = "12345";
        // como tanto el dominio como el puerto permanecera invisibles al usuario estos se pasara con un valor predifinido al usuario
        dominio ="labtelemaujaen.es";
        puerto=00;

    }

    public userDTO(String user_name, String email_user, String pass,String dominio, int puerto, Date expires,String sid) {
        this.user_name = user_name;
        this.email_user = email_user;
        this.pass = pass;
        this.dominio=dominio;//como el dominio va a ser transparente se deja predefinido
        this.puerto=puerto;
        this.expires=expires;
        this.sid=sid;
    }



    public void setExpires(String expires) {
    }


    public String csvtoString() {
        return  user_name +","+ email_user ;
    }
}


