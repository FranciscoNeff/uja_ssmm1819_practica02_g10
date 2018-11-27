package com.nef.corgi.apppowercorpore.mensaje;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nef.corgi.apppowercorpore.DTO.monitorDTO;
import com.nef.corgi.apppowercorpore.DTO.rutinaDTO;
import com.nef.corgi.apppowercorpore.DTO.userDTO;
import com.nef.corgi.apppowercorpore.ServiceActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Envio extends AsyncTask<String, Integer, Boolean> {
    public Context context;
    userDTO user = null;
    monitorDTO monitor = null;
    private ProgressBar progressBar=null;
    SimpleDateFormat FORMATOFECHA = new SimpleDateFormat("DD/MM/AAAA");
    public int progreso=0;
    Calendar c= new GregorianCalendar();
    public Envio(Context c,userDTO u, monitorDTO mon) {
        context=c;
        user = u;
        monitor = mon;
    }
    public Envio(userDTO u, monitorDTO mon){
        user = u;
        monitor = mon;
    }
@Override
protected Boolean doInBackground(String... strings) {
    String csvuser=user.csvtoString();
    String csvmonitor=monitor.csvtoString();
        Boolean estado=null;

    String actualizacion;
    try {
        ReadCSV csvreader = new ReadCSV();
        List<rutinaDTO> rutinalistcsv = csvreader.readRutinacsv();
        if(rutinalistcsv.isEmpty()) { estado=false;//si la array esta vacia no actualizamos
        }
        else {

            progreso=rutinalistcsv.size();
            actualizacion = csvuser+ ";" + FORMATOFECHA.format(c.getTime()) + 00001010+csvmonitor;//Union del mansaje de cabecera de user y monitor

            for (rutinaDTO rutinaeach : rutinalistcsv) {
                if(!isCancelled()) {
                    publishProgress((int)(rutinalistcsv.size()));
                }
                progreso++;

                actualizacion = rutinaeach.csvtoString();
            }//mensaje de las rutinas
            //capturar las excepciones de actulizaciones
            //excepcion una cabeceras una de usuario una de monitor mas rutinalist.size()
            estado = true;
        }
    }catch (IOException e)
    {
        actualizacion=null;
        e.printStackTrace();
        estado = false;
    }
        /*
         * Habria que añadir un enviar al servidor o bien al mail permitente del monitor
         * actualizacion = a formato mensaje en csv a mandar
         * */
        return estado;
        }

@Override
protected void onProgressUpdate(Integer... values){
        progressBar.setProgress(values[0]);
        progressBar.postInvalidate();
        super.onProgressUpdate(values);
        }
    protected void onPreExecute(){
        progressBar.setMax(progreso);//Hay q negociar el max
        progressBar.setProgress(0);
    }
        @Override
protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        if (result) {//Revisar el toast ya que no los puede lanzar desde aqui
        Toast.makeText(context, "Actualizacion Correcta", Toast.LENGTH_LONG).show();
        }
        else{
        Toast.makeText(context, "Actualizacion Fallida", Toast.LENGTH_LONG).show();
        }

        }
}
//Ayuda
//http://www.sgoliver.net/blog/tareas-en-segundo-plano-en-android-i-thread-y-asynctask/
