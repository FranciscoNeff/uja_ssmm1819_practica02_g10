package com.nef.corgi.apppowercorpore;

import android.content.Context;

import com.nef.corgi.apppowercorpore.DTO.UserDTO;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

public class Registros {
    public Context context;
public void Registros(Context c){
    this.context=c;
}
    SimpleDateFormat FORMATO = new SimpleDateFormat("y-M-d-H-m-s");
    public void AddRegistro(UserDTO reg){
        String cadena=reg.getUser_name()+";"+FORMATO.format(System.currentTimeMillis());
try {
    FileOutputStream fos =  context.openFileOutput("Registros", context.MODE_APPEND);
    fos.write(cadena.getBytes());
    fos.close();
}catch (Exception ex){
    ex.printStackTrace();
}
    }
}
