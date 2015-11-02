package com.ant.contact.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ant.contact.db.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qwerr on 2015/10/29.
 */
public class DBManager {
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context){
        helper = new DatabaseHelper(context);
        db=helper.getWritableDatabase();

    }
    //删除联系人操作
    public void delete(String phone){
       // SQLiteDatabase db = helper.getWritableDatabase();
        String sql ="delete from changyong where phone =?";
        String[] bindArgs = new String[]{phone};
        db.execSQL(sql, bindArgs);
        sql= null;
        db.close();

    }
    //最近联系人删除联系人操作
    public void delete2(String phone){
        //SQLiteDatabase db = helper.getWritableDatabase();
        String sql ="delete from nearly where phone =?";
        String[] bindArgs = new String[]{phone};
        db.execSQL(sql, bindArgs);
        sql= null;
        db.close();

    }
    /**
     * 查询数据库数据，常用联系人
     */
    public ArrayList<Map<String, Object>> querydb(){
        //SQLiteDatabase db = helper.getWritableDatabase();
        ArrayList<Map<String, Object>> data1 = new ArrayList<Map<String, Object>>();
        Cursor c =db.query("changyong", null, null, null, null, null, null);
        if(c.moveToFirst()){//判断游标是否为空
            for(int i = 0;i<c.getCount();i++){
                c.moveToPosition(i);//移动到指定记录
                String qname = c.getString(c.getColumnIndex("name"));
                String qphone = c.getString(c.getColumnIndex("phone"));
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", qname);
                map.put("phone", qphone);
                data1.add(map);
            }
        }
        db.close();
        c.close();
        return data1;

    };
    /**
     * 查询数据库数据，最近联系人
     */
    public ArrayList<Map<String, Object>> querydb2(){
        //SQLiteDatabase db = helper.getWritableDatabase();
        ArrayList<Map<String, Object>> data2 = new ArrayList<Map<String, Object>>();

        Cursor c =db.query("nearly", null, null, null, null, null, null);
        if(c.moveToFirst()){//判断游标是否为空
            for(int i = 0;i<c.getCount();i++){
                c.moveToPosition(i);//移动到指定记录
                String qname = c.getString(c.getColumnIndex("name"));
                String qphone = c.getString(c.getColumnIndex("phone"));
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("name", qname);
                map.put("phone", qphone);
                data2.add(map);
            }
        }
        db.close();
        return data2;
    };

    /**
     * 插入数据到最近联系人
     * @param name
     * @param phone
     */
    public void insert2(String name,String phone){
      // SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();//实例化一个cv 用来装数据
        cv.put("name", name);
        cv.put("phone", phone);
        db.insert("nearly", null, cv);//插入操作
        db.close();

    }

}
