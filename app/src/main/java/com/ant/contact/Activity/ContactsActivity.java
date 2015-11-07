package com.ant.contact.Activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.contact.R;
import com.ant.contact.Util.OnDataFinishedListener;
import com.ant.contact.Util.QuerXmlPeoData;
import com.ant.contact.db.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ContactsActivity extends Activity{
	DatabaseHelper dbh ;
	private List<Map<String, Object>> peos;//联系人列表
	private List<Map<String, Object>> peos2;
	SimpleAdapter simpleAdapter;
	private ListView list1;//下方联系人列表
	private ImageView ivDeleteText;
	private EditText etSearch;
	private TextView title,back;
	ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
	Handler myhandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Log.i("xml", "ContactsActivity======oncreat");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fragment1);
		initdb();
		init();
		initquery();
	}
	//初始化查询方法
	private void initquery(){
		Intent intent=getIntent();
        final int sid = intent.getIntExtra("sid", 1);//接收上个acitvity传来的sid
        String title = intent.getStringExtra("title");
        this.title.setText(title);


        peos=getInfo(getApplicationContext(),"huancun"+sid);
        peos2=getInfo(getApplicationContext(),"huancun"+sid);
        if (peos.size()!=0){
            Log.i("xml", " 本地查询成功====peos数据：" + peos.toString());
            Log.i("xml", " 本地查询成功====peos数据：" + peos.size()+"tiao");
            simpleAdapter = new SimpleAdapter
                    (ContactsActivity.this, peos, R.layout.list_contact_item,
                            new String[]{"name", "phone", "ranke"}, new int[]{R.id.name, R.id.number, R.id.rank});
            list1.setAdapter(simpleAdapter);

        }
        else{
            //联网异步查询联系人
            QuerXmlPeoData qData2 = new QuerXmlPeoData(sid);
            qData2.execute();
            qData2.setOnDataFinishedListener(new OnDataFinishedListener() {

                @SuppressWarnings("unchecked")
                @Override
                public void onDataSuccessfully(Object data) {
                    //缓存数据，用sp保存
                    List<Map<String, Object>> huancunpeos1 = new ArrayList<Map<String, Object>>();
                    List<Map<String, Object>> huancunpeos2 = new ArrayList<Map<String, Object>>();
                    huancunpeos1 =(List<Map<String, Object>>) data;
                    huancunpeos2 =(List<Map<String, Object>>) data;
                    saveInfo(getApplicationContext(), "huancun" + sid, huancunpeos1);
                    saveInfo(getApplicationContext(), "huancun" + sid, huancunpeos2);


                    peos = (List<Map<String, Object>>) data;
                    peos2 = (List<Map<String, Object>>) data;
                    Log.i("xml", " 联网异步查询成功====peos数据：" + peos.toString());
                    simpleAdapter = new SimpleAdapter
                            (ContactsActivity.this, peos, R.layout.list_contact_item,
                                    new String[]{"name", "phone", "ranke"}, new int[]{R.id.name, R.id.number, R.id.rank});
                    list1.setAdapter(simpleAdapter);

                }

                @Override
                public void onDataFailed() {
                    // TODO Auto-generated method stub

                }
            });

        }


	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		peos=null;
		peos2=null;
	}

	//初始化界面
	private void init() {
		list1 = (ListView) findViewById(R.id.list_1);
		title = (TextView) findViewById(R.id.title);
		back = (TextView) findViewById(R.id.back_1);
		//搜索功能
		ivDeleteText = (ImageView)findViewById(R.id.ivDeleteText);
		etSearch = (EditText)findViewById(R.id.etSearch);

		ivDeleteText.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				etSearch.setText("");
			}
		});
		//搜索框输入文字监听
		etSearch.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				// TODO Auto-generated method stub

			}

			public void afterTextChanged(Editable s) {
				if (s.length() == 0) {
					ivDeleteText.setVisibility(View.GONE);
				} else {
					ivDeleteText.setVisibility(View.VISIBLE);
				}
				//myhandler.post(eChanged);
				search1();

			}
		});
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//数据是使用Intent返回
				Intent intent = new Intent();
				//把返回数据存入Intent
				intent.putExtra("result", "该刷新了");
				//设置返回数据
                setResult(RESULT_OK,intent);
                ContactsActivity.this.finish();

			}
		});
		//短按打电话监听
		list1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				final TextView name = (TextView) arg1.findViewById(R.id.name);
				final TextView phone = (TextView) arg1.findViewById(R.id.number);
                final TextView rank = (TextView) arg1.findViewById(R.id.rank);
				insert2(name.getText().toString(), phone.getText().toString(),2+"",rank.getText().toString());
				Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri
						.parse("tel:" + phone.getText().toString()));
				startActivity(dialIntent);
			}
		});
		//长按dialog监听
		list1.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
										   final int arg2, long arg3) {
				final TextView name = (TextView) arg1.findViewById(R.id.name);
				final TextView phone = (TextView) arg1.findViewById(R.id.number);
                final TextView rank = (TextView) arg1.findViewById(R.id.rank);
				AlertDialog.Builder builder = new AlertDialog.Builder(ContactsActivity.this);
				builder.setTitle("请选择");
				//指定下拉列表的显示数据
				final String[] types = {"添加到手机通讯录", "添加到常用联系人", "发送短信"};
				builder.setItems(types, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String select = types[which];
						if (select.equals("添加到手机通讯录")) {
							testInsert(name.getText().toString(), phone.getText().toString());
							Toast.makeText(ContactsActivity.this, "添加成功!", Toast.LENGTH_LONG).show();
						}

						if (select.equals("添加到常用联系人")) {
							insert(name.getText().toString(), phone.getText().toString(),1+"",rank.getText().toString());
							Toast.makeText(ContactsActivity.this, "添加成功!", Toast.LENGTH_LONG).show();
						}

						if (select.equals("发送短信")) {
							String num = phone.getText().toString();
							Uri smsToUri = Uri.parse("smsto:" + num);

							Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);

							intent.putExtra("sms_body", "");

							startActivity(intent);
						}



					}
				});
				builder.show();
				return false;
			}
		});
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

	//初始化数据库
	private void initdb() {
		dbh = new DatabaseHelper(this);


	}
	private void search1(){
		String input = etSearch.getText().toString();
		//Log.i("xml","search方法： constest有："+constest.toString());
		getmDataSub(peos2, input);//获取更新数据
	}
	/**
	 * 插入数据到常用联系人
	 * @param name
	 * @param phone
	 */
	private void insert(String name,String phone,String pid,String rank){
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        Log.i("xml","当前时间=============："+date);

		SQLiteDatabase db = dbh.getWritableDatabase();
		ContentValues cv = new ContentValues();//实例化一个cv 用来装数据
		cv.put("name", name);
		cv.put("phone", phone);
        cv.put("pid", pid);
        cv.put("rank",rank);
        cv.put("date",date);
		db.insert("content1", null, cv);//插入操作
		db.close();


	}
	/**
	 * 添加单个联系人
	 * @param name
	 * @param phoneNum
	 */
	public void testInsert(String name,String phoneNum){
		ContentValues values = new ContentValues();
		//首先向RawContacts.CONTENT_URI执行一个空值插入，目的是获取系统返回的rawContactId
		Uri rawContactUri = this.getApplicationContext().getContentResolver().insert(RawContacts.CONTENT_URI, values);
		long rawContactId = ContentUris.parseId(rawContactUri);

		//往data表插入姓名数据
		values.clear();
		values.put(Data.RAW_CONTACT_ID, rawContactId);
		values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);
		values.put(StructuredName.GIVEN_NAME, name);
		this.getApplicationContext().getContentResolver().insert(
				android.provider.ContactsContract.Data.CONTENT_URI, values);

		//往data表插入电话数据
		values.clear();
		values.put(android.provider.ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
		values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
		values.put(Phone.NUMBER, phoneNum);
		values.put(Phone.TYPE, Phone.TYPE_MOBILE);
		this.getApplicationContext().getContentResolver().insert(
				android.provider.ContactsContract.Data.CONTENT_URI, values);

	}

	/**
	 * 插入数据到最近联系人
	 * @param name
	 * @param phone
	 */
	private void insert2(String name,String phone,String pid,String rank){
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
		SQLiteDatabase db = dbh.getWritableDatabase();
		ContentValues cv = new ContentValues();//实例化一个cv 用来装数据
		cv.put("name", name);
		cv.put("phone", phone);
		cv.put("pid",pid);
        cv.put("rank",rank);
        cv.put("date",date);
		db.insert("content1", null, cv);//插入操作
		db.close();

	}
	/**
	 * 插入数据到缓存
	 * @param name
	 * @param phone
	 */
	private void insert3(String name,String phone){
		SQLiteDatabase db = dbh.getWritableDatabase();
		ContentValues cv = new ContentValues();//实例化一个cv 用来装数据
		cv.put("name", name);
		cv.put("phone", phone);
		db.insert("memory", null, cv);//插入操作
		db.close();

	}
	/**
	 * 删除表3所有数据
	 * @param name
	 * @param phone
	 */
	private void delete3(String name,String phone){
		SQLiteDatabase db = dbh.getWritableDatabase();
		ContentValues cv = new ContentValues();//实例化一个cv 用来装数据
		cv.put("name", name);
		cv.put("phone", phone);
		db.insert("memory", null, cv);//插入操作
		db.close();

	}
	/**
	 * 更新data数据
	 * @param constest
	 * @param data
	 */
	private void getmDataSub(List<Map<String, Object>> constest,String data)
	{
		//Log.i("xml","进去getmDataSub方法时peos"+peos.toString());
		ArrayList<Map<String, Object>> data2 = new ArrayList<Map<String, Object>>();
		data2.clear();
		int length = constest.size();
		//Log.i("xml","进去getmDataSub方法时peos2的长度为"+length);
		for(int i = 0; i < length; i++){

			if (constest.get(i).get("phone").toString().contains(data)||
					constest.get(i).get("name").toString().contains(data)) {
				Map<String,Object> item = new HashMap<String,Object>();
				item.put("name",peos2.get(i).get("name").toString());
				item.put("phone", peos2.get(i).get("phone").toString());
				data2.add(item);
				//Log.i("xml","getmDataSub方法的循环后peos2：："+constest.get(i).get("phone").toString());
			}
		}
		//Log.i("xml","getmDataSub方法后peos"+constest.toString());
		//更新
		simpleAdapter =new SimpleAdapter(ContactsActivity.this, data2, R.layout.list_contact_item,
				new String[]{"name","phone"}, new int[]{R.id.name,R.id.number});
		list1.setAdapter(simpleAdapter);

	}
	/**
	 * 保存数据到sp
	 * @param context
	 * @param key
	 * @param datas
	 */
	public void saveInfo(Context context, String key, List<Map<String, Object>> datas) {
		JSONArray mJsonArray = new JSONArray();
		for (int i = 0; i < datas.size(); i++) {
			Map<String, Object> itemMap = datas.get(i);
			Iterator<Map.Entry<String, Object>> iterator = itemMap.entrySet().iterator();

			JSONObject object = new JSONObject();

			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = iterator.next();
				try {
					object.put(entry.getKey(), entry.getValue());
				} catch (JSONException e) {

				}
			}
			mJsonArray.put(object);
		}

		SharedPreferences sp = context.getSharedPreferences("finals", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(key, mJsonArray.toString());
		editor.commit();
	}

	/**
	 * 从sp获取数据
	 * @param context
	 * @param key
	 * @return
	 */
	public List<Map<String, Object>> getInfo(Context context, String key) {
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		SharedPreferences sp = context.getSharedPreferences("finals", Context.MODE_PRIVATE);
		String result = sp.getString(key, "");
		try {
			JSONArray array = new JSONArray(result);
			for (int i = 0; i < array.length(); i++) {
				JSONObject itemObject = array.getJSONObject(i);
				Map<String, Object> itemMap = new HashMap<String, Object>();
				JSONArray names = itemObject.names();
				if (names != null) {
					for (int j = 0; j < names.length(); j++) {
						String name = names.getString(j);
						String value = itemObject.getString(name);
						itemMap.put(name, value);
					}
				}
				datas.add(itemMap);
			}
		} catch (JSONException e) {

		}

		return datas;

	}



}
