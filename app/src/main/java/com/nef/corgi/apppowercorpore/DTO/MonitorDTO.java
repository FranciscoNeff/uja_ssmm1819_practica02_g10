package com.nef.corgi.apppowercorpore.DTO;

public class MonitorDTO {
    String nameM;
    String emailM;
    private static final String DL =";";
    private static final int FIN = 00001010 ;
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
        return  nameM +DL+ emailM +FIN ;
    }
}
