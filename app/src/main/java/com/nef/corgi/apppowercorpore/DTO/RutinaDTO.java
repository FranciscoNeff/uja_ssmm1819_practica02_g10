package com.nef.corgi.apppowercorpore.DTO;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RutinaDTO {
    private String diaRutina;
    List<Ejercicio> listaEjercicios =new ArrayList<Ejercicio>();
    private  String tiempo; //cuando se guarde este valor ya estara guardado como la diferencia entre empezar y terminar

    public List<Ejercicio> getListaEjercicios() {
        return listaEjercicios;
    }

    public void setListaEjercicios(List<Ejercicio> listaEjercicios) {
        this.listaEjercicios = listaEjercicios;
    }

    public String getDiaRutina() {
        return diaRutina;
    }

    public void setDiaRutina(String diaRutina) {
        this.diaRutina = diaRutina;
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
    public RutinaDTO(String datos,List<Ejercicio> listaEjercicios) throws MalformedRutinaException{

        if(datos==null)
            throw new MalformedRutinaException(1);
        String parts[] = datos.split(";");

        this.diaRutina = diaRutina;
        this.listaEjercicios=listaEjercicios;
        this.tiempo=tiempo;
    }

    public RutinaDTO(){}


    public String csvtoStringRutina() {
        SimpleDateFormat FORMATOFECHA = new SimpleDateFormat("DD/MM/AAAA");
        String csv=FORMATOFECHA.format(diaRutina)+";"+tiempo;
        return csv+00001010;
    }

    public class MalformedRutinaException extends Exception {
        private int type = 0;
        private static final int RUTINA_CORRECTA = 0;
        private static final int FALTAN_ELEMENTOS = 1;
        private static final int BAD_SERIES = 2;
        private static final int BAD_REP = 3;
        private static final int BAD_NAME_WORK = 4;
        private
        MalformedRutinaException(int type){
           this.type=type;
        }
        }
    public class Ejercicio extends RutinaDTO {
        private  String nombreEjercicio;
        private  int[] serie;
        private  String[] repeticiones;

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

        public String getNombreEjercicio() {
            return nombreEjercicio;
        }

        public void setNombreEjercicio(String nombreEjercicio) {
            this.nombreEjercicio = nombreEjercicio;
        }
        public Ejercicio(){

        }
        public Ejercicio(String nombreEjercicio, int[] serie, String[] repeticiones) throws MalformedRutinaException {
    super();
            if (serie.length<0 ){// no deja ||serie=null
                throw new MalformedRutinaException(2);
            }
            if (repeticiones.length<0 ){
                throw new MalformedRutinaException(3);
            }
            if(nombreEjercicio.length()<3){
                throw new MalformedRutinaException(3);
            }
            this.nombreEjercicio = nombreEjercicio;

            this.serie = serie;

            this.repeticiones = repeticiones;
        }


        public String csvtoStringEjercicio() {
            String csvejercicio= nombreEjercicio;
             for (int i=0;i<serie.length;i++){
              csvejercicio= ";" +serie[i] +";"+ repeticiones[i];
             }
            return csvejercicio+00001010;
        }
    }

    }

