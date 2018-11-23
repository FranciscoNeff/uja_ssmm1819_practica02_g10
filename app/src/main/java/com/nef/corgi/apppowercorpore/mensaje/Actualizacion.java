package com.nef.corgi.apppowercorpore.mensaje;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nef.corgi.apppowercorpore.DTO.monitorDTO;
import com.nef.corgi.apppowercorpore.DTO.rutinaDTO;
import com.nef.corgi.apppowercorpore.DTO.userDTO;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Actualizacion {

    protected String user=null;
    protected String monitor=null;

    SimpleDateFormat FORMATOFECHA = new SimpleDateFormat("DD/MM/AAAA");

    Calendar c= new GregorianCalendar();

    public String Actualizacion(String csvuser, String csvmonitor) throws IOException{
        this.user= csvuser;
        this.monitor=csvmonitor;
        String actualizacion;

        try {
            ReadCSV csvreader = new ReadCSV();
            List<rutinaDTO> rutinalistcsv = csvreader.readRutinacsv();
            if(rutinalistcsv.isEmpty()) { actualizacion=null;
            }
            else {
                actualizacion = user+ ";" + FORMATOFECHA.format(c.getTime()) + 00001010+monitor;
                for (rutinaDTO rutinaeach : rutinalistcsv) {
                    actualizacion = rutinaeach.csvtoString();
                }//mensaje de las rutinas
            }
        }catch (IOException e)
        {
            actualizacion=null;
            e.printStackTrace();
        }
        return actualizacion;
    }
    public Actualizacion(){}
//Metodo de reserva por si en algun futuro se decide cambiar el formato del csv
    public String update(userDTO csvuser, monitorDTO csvmonitor )throws IOException {
        String actualizacion=null;
        rutinaDTO csvrutina= new rutinaDTO();
        try {
            ReadCSV csvreader = new ReadCSV();
            List<rutinaDTO> rutinalistcsv = csvreader.readRutinacsv();
            if(rutinalistcsv.isEmpty()) { actualizacion=null;
            }
            else {

                actualizacion = csvuser.csvtoString() + ";" + FORMATOFECHA.format(c.getTime()) + 00001010;//mensaje del usuario
                actualizacion = actualizacion + csvmonitor.csvtoString();//mensaje del monitor
                for (rutinaDTO rutinaeach : rutinalistcsv) {
                    actualizacion = rutinaeach.csvtoString();
                }//mensaje de las rutinas
            }
        }catch (IOException e)
        {
            actualizacion=null;
           e.printStackTrace();
        }
        return actualizacion;
    }
}


