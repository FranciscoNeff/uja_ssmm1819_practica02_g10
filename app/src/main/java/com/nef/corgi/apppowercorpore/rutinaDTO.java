package com.nef.corgi.apppowercorpore;

import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;

public class rutinaDTO {
    Date diaRutina;
    String nombreEjercicio;
    int[] serie;
    String[] repeticiones;
    String tiempo; //cuando se guarde este valor ya estara guardado como la diferencia entre empezar y terminar

    public Date getDiaRutina() {
        return diaRutina;
    }

    public void setDiaRutina(Date diaRutina) {
        this.diaRutina = diaRutina;
    }

    public String getNombreEjercicio() {
        return nombreEjercicio;
    }

    public void setNombreEjercicio(String nombreEjercicio) {
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

    public rutinaDTO(Date diaRutina, String nombreEjercicio, int[] serie, String[] repeticiones, Time tempo) {
        this.diaRutina = diaRutina;
        this.nombreEjercicio = nombreEjercicio;
        this.serie = serie;
        this.repeticiones = repeticiones;
        this.tiempo=tiempo;
    }


    public String csvtoString() {
        return  diaRutina +","+ nombreEjercicio +","+  Arrays.toString(serie) +","+ Arrays.toString(repeticiones);//Preguntar por el array de to string como arreglarlo
    }
}
