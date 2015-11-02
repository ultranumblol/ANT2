package com.ant.contact.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import com.ant.contact.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 联系人
 */
public class FragmentTest extends Fragment {
    //定义两个List用来控制Group和Child中的String;

    private List<String> groupArray;//组列表
    private List<List<String>> childArray;//子列表
    private ExpandableListView expandableListView_one;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activvity_main, null);
        groupArray = new ArrayList<String>();
        childArray = new ArrayList<List<String>>();
        expandableListView_one = (ExpandableListView) view.findViewById(R.id.expandableListView);
        //创建二个一级条目标题
        Map<String, String> title_1 = new HashMap<String, String>();
        Map<String, String> title_2 = new HashMap<String, String>();
        Map<String, String> title_3 = new HashMap<String, String>();

        title_1.put("group", "111111");
        title_2.put("group", "222222");
        title_3.put("group", "33333");
        //创建一级条目容器
        List<Map<String, String>> gruops = new ArrayList<Map<String, String>>();

        gruops.add(title_1);
        gruops.add(title_2);
        gruops.add(title_3);
        //创建二级条目内容

        //内容一
        Map<String, String> content_1 = new HashMap<String, String>();
        Map<String, String> content_2 = new HashMap<String, String>();


        content_1.put("child", "ANDROID");
        content_2.put("child", "IOS");

        List<Map<String, String>> childs_1 = new ArrayList<Map<String, String>>();
        childs_1.add(content_1);
        childs_1.add(content_2);

        //内容二
        Map<String, String> content_3 = new HashMap<String, String>();
        Map<String, String> content_4 = new HashMap<String, String>();
        Map<String, String> content_5 = new HashMap<String, String>();

        content_3.put("child", "金钱");
        content_4.put("child", "权力");
        content_5.put("child", "女人");
        List<Map<String, String>> childs_2 = new ArrayList<Map<String, String>>();
        childs_2.add(content_3);
        childs_2.add(content_4);
        childs_2.add(content_5);
        List<Map<String, String>> childs_3 = new ArrayList<Map<String, String>>();
        childs_3.add(content_1);
        childs_3.add(content_2);

        //存放两个内容, 以便显示在列表中
        List<List<Map<String, String>>> childs = new ArrayList<List<Map<String, String>>>();
        childs.add(childs_1);
        childs.add(childs_2);
        childs.add(childs_3);
        //创建ExpandableList的Adapter容器
        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                getActivity().getApplicationContext(), gruops, R.layout.layout_parent, new String[]{"group"}, new int[]{R.id.textGroup},
                childs, R.layout.layout_children, new String[]{"child"}, new int[]{R.id.textChild}
        );

        //加入列表
        expandableListView_one.setAdapter(adapter);
        expandableListView_one.setOnChildClickListener(listener);


        return view;
    }

    private ExpandableListView.OnChildClickListener listener = new ExpandableListView.OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v,
                                    int groupPosition, int childPosition, long id) {
            // TODO Auto-generated method stub
            toast("点击了");
            return false;
        }

        private void toast(String str) {
            Toast.makeText(getActivity(), str, Toast.LENGTH_LONG).show();
        }
    };
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


