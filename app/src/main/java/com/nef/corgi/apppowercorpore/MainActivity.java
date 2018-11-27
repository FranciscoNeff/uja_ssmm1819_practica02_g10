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
public class MainActivity extends AppCompatActivity implements Authetication.OnFragmentInteractionListener {

    private static final String SERVICE_DEFAULT_WEB = "http://";
    private static final String DOMAIN = "labtelema.ujaen.es";//el dominio es estatico
    private static final String RESOURCE = "/ssmm/autentica.php";
    private static final String QUERY_USER = "?user=";
    private static final String QUERY_PASS = "&pass=";
    private static final int PORT = 80;

    private userDTO user = null;
    SimpleDateFormat FORMATO = new SimpleDateFormat("y-M-d-H-m-s");
    ConnectTask mtask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("INICIO", "Bienvenido a PowerCorpore");
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag_inicio = fm.findFragmentById(R.id.main_container);
        //Binvenida y muestra el nombre de usuario,en vez del de la app//si se comenta el menu lateral es como se ve mejor
        if (frag_inicio == null) {
            FragmentTransaction ft = fm.beginTransaction();
            Authetication fragment = Authetication.newInstance("", "");
            ft.add(R.id.main_container, fragment, "login");
            ft.commit();
            //lanzar tarea asincrona
        } else
            Toast.makeText(this, getString(R.string.mainactivity_fragmentepresent), Toast.LENGTH_SHORT).show();
        SharedPreferences sf = getPreferences(MODE_PRIVATE);
        String expires = sf.getString("EXPIRES", "");
        String nombre = sf.getString("USER", "");
        if (nombre.length() > 0 && expires.length() > 0) {

            //comprobar el expires para q la sesion sea valida
            try {
                FORMATO = new SimpleDateFormat("y-M-d-H-m-s");
                if (FORMATO.parse(expires).getTime() > System.currentTimeMillis()) {
                    //compareTO
                    //expires>=tactual sesion valida devuelve n>=0
                    //expires<tactual sesion no valida devuelve n<0
                    Toast.makeText(this, "Bienvenido" + nombre, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, ServiceActivity.class);
                    intent.putExtra(ServiceActivity.PARAM_USER_NAME, nombre);
                    intent.putExtra(ServiceActivity.PARAM_USER_EXPIRED, expires);
                    startActivity(intent);
                    //start activity
                }
            } catch (ParseException e_date) {
                e_date.printStackTrace();
            }

        }

