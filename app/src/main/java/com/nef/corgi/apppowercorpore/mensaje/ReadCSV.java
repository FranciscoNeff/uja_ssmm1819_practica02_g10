package com.nef.corgi.apppowercorpore.mensaje;

import android.content.Context;
import android.util.Log;


import com.nef.corgi.apppowercorpore.DTO.rutinaDTO;
import com.nef.corgi.apppowercorpore.R;



import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    /*
    En este codigo desfragmentamos un formato csv que seria el correspondiente
    solo al body de las rutinas
    ejercicios;series;repetciones CRLF
     */
    public List<rutinaDTO> readRutinacsv () throws IOException {
        List<rutinaDTO> rutinalistcsv = new ArrayList<>();
        boolean exist=false;
        InputStream rutinacsv = context.getResources().openRawResource(R.raw.rutina_csv);
        BufferedReader reader = null;
            reader = new BufferedReader(new InputStreamReader(rutinacsv, Charset.forName("UTF-8")));
        if(reader!=null) {
            exist = true;
            String line = "";
            String[] repeticiones = {};
            int[] series = {};
            String[] ejercicios = {};

            int i = 0;
            while ((line = reader.readLine()) != null) {
                line = reader.readLine();
                try {
                    rutinaDTO rutina = new rutinaDTO();
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
                        rutina.setSerie(series);//introduce series
                        rutina.setRepeticiones(repeticiones);//introduce repeticiones
                        rutinalistcsv.add(rutina);//introduce el objeto rutina a la lista
                    }
                } catch (NullPointerException e) {//Tengo que poner el nullPointerException ya que con las demas excepciones da fallo
                    // ya que debido al metodo pide devolver un objeto
                    e.printStackTrace();
                    rutinalistcsv.clear();
                }

            /*}catch(FileNotFoundException e){
                    e.getMessage();

            } catch (IOException ioe) {
                ioe.printStackTrace();
                System.out.println("Error: Fallo en la lectura del fichero. ");
            }*/
            }
        }else
        {rutinalistcsv.clear();
        System.out.print("Archivo no encontrado");}
        reader.close();
        return rutinalistcsv;
    }
}






