package com.ant.contact.fragment;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ant.contact.R;
import com.ant.contact.db.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 联系人
 */
public class FragmentTest extends Fragment {
    private ExpandableListView expandableListView_one;
    SimpleExpandableListAdapter adapter;
    private TextView addgroup;
    DatabaseHelper dbh ;
    List<Map<String, String>> gruops;
    List<List<Map<String, String>>> childs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activvity_main, null);

        expandableListView_one = (ExpandableListView) view.findViewById(R.id.expandableListView);
        addgroup = (TextView) view.findViewById(R.id.addgroup);
        dbh = new DatabaseHelper(view.getContext());
        initdata();
        //加入列表
        expandableListView_one.setAdapter(adapter);
        addgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText inputServer = new EditText(getActivity().getApplicationContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
                builder.setTitle("Server").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                        .setNegativeButton("Cancel", null);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        inputServer.getText().toString();



                    }
                });
                builder.show();


                insertGroup("新建分组", (gruops.size() + 1) + "");
                gruops =queryGroup();
                childs = GroupContent(queryContent(), gruops.size());
                Log.i("xml", "gruops.size()=======" + gruops.toString());
                Log.i("xml", "gruops.size()=======" + (gruops.size() + 1));
                adapter = new SimpleExpandableListAdapter(
                        getActivity().getApplicationContext(), gruops, R.layout.layout_parent, new String[]{"gname"}, new int[]{R.id.textGroup},
                        childs, R.layout.layout_children, new String[]{"name","phone"}, new int[]{R.id.textChild,R.id.textChild2}
                );
                expandableListView_one.setAdapter(adapter);
                Toast.makeText(getActivity().getApplicationContext(),"shuaxin!",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    //初始化数据
    private void initdata() {
        gruops = new ArrayList<Map<String, String>>();
        childs = new ArrayList<List<Map<String, String>>>();
        gruops =queryGroup();
        if(gruops.size()<1){
            insertGroup("常用联系人", "1");
            insertGroup("近期联系人", "2");
            insertGroup("新建分组", "3");
            insertContent("张三", "13328901234", "1");
            insertContent("张三", "13328901234", "1");
            insertContent("张三", "13328901234", "1");
            insertContent("张三","13328901234","2");
            insertContent("张三","13328901234","3");
        }
        gruops =queryGroup();
        childs = GroupContent(queryContent(),gruops.size());
        Log.i("xml", "gruops.size()=======" + gruops.size());

        Log.i("xml", "groups=====" + gruops.toString());
        Log.i("xml", "childs=====" + childs.toString());


        //创建ExpandableList的Adapter容器
        adapter = new SimpleExpandableListAdapter(
                getActivity().getApplicationContext(), gruops, R.layout.layout_parent, new String[]{"gname"}, new int[]{R.id.textGroup},
                childs, R.layout.layout_children, new String[]{"name","phone"}, new int[]{R.id.textChild,R.id.textChild2}
        );


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
     * 插入联系人数据
     * @param name
     * @param pid
     */
    private void insertContent(String name,String phone,String pid){
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues cv = new ContentValues();//实例化一个cv 用来装数据
        cv.put("pid", pid);
        cv.put("name",name );
        cv.put("phone",phone );
        db.insert("content1", null, cv);//插入操作
        db.close();

    }
    /**
     * 查询数据库数据，分组
     */
    private List<Map<String, String>> queryGroup(){

        SQLiteDatabase db = dbh.getWritableDatabase();
        Cursor c =db.query("fenzu", null, null, null, null, null, null);
        List<Map<String,String>> data = new ArrayList<Map<String, String>>();

        if(c.moveToFirst()){//判断游标是否为空
            for(int i = 0;i<c.getCount();i++){
                c.moveToPosition(i);//移动到指定记录
                String id = c.getString(c.getColumnIndex("pid"));
                String gname = c.getString(c.getColumnIndex("gname"));
                Map<String, String> map = new HashMap<String, String>();
                map.put("pid", id);

                map.put("gname", gname);
                data.add(map);

            }
        }
        Log.i("xml", "data的长度：=================" + data.size());
        db.close();
        return data;
    };
    /**
     * 查询数据库数据，分组
     */
    private List<Map<String, String>> queryContent(){
        SQLiteDatabase db = dbh.getWritableDatabase();
        Cursor c = db.query("content1", null, null, null, null, null, null);

        List<Map<String,String>> data = new ArrayList<Map<String, String>>();
        if(c.moveToFirst()){//判断游标是否为空
            for(int i = 0;i<c.getCount();i++){
                c.moveToPosition(i);//移动到指定记录
                String pid = c.getString(c.getColumnIndex("pid"));
                String name = c.getString(c.getColumnIndex("name"));
                String phone = c.getString(c.getColumnIndex("phone"));
                Map<String, String> map = new HashMap<String, String>();
                map.put("pid", pid);
                map.put("phone",phone);
                map.put("name", name);
                data.add(map);
            }
        }
        //Log.i("xml","diyicichaxun======"+data.size());
        // Log.i("xml","diyicichaxun======"+data.toString());
        db.close();
        return data;
    };
    /**
     * 重组联系人数据，按分组类型分组
     */
    private List<List<Map<String, String>>> GroupContent(List<Map<String, String>> data,int count){
        List<List<Map<String,String>>>  childs2 = new ArrayList<List<Map<String, String>>>();
        for (int i=1;i<=count;i++){
            List<Map<String,String>> data2 = new ArrayList<Map<String, String>>();
            for (int j = 0 ;j<data.size();j++){
                int k = Integer.parseInt(data.get(j).get("pid").toString());
                if (k==i){
                    Map<String, String> map = new HashMap<String, String>();
                    String name = data.get(j).get("name").toString();
                    String phone = data.get(j).get("phone").toString();
                    map.put("phone",phone);
                    map.put("name", name);
                    data2.add(map);
                }
            }
            childs2.add(data2);

        }

        return childs2;
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


    /* //RefreshableView refreshableView;
    ElasticScrollView elasticScrollView;
    DatabaseHelper dbh ;
    private TextView changyongtv,nearlytv;
    private ListView changyonglv,nearlylv;
    DBManager mdb;
    ArrayList<Map<String, Object>> data1 = new ArrayList<Map<String, Object>>();
    ArrayList<Map<String, Object>> data2 = new ArrayList<Map<String, Object>>();
    SimpleAdapter sAdapter1,sAdapter2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts2, null);
        initdb(view);
        init(view);
        Log.i("xml", "fragment4-->onCreateView()");

        return view;
    }
    //初始化数据库
    private void initdb(View view) {
        dbh = new DatabaseHelper(view.getContext());

    }

    //初始化控件
    private void init(View view) {
        mdb=new DBManager(view.getContext());

        changyonglv = (ListView) view. findViewById(R.id.lv_contacts_changyong);
        nearlylv = (ListView) view.findViewById(R.id.lv_contacts_nearly);
        changyongtv = (TextView) view.findViewById(R.id.contacts_changyong_type);
        nearlytv = (TextView) view .findViewById(R.id.contacts_nearly_type);


        changyongtv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                int Flag = changyonglv.getVisibility();
                if (Flag == View.VISIBLE) {
                    changyonglv.setVisibility(View.GONE);
                }
                if (Flag == View.GONE) {
                    data1.clear();
                    querydb();
                    sAdapter1.notifyDataSetChanged();
                    changyonglv.setVisibility(View.VISIBLE);
                }
            }
        });
        nearlytv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                int Flag2 = nearlylv.getVisibility();
                if(Flag2==View.VISIBLE){
                    nearlylv.setVisibility(View.GONE);
                }
                if(Flag2==View.GONE){
                    data2.clear();
                    querydb2();
                    sAdapter2.notifyDataSetChanged();
                    nearlylv.setVisibility(View.VISIBLE);
                }


            }
        });
        querydb();
        querydb2();
        sAdapter1 = new SimpleAdapter(getActivity(), data1, R.layout.list_contact_item2,
                new String[]{"name","phone"}, new int[]{R.id.name2,R.id.number2});
        sAdapter2 = new SimpleAdapter(getActivity(), data2, R.layout.list_contact_item2,
                new String[]{"name","phone"}, new int[]{R.id.name2,R.id.number2});
        changyonglv.setAdapter(sAdapter1);
        nearlylv.setAdapter(sAdapter2);
        //item点击事件
        changyonglv.setOnItemClickListener(new mshortclick());
        changyonglv.setOnItemLongClickListener(new mlongclick());
        nearlylv.setOnItemClickListener(new mshortclick());
        nearlylv.setOnItemLongClickListener(new mnearlylongClick());
    }
    *//**
 * 分类按钮点击事件监听
 * @author qwerr
 *
 *//*
    private class typeonclick implements OnClickListener  {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.contacts_changyong_type:
                    int Flag = changyonglv.getVisibility();
                    if(Flag==View.VISIBLE){
                        changyonglv.setVisibility(View.GONE);
                    }
                    if(Flag==View.GONE){
                        changyonglv.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.contacts_nearly_type:
                    int Flag2 = nearlylv.getVisibility();
                    if(Flag2==View.VISIBLE){
                        nearlylv.setVisibility(View.GONE);
                    }
                    if(Flag2==View.GONE){
                        nearlylv.setVisibility(View.VISIBLE);
                    }
                    break;

                default:
                    break;
            }

        }


    };

    //删除联系人操作
    private void delete(String phone){
        SQLiteDatabase db = dbh.getWritableDatabase();
        String sql ="delete from changyong where phone =?";
        String[] bindArgs = new String[]{phone};
        db.execSQL(sql, bindArgs);
        sql= null;
        db.close();

    }
    //最近联系人删除联系人操作
    private void delete2(String phone){
        SQLiteDatabase db = dbh.getWritableDatabase();
        String sql ="delete from nearly where phone =?";
        String[] bindArgs = new String[]{phone};
        db.execSQL(sql, bindArgs);
        sql= null;
        db.close();

    }
    *//**
 * 查询数据库数据，常用联系人
 *//*
    private void querydb(){
        SQLiteDatabase db = dbh.getWritableDatabase();
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

    };
    *//**
 * 查询数据库数据，最近联系人
 *//*
    private void querydb2(){
        SQLiteDatabase db = dbh.getWritableDatabase();
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
    };
    //短按打电话监听
    private class mshortclick implements OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            final TextView phone = (TextView) arg1.findViewById(R.id.number2);
            final TextView name = (TextView) arg1.findViewById(R.id.name2);
            insert2(name.getText().toString(), phone.getText().toString());
            Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri
                    .parse("tel:" +phone.getText().toString() ));
            startActivity(dialIntent);

        }};
    //常用联系人点击事件 长按删除，发送短信监听
    private class mlongclick implements OnItemLongClickListener{

        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
            final TextView phone = (TextView) arg1.findViewById(R.id.number2);
            AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
            builder.setTitle("请确认");
            //指定下拉列表的显示数据
            final String[] types = {"删除联系人", "发送短信"};
            builder.setItems(types,new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String select = types[which];
                    if(select.equals("删除联系人")){
                        delete(phone.getText().toString());
                        Toast.makeText(getActivity(), "删除成功!", Toast.LENGTH_SHORT).show();
                        data1.clear();
                        querydb();
                        sAdapter1.notifyDataSetChanged();
                    };
                    if(select.equals("发送短信")){
                        Uri smsToUri = Uri.parse("smsto:"+phone.getText().toString());

                        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);

                        intent.putExtra("sms_body", "");

                        startActivity(intent);
                    };

                }});
            builder.show();

            return true;
        }};
    *//**
 * 插入数据到最近联系人
 * @param name
 * @param phone
 *//*
    private void insert2(String name,String phone){
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues cv = new ContentValues();//实例化一个cv 用来装数据
        cv.put("name", name);
        cv.put("phone", phone);
        db.insert("nearly", null, cv);//插入操作
        db.close();

    }
    //近期联系人点击事件
    private class mnearlylongClick implements OnItemLongClickListener{

        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
            final TextView phone = (TextView) arg1.findViewById(R.id.number2);
            AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
            builder.setTitle("请确认");
            //指定下拉列表的显示数据
            final String[] types = {"删除联系人", "发送短信"};
            builder.setItems(types,new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String select = types[which];
                    if(select.equals("删除联系人")){
                        delete2(phone.getText().toString());
                        Toast.makeText(getActivity(), "删除成功!", Toast.LENGTH_SHORT).show();
                        data2.clear();
                        querydb2();
                        sAdapter2.notifyDataSetChanged();
                    };
                    if(select.equals("发送短信")){
                        Uri smsToUri = Uri.parse("smsto:"+phone.getText().toString());

                        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);

                        intent.putExtra("sms_body", "");

                        startActivity(intent);
                    };

                }});
            builder.show();
            return true;
        }

    }*/