       /* if(savedInstanceState!=null){
            String name = savedInstanceState.getString("name");
            String email = savedInstanceState.getString("email");
            String pass = savedInstanceState.getString("pass");
          //revisar lo de user

        }
        else {
            user = new userDTO();

        }*///p1


    }

      /*  @Override
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);

            outState.putString("name",user.getUser_name());
            outState.putString("pass",user.getUser_name());
            outState.putString("email",user.getEmail_user());
        }*/

    @Override
    public void onFragmentInteraction(userDTO user) {
        //this.user.setUser_name(user.getUser_name());
        //this.user.setEmail_user(user.getEmail_user());
        Autentica userLOG = new Autentica();
        userLOG.execute(user);

    }

    //Practica 2
    public String readServer(userDTO user) {
        try {
            //URL url = new URL(domain);
            //HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            Socket socket = new Socket(user.getDominio(), user.getPuerto());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("GET /~jccuevas/ssmm/login.php?user=user1&pass=12341234 HTTP/1.1\r\nhost:www4.ujaen.es\r\n\r\n");
            dataOutputStream.flush();

            StringBuilder sb = new StringBuilder();
            BufferedReader bis;
            bis = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = "";
            while ((line = bis.readLine()) != null) {
                sb.append(line);
            }
            final String datos = sb.toString();


            return datos;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }//esto por ahora no


    public class Autentica extends AsyncTask<userDTO, Void, userDTO> { //recibo un usario sin sesion ,iteracion intermedia,devuelvo un usuario con SSID y EXPIRES
        private static final String HTTP_STATUS_OKCODE = "200";
        private static final String HTTP_STATUS_ERRORLOCALCODE = "4";
        private static final String HTTP_STATUS_ERRORSERVERCODE = "5";

        @Override
        protected userDTO doInBackground(userDTO... userDTOS) {
            userDTO data;
            userDTO result = new userDTO();
            if (userDTOS != null) {
                data = userDTOS[0];
                //TODO hacer la conexion y la autenticacion
                //REVISAR ejemplos tema 3
                data.setDominio(DOMAIN);
                data.setPuerto(PORT);
                String service = SERVICE_DEFAULT_WEB + data.getDominio() + ":" + data.getPuerto() + RESOURCE + QUERY_USER + data.getUser_name() + QUERY_PASS + data.getPass();
                try {
                    URL urlservice = new URL(service);
                    HttpURLConnection connection = (HttpURLConnection) urlservice.openConnection();
                    connection.setReadTimeout(10000);//milisegundos
                    connection.setConnectTimeout(15000);//milisegundos
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.connect();
                    String code = String.valueOf(connection.getResponseCode());//recibe el codigo de respuesta de la peticion 1xx 2xx etc
                    if (code.equalsIgnoreCase(HTTP_STATUS_OKCODE)) {//por aqui anda el fallo
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                        String line;
                        while ((line = br.readLine()) != null) {
                            if (line.startsWith("SESSION-ID=")) {//compara que la cadena empiece de esta manera
                                String parts[] = line.split("&");//para trocear una cadena se usa split, devuelve un array por cada trozo
                                if (parts.length == 2) {
                                    if (parts[1].startsWith(("EXPIRES="))) {
                                        result = processSesion(data, parts[0], parts[1]);

                                    }
                                }
                            }
                        }
                        br.close();
                    } else if (code.startsWith(HTTP_STATUS_ERRORLOCALCODE)) {//errores 4XX
                        Toast.makeText(getApplicationContext(), "Vuelva a intentarlo", Toast.LENGTH_LONG).show();
                        data = null;//cambiar result
                    } else if (code.startsWith(HTTP_STATUS_ERRORSERVERCODE)) {//errores 5XX
                        Toast.makeText(getApplicationContext(), "Problemas con el servidor intentelo mas tarde", Toast.LENGTH_LONG).show();
                        data = null;//cambiar result
                    }
                    connection.disconnect();//cierra la conexion
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    data = null;//cambiar por result
                } catch (IOException ioex) {
                    ioex.printStackTrace();
                } finally {
                    return result;
                }

            } else {
                return result;
            }
        }

        @Override
        protected void onPostExecute(userDTO user) {
            super.onPostExecute(user);

            if (user != null) {
                Toast.makeText(getApplicationContext(), "Correcto", Toast.LENGTH_LONG).show();
                SharedPreferences sp = getPreferences(MODE_PRIVATE);//crea un fichero con el nombre user.getUser_name()
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("user", user.getUser_name());
                editor.putString("Email", user.getEmail_user());
                editor.putString("SID", user.getSid());
                FORMATO = new SimpleDateFormat("y-M-d-H-m-s");
                String temp = FORMATO.format(user.getExpires());
                editor.putString("EXPIRES", temp);

               /* SharedPreferences def = getPreferences(MODE_PRIVATE); //crea un fichero con todos los usuarios
                SharedPreferences.Editor edit2 = def.edit();
                edit2.putString("LAST_USER", user.getUser_name());
                editor.commit();*///

                Intent intent = new Intent(getApplicationContext(), ServiceActivity.class);
                intent.putExtra(ServiceActivity.PARAM_USER_NAME, user.getUser_name());
                intent.putExtra(ServiceActivity.PARAM_USER_SID, user.getSid());
                intent.putExtra(ServiceActivity.PARAM_USER_EXPIRED, user.getExpires());
                startActivity(intent);

            } else {
                SharedPreferences sp = getPreferences(MODE_PRIVATE);//crea un fichero con el nombre user.getUser_name()
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("user", "");
                editor.putString("Email", "");
                editor.putString("SID", "");
                editor.putString("EXPIRES", "");
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                //TODO dar mas informacion
            }

    }


    /**
     * @param input   userDTO
     * @param session SESSION ID=XX
     * @param expires EXPIRES=XX
     * @return userDTO
     */

    protected userDTO processSesion(userDTO input, String session, String expires) {
        FORMATO = new SimpleDateFormat("y-M-d-H-m-s");
        session = session.substring(session.indexOf("=") + 1, session.length());//copia la cadeda desde que encuentre el igual
        expires = expires.substring(expires.indexOf("=") + 1, expires.length());//como la copia desde que encuentra el igual le suma uno para coger la cadena
        input.setSid(session);
        try {
            input.setExpires(FORMATO.parse(expires));//se le introduce un date
        } catch (ParseException e) {
            e.printStackTrace();
            input.setExpires(expires);//se le introduce un String
        }
        return input;
    }
}


    private String downloadURL(String user, String pass) throws IOException {
        InputStream is = null;
        String result = "";

        HttpURLConnection conn = null;
        try {
            String contentAsString = "";
            String tempString = "";
            String url = SERVICE_DEFAULT_WEB + DOMAIN + RESOURCE + QUERY_PASS + user + QUERY_PASS + pass;
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

class ConnectTask extends AsyncTask<userDTO, Integer, String> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        TextView banner = findViewById(R.id.main_degree);
        banner.setText(R.string.main_connecting);
    }
//revisar
//        @Override
//        protected String doInBackground(userDTO... user) {
//            try {
//                //URL url = new URL(domain);
//                //HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                //DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
//                Socket socket = new Socket(user[0].getDominio(), user[0].getPuerto());
//                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
//                dataOutputStream.writeUTF("GET /~jccuevas/ssmm/login.php?user=user1&pass=12341234 HTTP/1.1\r\nhost:www4.ujaen.es\r\n\r\n");
//                dataOutputStream.flush();
//
//                StringBuilder sb = new StringBuilder();
//                BufferedReader bis;
//                bis = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                String line = "";
//                while ((line = bis.readLine()) != null) {
//                    sb.append(line);
//                    publishProgress(line.length());
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                final String datos = sb.toString();
//
//
//                return datos;
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//
//        }

    @Override
    protected String doInBackground(userDTO... userDTOS) {

        try {
            return downloadURL(userDTOS[0].getUser_name(), userDTOS[0].getPass());
        } catch (IOException ioex) {
            ioex.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Toast.makeText(getApplicationContext(), getString(R.string.main_progress) + " " + String.valueOf(values[0]), Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        TextView banner = findViewById(R.id.main_degree);
        banner.setText(R.string.main_connected);
    }
}
}
