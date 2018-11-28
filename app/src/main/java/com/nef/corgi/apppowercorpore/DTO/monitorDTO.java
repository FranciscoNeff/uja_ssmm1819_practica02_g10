package com.nef.corgi.apppowercorpore.DTO;

public class MonitorDTO {
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

    public MonitorDTO(String nameM, String emailM) {
        this.nameM = nameM;
        this.emailM = emailM;
    }
public MonitorDTO(){}
    public String csvtoString() {
        return  nameM +";"+ emailM +00001010 ;
    }
}
