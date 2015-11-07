package com.ant.contact.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ant.contact.R;
import com.ant.contact.db.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qwerr on 2015/11/5.
 */
public class GroupManagerActivity extends Activity{
    private TextView mfinish;
    DatabaseHelper dbh ;
    private List<Map<String,String>> list_data;
    List<Map<String, Object>> data1 = new ArrayList<Map<String, Object>>();
    private TextView maddgroup;
    private ListView grouplist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        dbh = new DatabaseHelper(getApplicationContext());
        setContentView(R.layout.managergroup);
        init();
    }

    @Override
    public void finish() {
        //数据是使用Intent返回
        Intent intent = new Intent();
        //把返回数据存入Intent
        intent.putExtra("result", "该刷新了");
        //设置返回数据
        setResult(RESULT_OK,intent);
        super.finish();
    }

    private void init() {
        mfinish = (TextView) findViewById(R.id.groupfinish);
        maddgroup = (TextView) findViewById(R.id.addgroup);
        grouplist = (ListView) findViewById(R.id.groupMan_list);
        mfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //数据是使用Intent返回
                Intent intent = new Intent();
                //把返回数据存入Intent
                intent.putExtra("result", "该刷新了");
                //设置返回数据
                setResult(RESULT_OK, intent);
                GroupManagerActivity.this.finish();
            }
        });
        maddgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText inputServer = new EditText(GroupManagerActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(GroupManagerActivity.this);
                builder.setTitle("请输入分组名称：").setView(inputServer)
                        .setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        insertGroup(inputServer.getText().toString(), (data1.size() + 1) + "");
                        flush();

                    }
                });
                builder.show();
            }
        });
        flush();
    }
    private void flush(){
        data1 = new ArrayList<Map<String, Object>>();
        data1=queryGroup();
        //Log.i("xml", "data10000000000000:" + data1.toString());
        grouplist.setAdapter(new SimpleAdapter(getApplicationContext(), data1, R.layout.layout_parent,
                new String[]{"gname"}, new int[]{R.id.textGroup}));

    }
    /**
     * 插入分组数据
     * @param gname
     * @param pid
     */
    private void insertGroup(String gname,String pid){
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues cv = new ContentValues();//实例化一个cv 用来装数据
        cv.put("pid", pid);
        cv.put("gname", gname);
        db.insert("fenzu", null, cv);//插入操作
        db.close();

    }
    /**
     * 查询数据库数据，分组
     */
    private List<Map<String, Object>> queryGroup(){

        SQLiteDatabase db = dbh.getWritableDatabase();
        Cursor c =db.query("fenzu", null, null, null, null, null, null);
        List<Map<String,Object>> data = new ArrayList<Map<String, Object>>();

        if(c.moveToFirst()){//判断游标是否为空
            for(int i = 0;i<c.getCount();i++){
                c.moveToPosition(i);//移动到指定记录
                String gname = c.getString(c.getColumnIndex("gname"));
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("gname", gname);
                data.add(map);

            }
        }
        db.close();
        return data;
    }
}
