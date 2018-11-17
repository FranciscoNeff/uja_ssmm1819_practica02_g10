package com.nef.corgi.apppowercorpore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

public class actualizacion {
    SimpleDateFormat FORMATOFECHA = new SimpleDateFormat("DD/MM/AAAA");
    private userDTO csvuser;
    private monitorDTO csvmonitor;
    private rutinaDTO csvrutina;
    Calendar c= new GregorianCalendar();

    public String actualizacion(userDTO csvuser,monitorDTO csvmonitor, rutinaDTO csvrutina){
        String actualizacion;
        actualizacion=csvuser.getUser_name()+","+csvuser.getEmail_user()+","+FORMATOFECHA.format(c.getTime())+",";//cabecera de usuario
        actualizacion= actualizacion+csvmonitor.getNameM()+","+csvmonitor.getEmailM()+",";//cabecera de monitor
        actualizacion=actualizacion+csvrutina.getDiaRutina()+","+csvrutina.getNombreEjercicio();//inicio de rutina
        int[] series = csvrutina.getSerie();
        for (int i=0; i < (series.length); i++){
            actualizacion=actualizacion+","+i+","+ csvrutina.getRepeticiones()[i];
        }//paso intermedio para serie y repeticiones
        actualizacion=actualizacion+","+csvrutina.getTiempo()+";";//tiempo y final del mensaje
        return actualizacion;
    }

}
