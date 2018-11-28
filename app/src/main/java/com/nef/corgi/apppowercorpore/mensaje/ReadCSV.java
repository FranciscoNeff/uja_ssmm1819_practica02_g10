package com.nef.corgi.apppowercorpore.mensaje;

import android.content.Context;


import com.nef.corgi.apppowercorpore.DTO.RutinaDTO;

import com.nef.corgi.apppowercorpore.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
/***
 * Formato en el que se guardan las rutinas
 * Fecha en la que se realiza DD/MM/AAAA
 * Array de los ejercicios realizados en ese dia
 * series de los ejercicios
 * repeticiones de los ejercicios
 *
 */

//ejemplo de excepciones en java de clase 4
public class ReadCSV {
    public Context context;//esto sirve para usarlo en varios metodos
    public ReadCSV(Context c){
        context = c;
    }
    public ReadCSV(){}
    /*
    En este codigo desfragmentamos un formato csv que seria el correspondiente
    solo al body de las rutinas
    ejercicios;series;repetciones CRLF
     */
    public List<RutinaDTO> readRutinacsv () throws IOException {
        List<RutinaDTO> rutinalistcsv = new ArrayList<>();
        boolean exist=false;
        InputStream rutinacsv = context.getResources().openRawResource(R.raw.rutina_csv);
        BufferedReader reader = null;
            reader = new BufferedReader(new InputStreamReader(rutinacsv, Charset.forName("UTF-8")));
        if(reader!=null) {
            exist = true;
            String line = "";

            String[] ejercicios = {};//array con el nombre de los ejercicios
            int[] series = {};//array de series
            String[] repeticiones = {};//array de repeticiones
            //hacer un split por fechas//a la hora de guardar la rutina que se realiza, se guarda la fecha en la que se realiza la rutina
            /**/

            int i = 0;
            while ((line = reader.readLine()) != null) {

                line = reader.readLine();
                String s_f_rutina=line;
                //fecha
                try {
                    RutinaDTO rutina = new RutinaDTO();
                    String[] items = line.split(";");//La cadena se trocea con ;

                    while ((Integer.parseInt(items[i])) != 00001010) {
                        rutina = null;
                        //Lectura
                        ejercicios[i] = items[i];
                        i++;
                        do {
                            series[i] = (Integer.parseInt(items[i]));//leo la serie en la que esta, es un numero
                            i++;
                            repeticiones[i] = items[i];//esto es un string
                            i++;
                        }
                        while (Integer.parseInt(items[i]) != 00001010); //deberia trocear hasta terminar toda la serie y repeticiones
                        i++;
                        rutina.setNombreEjercicio(ejercicios);//introduce ejercicio
                        rutina.setSerie(series);//introduce el array de series
                        rutina.setRepeticiones(repeticiones);//introduce el array de repeticiones
                        rutinalistcsv.add(rutina);//introduce el objeto rutina a la lista
                    }
                } catch (NullPointerException e) {//Tengo que poner el nullPointerException ya que con las demas excepciones da fallo
                    // ya que debido al metodo pide devolver un objeto
                    e.printStackTrace();
                    rutinalistcsv.clear();
                }

            }
        }else
        {rutinalistcsv.clear();
        System.out.print("Archivo no encontrado");}
        if(exist!=true){rutinalistcsv.clear();}
        reader.close();
        return rutinalistcsv;
    }
}






