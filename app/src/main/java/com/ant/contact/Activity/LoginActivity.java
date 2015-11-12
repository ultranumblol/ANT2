package com.ant.contact.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ant.contact.R;
import com.ant.contact.Util.CheckLogin;
import com.ant.contact.Util.OnDataFinishedListener;

/**
 * Created by qwerr on 2015/11/11.
 */
public class LoginActivity extends Activity {
    private EditText name,password;
    private LinearLayout login;
    private CheckBox autologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);
        initview();
        checklogin();
    }

    private void checklogin() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lname = name.getText().toString();
                String pass = password.getText().toString();
                CheckLogin checkLogin = new CheckLogin(lname, pass);
                checkLogin.execute();
                checkLogin.setOnDataFinishedListener(new OnDataFinishedListener() {
                    @Override
                    public void onDataSuccessfully(Object data) {

                        String checked = data.toString();
                        //Log.i("xml", "truetruetruetruetruetruetruetrue" + checked);
                        Toast.makeText(LoginActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
                        if (autologin.isChecked()){
                            savesp();
                        }
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onDataFailed() {
                        //Log.i("xml", "falsefalsefalsefalsefalsefalsefalsefalse");
                        Toast.makeText(LoginActivity.this, "请输入正确的用户名和密码！", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    private void initview() {
        name = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.userpass);
        login = (LinearLayout) findViewById(R.id.login);
        autologin = (CheckBox) findViewById(R.id.autologin);


    }
    private void savesp(){
        SharedPreferences preferences = getSharedPreferences("autologin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor  = preferences.edit();
        editor.putString("autologin","true");
        editor.commit();

    }
    private String getsp(){
        SharedPreferences preferences = getSharedPreferences("autologin", Context.MODE_PRIVATE);
        String flag = preferences.getString("autologin","false");
        return flag;
    }

}
