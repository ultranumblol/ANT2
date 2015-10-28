package com.ant.contact.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
	//类没有实例化,是不能用作父类构造器的参数,必须声明为静态  

	private static final String Name = "count"; //数据库名称  

	private static final int Version = 1; //数据库版本  
	private static final String CHANGYONG = "changyong";//表1
	private static final String NEARLY = "nearly";//表2
	private static final String MEMORY = "memory";//表3
	private static final String PERSON_ID = "id";
	private static final String PERSON_name = "name";
	private static final String PERSON_phone = "phone";

	public DatabaseHelper(Context context) {
		super(context, Name, null, Version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + CHANGYONG + " (" + PERSON_ID
				+ " INTEGER primary key autoincrement, " + PERSON_name + " text, "+ PERSON_phone +" text);";
		db.execSQL(sql);
		String sql2="CREATE TABLE " + NEARLY + " (" + PERSON_ID
				+ " INTEGER primary key autoincrement, " + PERSON_name + " text, "+ PERSON_phone +" text);";
		db.execSQL(sql2);
		String sql3="CREATE TABLE " + MEMORY + " (" + PERSON_ID
				+ " INTEGER primary key autoincrement, " + PERSON_name + " text, "+ PERSON_phone +" text);";
		db.execSQL(sql3);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
