package com.nef.corgi.apppowercorpore;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.service.autofill.UserData;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;
import android.widget.Toast;

import com.nef.corgi.apppowercorpore.DTO.userDTO;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;


//el email sera el identificador unico de nuestra aplicacion(futuro)
//Aunque este el menu lateral este no se usa aun, solo tiene estilos por si en un futuro se usa
//en Translations Edit, contraseña esta Untranslate debido a la ñ (si se ve que el uso de la ñ se hace casi obligatorio, se cambiara la fuente)
//en nav tanto el header como el subtitulo cambiar el valor por el nombre del usuario como header y el mail como subtitule(futuro);estos ademas salen blancos por el tema(cambiarlo)
//E/HAL: load: id=gralloc != hmi->id=gralloc He buscado como eliminar este fallo pero ni he encontrado ese id ni nada(no es un error critico)
public class MainActivity extends AppCompatActivity implements Authetication.OnFragmentInteractionListener{
private userDTO user=null;
    SimpleDateFormat FORMATO = new SimpleDateFormat("y-M-d-H-m-s");

    private static final String SERVICE_DEFAULT_WEB = "http://";
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
            //lanzar tarea asincrona
        } else
            Toast.makeText(this,getString(R.string.mainactivity_fragmentepresent), Toast.LENGTH_SHORT).show();
        SharedPreferences sf = getPreferences(MODE_PRIVATE);
        String expires = sf.getString("EXPIRES","");
        String nombre = sf.getString("USER","");
            if(nombre!=""&&expires!=""){
                    try{
                if (FORMATO.parse(expires).compareTo(new Date(System.currentTimeMillis()))==1) {
                    //compareTO
                    //expires>=tactual sesion valida devuelve n>=0
                    //expires<tactual sesion no valida devuelve n<0
                    //start activity
                    Toast.makeText(this, "Bienvenido" + nombre, Toast.LENGTH_LONG).show();//comprobar el expires para q la sesion sea valida

                } else{
                        //return login
                    }
                }catch (ParseException e_date){
                     e_date.printStackTrace();
                    }

            }


        if(savedInstanceState!=null){
            String name = savedInstanceState.getString("name");
            String email = savedInstanceState.getString("email");
            String pass = savedInstanceState.getString("pass");
          //revisar lo de user

        }
        else {
            user = new userDTO();

        }

    }



        @Override
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);

            outState.putString("name",user.getUser_name());
            outState.putString("email",user.getEmail_user());
        }

        @Override
        public void onFragmentInteraction(userDTO user) {
            this.user.setUser_name(user.getUser_name());
            this.user.setEmail_user(user.getEmail_user());
        }

    //Practica 2
    /*public String readServer(userDTO user){
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

    }*///esto por ahora no
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

public class Autentica extends AsyncTask<userDTO,Void,userDTO>{ //recibo un usario sin sesion ,iteracion intermedia,devuelvo un usuario con SSID y EXPIRES

