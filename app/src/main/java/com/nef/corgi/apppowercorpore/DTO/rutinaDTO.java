package com.nef.corgi.apppowercorpore.DTO;

import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;

public class rutinaDTO {
    private String diaRutina;
    private  String[] nombreEjercicio;
    private  int[] serie;
    private  String[] repeticiones;
    private  String tiempo; //cuando se guarde este valor ya estara guardado como la diferencia entre empezar y terminar

    public String getDiaRutina() {
        return diaRutina;
    }

    public void setDiaRutina(String diaRutina) {
        this.diaRutina = diaRutina;
    }

    public String[] getNombreEjercicio() {
        return nombreEjercicio;
    }

    public void setNombreEjercicio(String[] nombreEjercicio) {
        this.nombreEjercicio = nombreEjercicio;
    }

    public int[] getSerie() {
        return serie;
    }

    public void setSerie(int[] serie) {
        this.serie = serie;
    }

    public String[] getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(String[] repeticiones) {
        this.repeticiones = repeticiones;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    /**
     * <datos> = <rutina><PUNTOYCOMA><repeticiones>
     *
     *
     * @param datos
     */
    public rutinaDTO(String datos) throws MalformedRutinaException{

        if(datos==null)
            throw new MalformedRutinaException(1);
        String parts[] = datos.split(";");

        this.diaRutina = diaRutina;
        this.nombreEjercicio = nombreEjercicio;
        this.serie = serie;
        this.repeticiones = repeticiones;
        this.tiempo=tiempo;
    }

    public rutinaDTO(String diaRutina, String[] nombreEjercicio, int[] serie, String[] repeticiones, String tempo) {
        this.diaRutina = diaRutina;
        this.nombreEjercicio = nombreEjercicio;
        this.serie = serie;
        this.repeticiones = repeticiones;
        this.tiempo=tiempo;
    }
    public rutinaDTO(){}


    public String csvtoString() {
        String csv;
       csv= diaRutina +";"+ nombreEjercicio;
               for(int i=0;i<Arrays.toString(serie).length();i++){
            csv=csv+";"+serie[i]+";"+repeticiones[i];
        }
        return csv+00001010;
    }

    public class MalformedRutinaException extends Exception {

        private int type = 0;
        private static final int FALTAN_ELEMENTOS = 1;

        MalformedRutinaException(int type){
           this.type=type;
        }

    }
}
