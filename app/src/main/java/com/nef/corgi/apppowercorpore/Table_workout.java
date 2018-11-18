package com.nef.corgi.apppowercorpore;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Table_workout extends AppCompatActivity  {
    private List<rutinaDTO> rutinalistcsv = new ArrayList<>();
    private void readRutincacsv (){
        InputStream rutinacsv = getResources().openRawResource(R.raw.rutina_csv);
        BufferedReader reader = new BufferedReader(new InputStreamReader(rutinacsv, Charset.forName("UTF-8")));
        String line="";
       // try {
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
                Log.d("Rutina","Tabla" + rutinalistcsv);
            }

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
    private TextView title = (TextView) findViewById(R.id.row_text_title);
    private TextView header= (TextView) findViewById(R.id.row_text_header);
    private TextView text_body=(TextView)findViewById(R.id.row_text_body);
    //TODO tabla dinamica para los ejercicios
    //TODO tabla dinamica a traves de un XML(Investigar)
    //@Override
    public void onCreate (Bundle savedInstanteState,List<rutinaDTO> rutinalistCSV){
        super.onCreate(savedInstanteState);
        tableworkout.setShrinkAllColumns(true);
        tableworkout.setShrinkAllColumns(true);

        rutinaDTO rutine = new rutinaDTO();
        String[] ejercicios={};
        String[] repeticiones={};
        int Nseries=rutine.getSerie().length;
        String[] head={};
        for(int i=0; i < (rutinalistCSV.size()) ; i++){
            rutine=rutinalistCSV.get(i);
            if (rutine.getNombreEjercicio()[0]=="Ejercicio")
            { head[0]=rutine.getNombreEjercicio()[0]; }
        }//primera parte cabecera
        Nseries=rutine.getSerie().length;
        for(int i=0;i<Nseries+1;i++){
        head[i+1]="Serie"+(i+1);
        }//segunda parte cabecera
        String[] body={};
        //Ejercicio y numero de series
        //Todo introducir el head
        //Cuerpo de la rutina
        for(int i=1;i<(rutinalistCSV.size());i++){
            rutine=rutinalistCSV.get(i);
            ejercicios[i]= rutine.getNombreEjercicio()[i];
            for(int j=1; j< Nseries;j++){
                repeticiones[j]=rutine.getRepeticiones()[j];
            }
        }
        ejercicios[0]=head[0];

        for(int i = 0;i< (ejercicios.length); i++ ){
            body[i]=ejercicios[i];
            for(int j=0;j<Nseries;j++){
                body[i]=body[i]+repeticiones[j];
            }
        }

       /* String tabla={};
                tabla = head+body;*/
        //CSVReader csvreader = new CSVReader(new FileReader(R.raw.rutina_csv)';');//no funciona
        //Creacion de la tabla
        //Titulo de la columna
        Date fecha=new Date();
        String s_fecha = FORMATOFECHA.format(fecha);
        rutine.setDiaRutina(s_fecha);
        title.setText(s_fecha);
        row_title.addView(title);
        //TODO tabla dinamica de las series (cabecera)
        for(int i=0;i<rutine.getSerie().length;i++){
            header.setText(head[i]);
        }
        row_header.addView(header);
        //TODO tabla dinamica filas de ejercicios
            String[] filas={};
        for (int i=0;i<body.length ;i++) {
            for(int j=0;j<Nseries;j++) {
                filas[i] =filas[i]+body[j];//lectura de una fila con nombre mas repeticiones
                text_body.setText(filas[i]);
                row_body.addView(text_body);
            }
        }
    }
}
