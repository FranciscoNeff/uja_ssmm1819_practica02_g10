package com.nef.corgi.apppowercorpore;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Table_workout extends Activity {
    private List<rutinaDTO> rutinalistcsv = new ArrayList<>();
    private void readRutincacsv (){
        InputStream rutinacsv = getResources().openRawResource(R.raw.rutina_csv);
        BufferedReader reader = new BufferedReader(new InputStreamReader(rutinacsv, Charset.forName("UTF-8")));
        String line="";
       // try {
            String[] ejercicios={};
            int i=0;
            rutinaDTO rutina = new rutinaDTO();
            String[] items={};
            while ((Integer.parseInt(items[i]))!=00001010){


                //La cadena se trocea con ;
                items = line.split(";");
                //Lectura

                //rutina.setNombreEjercicio(items[0]);
                int[] series={};
                String[] repeticiones={};

                ejercicios[i]=items[i];
                i++;
                do {
                    series[i]=(Integer.parseInt(items[i]));//revisar xq esto slo hay q hacerlo la primera linea
                    i++;
                    repeticiones[i]=items[i];
                i++;
                }while (Integer.parseInt(items[i])!=00001010);
                rutina.setSerie(series);
                rutina.setRepeticiones(repeticiones);
                rutinalistcsv.add(rutina);
                Log.d("Rutina","Tabla" + rutinalistcsv);
            }
            rutina.setNombreEjercicio(ejercicios);

       /* }catch (IOException error){
            Log.wtf("MyActivity", "Error reading line: " +line, error);
            error.printStackTrace();
            line = "Excepción: " + error.getMessage();
            System.out.println(line);
        }finally {
            System.out.println("Imposible cargar la rutina");
        }*/

    }
    SimpleDateFormat FORMATOFECHA = new SimpleDateFormat("DD/MM/AAAA", Locale.getDefault());
    private TableLayout tableworkout = (TableLayout)findViewById(R.id.tableworkout) ;
    private TableRow row_title=(TableRow)findViewById(R.id.row_title);
    private TableRow row_header=(TableRow)findViewById(R.id.row_header);
    private TableRow row_body=(TableRow)findViewById(R.id.row_body);

    //TODO tabla dinamica para los ejercicios
    //TODO tabla dinamica a traves de un XML(Investigar)
    //@Override
    public void onCreate (Bundle savedInstanteState,rutinaDTO rutine){
        //rutine.setSerie(series);//esto se cambiara por un get pero para el ejemplo sera un set
        int Nseries=rutine.getSerie().length;

        //public void addHeader(){//da fallo preguntar

        super.onCreate(savedInstanteState);
        tableworkout.setShrinkAllColumns(true);
        tableworkout.setShrinkAllColumns(true);
        //Titulo de la columna
        row_title.setGravity(Gravity.CENTER);
        TextView title = (TextView) findViewById(R.id.row_text_title);
        Date fecha=new Date();
        String s_fecha = FORMATOFECHA.format(fecha);
        rutine.setDiaRutina(s_fecha);
        title.setText(s_fecha);
        row_title.addView(title);
        //TODO tabla dinamica de las series
        TextView header= (TextView) findViewById(R.id.row_text_header);
        String head="Ejercicio";
        header.setText(head);
        for(int i=0;i<rutine.getSerie().length;i++){
            header.setText(head+"Serie"+i);
            //header.setGravity(Gravity.CENTER);
            //header.setBackgroundResource();
            //header.setLayoutParams(row_header);
        }
        row_header.addView(header);
        //TODO tabla dinamica filas de ejercicios
        TextView body=(TextView)findViewById(R.id.row_text_body);
        for (int i=0;i<rutine.getSerie().length;i++) {

        }


    }
}
