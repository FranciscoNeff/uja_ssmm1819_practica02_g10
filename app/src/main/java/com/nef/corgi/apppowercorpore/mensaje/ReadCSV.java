package com.nef.corgi.apppowercorpore.mensaje;

import android.content.Context;
import android.util.Log;

import com.nef.corgi.apppowercorpore.DTO.rutinaDTO;
import com.nef.corgi.apppowercorpore.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class ReadCSV {
    public Context context;//esto sirve para usarlo en varios metodos
    public ReadCSV(Context c){
        context = c;
    }
    public ReadCSV(){}

    public List<rutinaDTO> readRutincacsv (){
        List<rutinaDTO> rutinalistcsv = new ArrayList<>();
        InputStream rutinacsv = context.getResources().openRawResource(R.raw.rutina_csv);
        BufferedReader reader = new BufferedReader(new InputStreamReader(rutinacsv, Charset.forName("UTF-8")));
        String line="";
        //try {
        String[] ejercicios={};
        int i=0;
        rutinaDTO rutina = new rutinaDTO();
        String[] items=line.split(";");//La cadena se trocea con ;
        int[] series={};
        while ((Integer.parseInt(items[i]))!=00001010){
            //Lectura
            ejercicios[i]=items[i];
            i++;
            do{
                series[i]=(Integer.parseInt(items[i]));//revisar xq esto slo hay q hacerlo la primera linea
                i++;
            }while (Integer.parseInt(items[i])!=00001010);//deberia trocear todas las series //header de la tabla
            String[] repeticiones={};

            do {
                repeticiones[i]=items[i];
                i++;
            }while (Integer.parseInt(items[i])!=00001010);
            i++;
            rutina.setNombreEjercicio(ejercicios);//introduce ejercicio
            rutina.setSerie(series);//introduce series
            rutina.setRepeticiones(repeticiones);//introduce repeticiones
            rutinalistcsv.add(rutina);
        }

        /*}catch (IOException error){
            Log.wtf("MyActivity", "Error reading line: " +line, error);
            error.printStackTrace();
            line = "Excepción: " + error.getMessage();
            System.out.println(line);
        }finally {
            System.out.println("Imposible cargar la rutina");
        }*/
    return rutinalistcsv;
    }
}
