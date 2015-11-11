package com.ant.contact.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ant.contact.R;

/**
 * Created by qwerr on 2015/10/23.
 */
public class StartActivity extends Activity{
    private boolean net=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        init();
        net=checkNetWorkStatus(getApplicationContext());
        if(net==false){
            Toast.makeText(getApplicationContext(),"正处于离线状态！",Toast.LENGTH_LONG).show();
        }


    }

    /**
     * 判断网络状态
     * @param context
     * @return
     */
    public static boolean checkNetWorkStatus(Context context){
        boolean result;
        ConnectivityManager cm=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if ( netinfo !=null && netinfo.isConnected() ) {
            result=true;
            Log.i("xml", "The net was connected");
        }else{
            result=false;
            Log.i("xml", "The net was bad!");
        }
        return result;
    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2200);
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