            private static final String RESOURCE="/ssmm/autentica.php";
            private static final  String DOMAIN= "labtelema.ujaen.es";
            private static final  String PARAM_USER= "user";
            private static final String  PARAM_PASS="pass";
            private static final String HTTP_STATUS_OKCODE ="200" ;
            private static final String HTTP_STATUS_ERRORLOCALCODE ="4";
            private static final String HTTP_STATUS_ERRORSERVERCODE ="5";
    @Override
            protected userDTO doInBackground(userDTO... userDTOS) {
                userDTO data;
                userDTO result = new userDTO();
                if (userDTOS!=null) {
                    data=userDTOS[0];
                    //TODO hacer la conexion y la autenticacion
                    //REVISAR ejemplos tema 3
                    data.setDominio(DOMAIN);
                    data.setPuerto(80);
                    String service=SERVICE_DEFAULT_WEB+data.getDominio()+data.getPuerto()+RESOURCE+"?"+data.getUser_name()+"&"+data.getPass();
                    try {
                        URL urlservice = new URL(service);
                        HttpURLConnection connection=(HttpURLConnection) urlservice.openConnection();
                        connection.setReadTimeout(10000);//milisegundos
                        connection.setConnectTimeout(15000 );//milisegundos
                        connection.setRequestMethod("GET");
                        connection.setDoInput(true);
                        connection.connect();
                        String code=String.valueOf(connection.getResponseCode());//recibe el codigo de respuesta de la peticion 1xx 2xx etc
                        if (code==HTTP_STATUS_OKCODE){
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
                        result=data;
                        br.close();
                        }
                         else if(code.startsWith(HTTP_STATUS_ERRORLOCALCODE)){//errores 4XX
                            //Toast.makeText(ServiceActivity.class,"Vuelva a intentarlo",Toast.LENGTH_LONG).show();revisar lo del toast
                            result=null;
                        } else if(code.startsWith(HTTP_STATUS_ERRORSERVERCODE)){//errores 5XX
                            result=null;
                        }
                        connection.disconnect();//cierra la conexion
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        result=null;
                    }catch(IOException ioex){
                        ioex.printStackTrace();
                    }finally {
                        return null;
                    }

                }
               else{
                    result=null;}
                return result;}
        protected void onPostExecute(userDTO user) {
            super.onPostExecute(user);
            if (user != null) {
                Toast.makeText(getApplicationContext(), "Correcto", Toast.LENGTH_LONG).show();
                SharedPreferences sp = getSharedPreferences(user.getUser_name(), MODE_PRIVATE);//crea un fichero con el nombre user.getUser_name()
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("user", user.getUser_name());
                editor.putString("Email", user.getEmail_user());
                editor.putString("SID", user.getSid());
                editor.putString("EXPIRES", FORMATO.format(user.getExpires()));

               /* SharedPreferences def = getPreferences(MODE_PRIVATE); //crea un fichero con todos los usuarios
                SharedPreferences.Editor edit2 = def.edit();
                edit2.putString("LAST_USER", user.getUser_name());
                editor.commit();*///
                Date expirationDATE = FORMATO.parse(FORMATO.format(user.getExpires()), new ParsePosition(0));
                Intent intent = new Intent(getApplicationContext(), ServiceActivity.class);
                Date instant = new Date(System.currentTimeMillis());
                if (((Date) expirationDATE).getTime() > instant.getTime()) {
                    intent.putExtra(ServiceActivity.NAME_USER, user.getUser_name());
                    intent.putExtra(ServiceActivity.PARAM_SID, user.getSid());
                    intent.putExtra(ServiceActivity.PARAM_EXPIRED, user.getExpires());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    //TODO dar mas informacion
                }
            }
        }

    /**
     *
     * @param input userDTO
     * @param session SESSION ID=XX
     * @param expires EXPIRES=XX
     * @return userDTO
     */

    protected userDTO processSesion (userDTO input,String session,String expires){
        userDTO ressult = null;
        session = session.substring(session.indexOf("="),session.length());//copia la cadeda desde que encuentre el igual
        expires = expires.substring(expires.indexOf("="),expires.length());
        input.setSid(session);
        input.setExpires(user.getExpires());//tenemos que meter un Date
        return input;
    }


        }
        private String downloadURL(String domain, String user, String pass) throws IOException {
            InputStream is = null;
            String result = "";

            HttpURLConnection conn = null;
            try {
                String contentAsString = "";
                String tempString = "";
                String url = "http://" + domain + "/ssmm/autentica.php" + "?user=" + user + "&pass=" + pass;
                URL service_url = new URL(url);
                System.out.println("Abriendo conexión: " + service_url.getHost()
                        + " puerto=" + service_url.getPort());
                conn = (HttpURLConnection) service_url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                final int response = conn.getResponseCode();
                final int contentLength = conn.getHeaderFieldInt("Content-length", 1000);
                String mimeType = conn.getHeaderField("Content-Type");
                String encoding = mimeType.substring(mimeType.indexOf(";"));

                Log.d(SERVICE_DEFAULT_WEB, "The response is: " + response);
                is = conn.getInputStream();

                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                while ((tempString = br.readLine()) != null) {
                    contentAsString = contentAsString + tempString;
                    //task.onProgressUpdate(contentAsString.length());
                }


                return contentAsString;
            } catch (MalformedURLException mex) {
                result = "URL mal formateada: " + mex.getMessage();
                System.out.println(result);
                 /*  mURL.post(new Runnable() {
                   @Override
                     public void run() {
                          mURL.setError(getString(R.string.network_url_error));
                      }
                  });*///preguntar por esto
            } catch (IOException e) {
                result = "Excepción: " + e.getMessage();
                System.out.println(result);


            } finally {
                if (is != null) {
                    is.close();
                    conn.disconnect();
                }
            }
            return result;


            }

      /*  @Override
        protected String doInBackground(userDTO...userDTOS){

            try{
                String url="http://"+userDTOS[0].getDominio();
             return  dowloadURL(url,userDTOS[0].getUser_name(),userDTOS[0].getPass());
            }catch (IOException ioex){
                ioex.printStackTrace();
            }
            return null;
}*/
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
