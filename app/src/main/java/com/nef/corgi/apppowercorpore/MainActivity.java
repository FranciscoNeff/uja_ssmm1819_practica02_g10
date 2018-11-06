package com.nef.corgi.apppowercorpore;


import android.content.Intent;
import android.content.SharedPreferences;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.text.DateFormat;


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
        SharedPreferences sf = getPreferences(MODE_PRIVATE);
        //String expires = sf.getString("EXPIRES","")
        String nombre = sf.getString("USER","");
            if(nombre!=""){
                Toast.makeText(this,"Bienvenido"+nombre,Toast.LENGTH_LONG).show();//comprobar el expires para q la sesion sea valida
                //expires > sesion valida
                //start activity
                //expires < sesion no valida
                //return login

            }


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

public class Autentica extends AsyncTask<userDTO,Void,userDTO>{ //me pasas,iteracion intermedia,devuelvo

            private static final String RESOURCE="/ssmm/autentica.php";
    private static final  String PARAM_USER= "user";
    private static final String  PARAM_PASS="pass";
    private static final int HTTP_STATUS_CODE =200 ;

    @Override
            protected userDTO doInBackground(userDTO... userDTOS) {
                userDTO data;
                userDTO result;
                if (userDTOS!=null) {
                    data=userDTOS[0];
                    //TODO hacer la conexion y la autenticacion
                    //REVISAR ejemplos tema 3

                    String service="http://"+data.getDominio()+data.getPuerto()+RESOURCE+"?"+data.getUser_name()+"&"+data.getPass();
                    try {
                        URL urlservice = new URL(service);
                        HttpURLConnection connection=(HttpURLConnection) urlservice.openConnection();

                        connection.setReadTimeout(10000);//milisegundos
                        connection.setConnectTimeout(15000 );//milisegundos
                        connection.setRequestMethod("GET");
                        connection.setDoInput(true);
                        connection.connect();
                        int code = connection.getResponseCode();//recibe el codigo de respuesta de la peticion 1xx 2xx etc
                        if (code==HTTP_STATUS_CODE){
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
                        String line;
                        while((line=br.readLine())!=null){
                            if(line.startsWith("SESSION-ID=")){//compara que la cadena empiece de esta manera
                               String parts[]= line.split("&");//para trocear una cadena se usa split, devuelve un array por cada trozo
                                if (parts.length==2){
                                    if(parts[1].startsWith(("EXPIRES"))){
                                        result = processSesion(data,parts[0],parts[1]);

                                    }
                                }
                            }
                        }
                        br.close();
                        }
                        else{
                            data=null;
                        }
                        connection.disconnect();//cierra la conexion
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        data=null;
                    }catch(IOException ioex){
                        ioex.printStackTrace();
                    }finally {
                        return data;
                    }


                }
               else{
                return null;}}
        protected void onPostExecute(userDTO user){
                    super.onPostExecute(user);
                    if (user!=null){
                        Toast.makeText(getApplicationContext(),"Correcto",Toast.LENGTH_LONG).show();
                        SharedPreferences sp = getSharedPreferences(user.getUser_name(),MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("User",user.getUser_name());
                        editor.putString("Email",user.getEmail_user());

                       // editor.putString("Expires",user.getExpires());//FORMAT
                        SharedPreferences def = getPreferences(MODE_PRIVATE);
                        SharedPreferences.Editor edit2 = def.edit();
                        edit2.putString("LAST_USER",user.getUser_name());
                        editor.commit();

                        Intent intent = new Intent(getApplicationContext(),ServiceActivity.class);
                        intent.putExtra(ServiceActivity.NAME_USER,user.getUser_name());
                        intent.putExtra(ServiceActivity.PARAM_SID,user.getSid());
                        intent.putExtra(ServiceActivity.PARAM_EXPIRED,user.getExpires());
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();//TODO dar mas informacion
                    }
                }

    /**
     *
     * @param input
     * @param session
     * @param expires
     * @return
     */

    protected userDTO processSesion (userDTO input,String session,String expires){
        userDTO ressult = null;
        session = session.substring(session.indexOf("="),session.length());//copia la cadeda desde que encuentre el igual
        expires = expires.substring(expires.indexOf("="),expires.length());
        input.setSid(session);
        String fecha = DateFormat.getDateInstance().format(expires);//TODO Revisar el date format
        input.setExpires(fecha);
        return input;
    }


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
