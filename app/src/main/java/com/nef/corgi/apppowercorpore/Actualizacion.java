package com.nef.corgi.apppowercorpore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

public class Actualizacion {
    SimpleDateFormat FORMATOFECHA = new SimpleDateFormat("DD/MM/AAAA");
    private userDTO csvuser;
    private monitorDTO csvmonitor;
    private rutinaDTO csvrutina;
    Calendar c= new GregorianCalendar();

    public String update(userDTO csvuser,monitorDTO csvmonitor, rutinaDTO csvrutina){
        String actualizacion;
        actualizacion=csvuser.getUser_name()+";"+csvuser.getEmail_user()+";"+FORMATOFECHA.format(c.getTime())+00001010;//cabecera de usuario//salto de linea utf-8
        actualizacion= actualizacion+csvmonitor.getNameM()+";"+csvmonitor.getEmailM()+00001010;//cabecera de monitor
        actualizacion=actualizacion+csvrutina.getDiaRutina()+00001010;//inicio de rutina con el dia
        for (int i=0;i<csvrutina.getSerie().length;i++) {
            actualizacion=actualizacion+csvrutina.getNombreEjercicio()[i];
            for (int j = 0; j < (csvrutina.getNombreEjercicio().length); j++) {
                actualizacion = actualizacion + ";" + j + ";" + csvrutina.getRepeticiones()[j];
            }//paso intermedio para serie y repeticiones
            actualizacion=actualizacion+00001010;
        }//paso intermedio para los ejercicios
        actualizacion=actualizacion+";"+csvrutina.getTiempo()+00001010;//tiempo y final del mensaje
        return actualizacion;
    }

}
