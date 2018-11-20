package com.nef.corgi.apppowercorpore;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import com.nef.corgi.apppowercorpore.DTO.monitorDTO;
import com.nef.corgi.apppowercorpore.DTO.rutinaDTO;
import com.nef.corgi.apppowercorpore.DTO.userDTO;
import com.nef.corgi.apppowercorpore.mensaje.Actualizacion;
import com.nef.corgi.apppowercorpore.mensaje.ReadCSV;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//Preguntar como quitar(en la barra de aplicacion)el ServiceActivity
public class ServiceActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //Variables estaticas de control de usuario
    public static final String NAME_USER="name";
    public static final String EMAIL_USER="email";
    public static final String PASS_USER="pass";//Esta variable quizas no sea adecuada
    public static final String PARAM_SID="";
    public static final String PARAM_EXPIRED="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //botón flotante
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();//añadir accion(futuro)
            }
        });
        //Menu lateral
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Código práctica
        Intent intent = getIntent();
        String s_user = intent.getStringExtra(NAME_USER);
        String s_pass = intent.getStringExtra(PASS_USER);
        String s_email =intent.getStringExtra(EMAIL_USER);

        Toast.makeText(this, "Hola "+s_user , Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        //llamada a la actualizacion de datos
        if (id == R.id.action_update) {//codigo para actualizar las rutinas de cliente a server

            Actualizacion subida =  new Actualizacion();

        userDTO user=null;
            monitorDTO monitor=null ;
            //los dto contendran los valores necesarios estos no estaran a null
          user.setUser_name(getIntent().getStringExtra(NAME_USER));
          user.setEmail_user(getIntent().getStringExtra(EMAIL_USER));
          //TODO preguntar como realizarlo con los parametros dentro de actualizacion
try {
    String datos = subida.update(user, monitor);
    /*
    * Habria que añadir un enviar al servidor o bien al mail permitente del monitor
    * datos = a formato mensaje en csv a mandar
    * */
}catch (IOException e){
    e.printStackTrace();
}


        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id ==  R.id.nav_user) {
//opcion para ver datos del usuario
        } else if (id == R.id.nav_work_out) {
            //TODO llamar a table_workout desde aqui
    Intent workout = new Intent(ServiceActivity.this,Table_workout.class);
    startActivity(workout);
//opcion para elegir las rutinas
        }else if (id == R.id.nav_result) {
//opcion para ver los resultado las rutinas
        } else if (id == R.id.nav_share) {
//opcion para compartir los resultados por redes sociales

        } else if (id == R.id.nav_send) {
//opcion para recibir los datos almacenados al server
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
