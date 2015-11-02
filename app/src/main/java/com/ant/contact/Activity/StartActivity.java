package com.ant.contact.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ant.contact.R;

/**
 * Created by qwerr on 2015/10/23.
 */
public class StartActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        init();

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
