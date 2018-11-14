package com.nef.corgi.apppowercorpore;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;

import static android.content.Context.MODE_PRIVATE;


public class userDAO {
    public static SimpleDateFormat FORMATO = new SimpleDateFormat("dd/MM/yyyy");
    public static void credenciales (SharedPreferences sp, userDTO userdto){

        //sp = getPreferences(MODE_PRIVATE);//crea un fichero de usuarios //TODO revisar el preferences
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("user",userdto.getUser_name());
        editor.putString("Email",userdto.getEmail_user());
        editor.putString("SID",userdto.getSid());
        editor.putString("EXPIRES",FORMATO.format(userdto.getExpires()));
        editor.putString("LAST_USER",userdto.getUser_name());
        editor.commit();
    }


}