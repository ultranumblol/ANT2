package com.ant.contact.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.contact.R;
import com.ant.contact.adapter.GroupManagerAdapter;
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
    GroupManagerAdapter gpadapter;

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
        setResult(RESULT_OK, intent);
        super.finish();
    }

    private void init() {
        mfinish = (TextView) findViewById(R.id.groupfinish);
        maddgroup = (TextView) findViewById(R.id.addgroup);
        grouplist = (ListView) findViewById(R.id.groupMan_list);
        grouplist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                TextView pidtv = (TextView) view.findViewById(R.id.gmpid);
                TextView idtv = (TextView) view.findViewById(R.id.gmid);
                final String pid = pidtv.getText().toString();
                final String gid = idtv.getText().toString();
                new AlertDialog.Builder(GroupManagerActivity.this)

                        .setTitle("删除")

                        .setMessage("是否删除该分组？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (pid.equals("1")||pid.equals("2")){
                                    Toast.makeText(getApplicationContext(),"默认分组不能删除！",Toast.LENGTH_SHORT).show();
                                }
                                else{

                                    deletecontent(pid,gid);// 删除操作
                                    flush();
                                }
                            }
                        })
                        .setNegativeButton("取消", null)


                        .show();
            }
        });
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
                        int num = (int) (1000 * Math.random()) + 2;
                        int num2 = data1.size() + 1;
                        insertGroup(inputServer.getText().toString(), num2 + "");
                        flush();

                    }
                });
                builder.show();
            }
        });
        flush();
    }
    //刷新list
    private void flush(){
        data1 = new ArrayList<Map<String, Object>>();
        data1=queryGroup();
        Log.i("xml", "data10000000000000:" + data1.toString());
        gpadapter = new GroupManagerAdapter(data1,GroupManagerActivity.this);
        //grouplist.setAdapter(gpadapter);
        grouplist.setAdapter(new SimpleAdapter(getApplicationContext(), data1, R.layout.layout_parent2,
                new String[]{"gname", "pid", "id"}, new int[]{R.id.mangroup_name, R.id.gmpid, R.id.gmid}));

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
     * 删除联系人
     * @param pid
     */
    private void deletecontent(String pid,String id){
        SQLiteDatabase db = dbh.getWritableDatabase();
        String sql2 ="delete from content1 where pid =?";
        String[] bindArgs = new String[]{pid};
        String sql3 ="delete from fenzu where id =?";
        String[] bindArgs2 = new String[]{id};
        db.execSQL(sql3, bindArgs2);
        db.execSQL(sql2, bindArgs);
        sql2= null;
        sql3=null;
        db.close();

    }
    /**
     * 删除联系人
     * @param id
     */
    private void deletecontentid(String id){
        SQLiteDatabase db = dbh.getWritableDatabase();
        String sql3 ="delete from fenzu where id =?";
        String[] bindArgs = new String[]{id};
        db.execSQL(sql3, bindArgs);
        sql3=null;
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
                String pid = c.getString(c.getColumnIndex("pid"));
                String gid = c.getString(c.getColumnIndex("id"));
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("gname", gname);
                map.put("pid",pid);
                map.put("id",gid);
                data.add(map);

            }
        }
        db.close();
        return data;
    }
}
