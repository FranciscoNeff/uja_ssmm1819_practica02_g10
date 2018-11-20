package com.nef.corgi.apppowercorpore.mensaje;

import com.nef.corgi.apppowercorpore.DTO.monitorDTO;
import com.nef.corgi.apppowercorpore.DTO.rutinaDTO;
import com.nef.corgi.apppowercorpore.DTO.userDTO;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Actualizacion {
    protected String user=null;
    protected String monitor=null;


    SimpleDateFormat FORMATOFECHA = new SimpleDateFormat("DD/MM/AAAA");

    Calendar c= new GregorianCalendar();

    public Actualizacion(String user, String monitor){
        this.user= user;
        this.monitor=monitor;

    }

    public String update(userDTO csvuser, monitorDTO csvmonitor, List<rutinaDTO> rutinalistcsv){
        String actualizacion;
        rutinaDTO csvrutina= new rutinaDTO();


        actualizacion=csvuser.getUser_name()+";"+csvuser.getEmail_user()+";"+FORMATOFECHA.format(c.getTime())+00001010;//cabecera de usuario//salto de linea utf-8
        actualizacion= actualizacion+csvmonitor.getNameM()+";"+csvmonitor.getEmailM()+00001010;//cabecera de monitor
        csvrutina.setDiaRutina(rutinalistcsv.get(0).getDiaRutina());
        actualizacion=actualizacion+csvrutina.getDiaRutina()+00001010;//inicio de rutina con el dia

        csvrutina.setSerie(rutinalistcsv.get(0).getSerie());
        for (int z = 0;z<rutinalistcsv.size();z++) {
            csvrutina=rutinalistcsv.get(z);
            actualizacion = actualizacion + csvrutina.getNombreEjercicio()[0];//revisar esto del 0 xq alomejor no es el primero
                for (int j = 0; j < (csvrutina.getSerie().length); j++) {
                    actualizacion = actualizacion + ";" + j + ";" + csvrutina.getRepeticiones()[j];
                }//paso intermedio para serie y repeticiones
                actualizacion = actualizacion + 00001010;
        }//paso intermedio para los ejercicios
        actualizacion=actualizacion+";"+csvrutina.getTiempo()+00001010;//tiempo y final del mensaje
        return actualizacion;
    }


}
