package com.nef.corgi.apppowercorpore.mensaje;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nef.corgi.apppowercorpore.DTO.monitorDTO;
import com.nef.corgi.apppowercorpore.DTO.userDTO;

import java.io.IOException;

public class Envio extends AsyncTask<String, Integer, Boolean> {
    userDTO user = null;
    monitorDTO monitor = null;
    private ProgressBar progressBar=null;
    public Envio(userDTO u, monitorDTO mon) {
        user = u;
        monitor = mon;
    }
@Override
protected Boolean doInBackground(String... strings) {
    String csvuser=user.csvtoString();
    String csvmonitor=monitor.csvtoString();
        Boolean estado=null;
        Actualizacion mensj = new Actualizacion();
        try {
        String datos = mensj.Actualizacion(csvuser, csvmonitor);
        if (datos == null) {
        estado=false;
        }else{
        if(!isCancelled()) {
        int[] j = {};
        int h=10;
        for (int i = 0; i < h; i++) {
        publishProgress((int)(i)/h);

        }
//                            este bucle es solo de prueba para mostrar el dialogo de progres
//                            en el futuro se realizara con un publish de la actualizacion
//                                    de las lineas de rutina q va leyendo
        }else{
        estado=true;}
        }
        /*
         * Habria que añadir un enviar al servidor o bien al mail permitente del monitor
         * datos = a formato mensaje en csv a mandar
         * */
        } catch (IOException e) {
        e.printStackTrace();
        estado=false;
        }

        return estado;
        }
//realizar un onpreexcute
protected void onProgressUpdate(Integer... values){
        progressBar.setProgress(values[0]);
        progressBar.postInvalidate();
        super.onProgressUpdate(values);
        //http://www.sgoliver.net/blog/tareas-en-segundo-plano-en-android-i-thread-y-asynctask/
        }
protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (result) {
        Toast.makeText(null, "Actualizacion Correcta", Toast.LENGTH_LONG).show();
        }
        else{
        Toast.makeText(null, "Actualizacion Fallida", Toast.LENGTH_LONG).show();
        }

        }
}
