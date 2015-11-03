package com.ant.contact.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
	//��û��ʵ����,�ǲ����������๹�����Ĳ���,��������Ϊ��̬  

	private static final String Name = "count1"; //���ݿ�����

	private static final int Version = 1; //���ݿ�汾
	private static final String FENZU_NAME = "fenzu";//�������Ʊ�
	private static final String CONTENT = "content";//��ϵ���б�
	private static final String PERSON_ID = "id";
	private static final String GROUP_name = "gname";
    private static final String PERSON_name = "name";
	private static final String PERSON_phone = "phone";
    private static final String PERSON_groupid = "pid";

	public DatabaseHelper(Context context) {
		super(context, Name, null, Version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/*String sql = "CREATE TABLE " + CHANGYONG + " (" + PERSON_ID
				+ " INTEGER primary key autoincrement, " + PERSON_name + " text, "+ PERSON_phone +" text);";
		db.execSQL(sql);
		String sql2="CREATE TABLE " + NEARLY + " (" + PERSON_ID
				+ " INTEGER primary key autoincrement, " + PERSON_name + " text, "+ PERSON_phone +" text);";
		db.execSQL(sql2);
		String sql3="CREATE TABLE " + MEMORY + " (" + PERSON_ID
				+ " INTEGER primary key autoincrement, " + PERSON_name + " text, "+ PERSON_phone +" text);";
		db.execSQL(sql3);*/
//        String sql3="CREATE TABLE " + "Test" + " (" + PERSON_ID
//                + " INTEGER primary key autoincrement, " + PERSON_name + " text, "+ PERSON_phone +" text);";
//        db.execSQL(sql3);
        db.execSQL("CREATE TABLE IF NOT EXISTS fenzu (id integer primary key autoincrement, pid varchar(60),gname varchar(60))");
        db.execSQL("CREATE TABLE content1 (id integer primary key autoincrement, pid varchar(60),name varchar(60), phone varchar(60))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
