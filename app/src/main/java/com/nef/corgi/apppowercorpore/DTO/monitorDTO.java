package com.nef.corgi.apppowercorpore.DTO;

public class monitorDTO {
    String nameM;
    String emailM;

    public String getNameM() {
        return nameM;
    }

    public void setNameM(String nameM) {
        this.nameM = nameM;
    }

    public String getEmailM() {
        return emailM;
    }

    public void setEmailM(String emailM) {
        this.emailM = emailM;
    }

    public monitorDTO(String nameM, String emailM) {
        this.nameM = nameM;
        this.emailM = emailM;
    }

    public String csvtoString() {
        return  nameM +";"+ emailM ;
    }
}
