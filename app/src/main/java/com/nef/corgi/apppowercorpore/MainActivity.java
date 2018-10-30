package com.nef.corgi.apppowercorpore;


import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.Socket;


//el email sera el identificador unico de nuestra aplicacion(futuro)
//Aunque este el menu lateral este no se usa aun, solo tiene estilos por si en un futuro se usa
//en Translations Edit, contraseña esta Untranslate debido a la ñ (si se ve que el uso de la ñ se hace casi obligatorio, se cambiara la fuente)
//en nav tanto el header como el subtitulo cambiar el valor por el nombre del usuario como header y el mail como subtitule(futuro);estos ademas salen blancos por el tema(cambiarlo)
//E/HAL: load: id=gralloc != hmi->id=gralloc He buscado como eliminar este fallo pero ni he encontrado ese id ni nada(no es un error critico)
public class MainActivity extends AppCompatActivity implements Authetication.OnFragmentInteractionListener{
private userDTO user=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("INICIO","Bienvenido a PowerCorpore");
        FragmentManager fm = getSupportFragmentManager();
        Fragment hello = fm.findFragmentById(R.id.main_container);
        //Binvenida y muestra el nombre de usuario,en vez del de la app//si se comenta el menu lateral es como se ve mejor
        if(hello==null) {
            FragmentTransaction ft = fm.beginTransaction();
            Authetication fragment = Authetication.newInstance("", "");
            ft.add(R.id.main_container, fragment, "login");
            ft.commit();
        } else
            Toast.makeText(this,getString(R.string.mainactivity_fragmentepresent), Toast.LENGTH_SHORT).show();

        if(savedInstanceState!=null){
            String name = savedInstanceState.getString("name");
            String email = savedInstanceState.getString("email");
            String pass = savedInstanceState.getString("pass");
            user = new userDTO(name, email, pass);
          //revisar lo de user

        }
        else {
            user = new userDTO();

        }

       //changetitle(user.getUser_name());//evitamos q coja el nombre name por defecto del ya creado en la clase userDTO
    }

    public void changetitle(String title) {
        TextView tuser = findViewById(R.id.main_apptitle);
        tuser.setText(title);
    }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);

            outState.putString("Dominio",user.getDominio());
        }

        @Override
        public void onFragmentInteraction(userDTO name) {

            this.user.setUser_name(name.getUser_name());
            changetitle(user.getUser_name());
        }

    //Practica 2
    public String readServer(userDTO user){
        try {
            //URL url = new URL(domain);
            //HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            Socket socket = new Socket(user.getDominio(),user.getPuerto());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("GET /~jccuevas/ssmm/login.php?user=user1&pass=12341234 HTTP/1.1\r\nhost:www4.ujaen.es\r\n\r\n");
            dataOutputStream.flush();

            StringBuilder sb = new StringBuilder();
            BufferedReader bis;
            bis = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = "";
            while((line = bis.readLine())!=null) {
                sb.append(line);
            }
            final String datos= sb.toString();


            return datos;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
    class ConnectTask extends AsyncTask<userDTO,Integer,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            TextView banner = findViewById(R.id.main_degree);
            banner.setText(R.string.main_connecting);
        }

        @Override
        protected String doInBackground(userDTO... user) {
            try {
                //URL url = new URL(domain);
                //HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                Socket socket = new Socket(user[0].getDominio(), user[0].getPuerto());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF("GET /~jccuevas/ssmm/login.php?user=user1&pass=12341234 HTTP/1.1\r\nhost:www4.ujaen.es\r\n\r\n");
                dataOutputStream.flush();

                StringBuilder sb = new StringBuilder();
                BufferedReader bis;
                bis = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line = "";
                while ((line = bis.readLine()) != null) {
                    sb.append(line);
                    publishProgress(line.length());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                final String datos = sb.toString();


                return datos;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;

        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Toast.makeText(getApplicationContext(),getString(R.string.main_progress)+" "+String.valueOf(values[0]),Toast.LENGTH_LONG).show();

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            TextView banner = findViewById(R.id.main_degree);
            banner.setText(R.string.main_connected);
        }
    }
}
