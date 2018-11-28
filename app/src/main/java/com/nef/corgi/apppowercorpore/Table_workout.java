package com.nef.corgi.apppowercorpore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.nef.corgi.apppowercorpore.DTO.RutinaDTO;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Table_workout extends AppCompatActivity  {
    private List<RutinaDTO> rutinalistcsv = new ArrayList<>();

    //SimpleDateFormat FORMATOFECHA = new SimpleDateFormat("DD/MM/AAAA", Locale.getDefault());
    private TableLayout tableworkout =null;
//    private TableRow row_title=(TableRow)findViewById(R.id.row_title);
//    private TableRow row_header=(TableRow)findViewById(R.id.row_header);
//    private TableRow row_body=(TableRow)findViewById(R.id.row_body);
//    private TextView title = (TextView) findViewById(R.id.row_text_title);
//    private TextView header= (TextView) findViewById(R.id.row_text_header);
//    private TextView text_body=(TextView)findViewById(R.id.row_text_body);
//    //TODO tabla dinamica para los ejercicios
    //TODO tabla dinamica a traves de un XML(Investigar)

    @Override
    public void onCreate (Bundle savedInstanteState){
        super.onCreate(savedInstanteState);
        setContentView(R.layout.layout_show_workouts);
        tableworkout = findViewById(R.id.tableworkout) ;
        tableworkout.setShrinkAllColumns(true);
        tableworkout.setShrinkAllColumns(true);

//        rutinaDTO rutine = new rutinaDTO();
//        String[] ejercicios={};
//        String[] repeticiones={};
//        int Nseries=rutine.getSerie().length;
//        String[] head={};
//        for(int i=0; i < (rutinalistCSV.size()) ; i++){
//            rutine=rutinalistCSV.get(i);
//            if (rutine.getNombreEjercicio()[0]=="Ejercicio")
//            { head[0]=rutine.getNombreEjercicio()[0]; }
//        }//primera parte cabecera
//        Nseries=rutine.getSerie().length;
//        for(int i=0;i<Nseries+1;i++){
//        head[i+1]="Serie"+(i+1);
//        }//segunda parte cabecera
//        String[] body={};
//        //Ejercicio y numero de series
//        //Todo introducir el head
//        //Cuerpo de la rutina
//        for(int i=1;i<(rutinalistCSV.size());i++){
//            rutine=rutinalistCSV.get(i);
//            ejercicios[i]= rutine.getNombreEjercicio()[i];
//            for(int j=1; j< Nseries;j++){
//                repeticiones[j]=rutine.getRepeticiones()[j];
//            }
//        }
//        ejercicios[0]=head[0];
//
//        for(int i = 0;i< (ejercicios.length); i++ ){
//            body[i]=ejercicios[i];
//            for(int j=0;j<Nseries;j++){
//                body[i]=body[i]+repeticiones[j];
//            }
//        }

       /* String tabla={};
                tabla = head+body;*/
        //CSVReader csvreader = new CSVReader(new FileReader(R.raw.rutina_csv)';');//no funciona
        //Creacion de la tabla
        //Titulo de la columna

  //      rutine.setDiaRutina(s_fecha);
        for(int i=1;i<5;i++){
            final TableRow row_body = new TableRow(this);
            Date fecha=new Date();
            //String s_fecha = FORMATOFECHA.format(fecha);
            TextView datetext = new TextView(this);
            datetext.setText(fecha.toString());
            datetext.setTextColor(getColor(R.color.colorAccent));
            row_body.addView(datetext);
            TextView texto = new TextView(this);
            texto.setText("Texto");

            row_body.addView(texto);
            tableworkout.addView(row_body);
        }
        //TODO tabla dinamica de las series (cabecera)
//        for(int i=0;i<rutine.getSerie().length;i++){
//            header.setText(head[i]);
//        }
//        row_header.addView(header);
//        tableworkout.addView(row_header);
//        //TODO tabla dinamica filas de ejercicios
//            String[] filas={};
//        for (int i=0;i<body.length ;i++) {
//            for(int j=0;j<Nseries;j++) {
//                filas[i] =filas[i]+body[j];//lectura de una fila con nombre mas repeticiones
//                text_body.setText(filas[i]);
//                row_body.addView(text_body);
//                tableworkout.addView(row_body);
//
//            }

///Revisar xq esto peta tela
 //       }
    }
}
