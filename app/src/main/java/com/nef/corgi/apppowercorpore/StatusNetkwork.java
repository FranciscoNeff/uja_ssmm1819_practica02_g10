package com.nef.corgi.apppowercorpore;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class StatusNetkwork extends BroadcastReceiver {
    public Context c;
public StatusNetkwork(){}
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = manager.getActiveNetworkInfo();
        onNetworkChange(ni);
        c=context;
    }
    private void onNetworkChange(NetworkInfo networkInfo) {
        if (networkInfo != null) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                Log.d("MenuActivity", "CONNECTED");
                Toast.makeText(c.getApplicationContext(),R.string.Connect ,Toast.LENGTH_SHORT).show();
            } else {
                Log.d("MenuActivity", "DISCONNECTED");
                Toast.makeText(c.getApplicationContext(), R.string.Disconnected ,Toast.LENGTH_SHORT).show();
            }
        }
    }

}
//Ayuda de //https://programacionymas.com/blog/detectar-cuando-app-se-conecta-o-desconecta-internet
