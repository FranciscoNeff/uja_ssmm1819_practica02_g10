package com.nef.corgi.apppowercorpore.mensaje;

import android.os.AsyncTask;
import android.widget.Toast;

import com.nef.corgi.apppowercorpore.DTO.monitorDTO;
import com.nef.corgi.apppowercorpore.DTO.rutinaDTO;
import com.nef.corgi.apppowercorpore.DTO.userDTO;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Actualizacion extends AsyncTask<Void, String, Boolean> {
    protected String user=null;
    protected String monitor=null;


    SimpleDateFormat FORMATOFECHA = new SimpleDateFormat("DD/MM/AAAA");

    Calendar c= new GregorianCalendar();

    public Actualizacion(String user, String monitor){
        this.user= user;
        this.monitor=monitor;

    }
    public Actualizacion(){}

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



               /* actualizacion = csvuser.getUser_name() + ";" + csvuser.getEmail_user() + ";" + FORMATOFECHA.format(c.getTime()) + 00001010;//cabecera de usuario//salto de linea utf-8
                actualizacion = actualizacion + csvmonitor.getNameM() + ";" + csvmonitor.getEmailM() + 00001010;//cabecera de monitor
                csvrutina.setDiaRutina(rutinalistcsv.get(0).getDiaRutina());//lo q creo q no esta bien es lo de lo "0" para mejorar deberia obtener todas las fechas,
                // comparar desde la ultima actualizacion y solo recorrer las mayores a esta fecha*/
                /*actualizacion = actualizacion + FORMATOFECHA.format(csvrutina.getDiaRutina()) + 00001010;//inicio de rutina con el dia
                csvrutina.setSerie(rutinalistcsv.get(0).getSerie());
                for (int z = 0; z < rutinalistcsv.size(); z++) {
                    csvrutina = rutinalistcsv.get(z);
                    actualizacion = actualizacion + csvrutina.getNombreEjercicio()[0];//revisar esto del 0 xq alomejor no es el primero
                    for (int j = 0; j < (csvrutina.getSerie().length); j++) {
                        actualizacion = actualizacion + ";" + j + ";" + csvrutina.getRepeticiones()[j];
                    }//paso intermedio para serie y repeticiones
                    actualizacion = actualizacion + 00001010;
                }//paso intermedio para los ejercicios
                actualizacion = actualizacion + ";" + csvrutina.getTiempo() + 00001010;}//tiempo y final del mensaje*/


        }catch (IOException e)
        {
            actualizacion=null;
           e.printStackTrace();
        }
        return actualizacion;
    }
    protected Boolean doInBackground(Void... params){
        Boolean tarea=null;
        return tarea;
    }
    protected String onProgressUpdate(Void... values){
        String progreso=null;
        return progreso;
    //http://www.sgoliver.net/blog/tareas-en-segundo-plano-en-android-i-thread-y-asynctask/
    }
    protected void onPostExecute(Boolean result) {
        if (result) {
         // Toast.makeText(this, "Actualizacion Correcta", Toast.LENGTH_LONG).show();
        }
    }
}


