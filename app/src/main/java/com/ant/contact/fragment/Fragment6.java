package com.ant.contact.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.ant.contact.Activity.ContactsActivity;
import com.ant.contact.R;
import com.ant.contact.Util.OnDataFinishedListener;
import com.ant.contact.Util.QuerXmlData;
import com.ant.contact.View.ReboundScrollView;
import com.ant.contact.adapter.SimpleTreeAdapter;
import com.ant.contact.bean.FileBean;
import com.ant.contact.bean.Node;
import com.ant.contact.bean.TreeListViewAdapter;
import com.ant.contact.bean.TreeListViewAdapter.OnTreeNodeClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 组织机构
 */
public class Fragment6 extends Fragment {
    private List<FileBean> mDatas = new ArrayList<FileBean>();
    private ListView mTree,listView2;
    private List<Map<String, Object>> constest;
    private TreeListViewAdapter mAdapter;
    private LinearLayout reflesh2;
    private ImageView ivDeleteText2;
    private EditText etSearch2;
    private ReboundScrollView reboundScrollView,reboundScrollView2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //View view = inflater.inflate(R.layout.activity_main2, null);
        View view = inflater.inflate(R.layout.fragment1_1, null);
        init(view);
        return view;
    }
    /**
     * 判断网络状态
     * @param context
     * @return
     */
    public static boolean checkNetWorkStatus(Context context){
        boolean result;
        ConnectivityManager cm=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if ( netinfo !=null && netinfo.isConnected() ) {
            result=true;
            Log.i("xml", "The net was connected");
        }else{
            result=false;
            Log.i("xml", "The net was bad!");
        }
        return result;
    }
    private void init(View view) {
        //mTree = (ListView) view.findViewById(R.id.id_tree);
        reboundScrollView = (ReboundScrollView) view.findViewById(R.id.zuzhi_sv);
        reboundScrollView2 = (ReboundScrollView) view.findViewById(R.id.zuzhi_sv2);
        mTree = (ListView) view.findViewById(R.id.list_1_1);
        listView2 = (ListView) view.findViewById(R.id.list_1_2);
        reflesh2= (LinearLayout) view.findViewById(R.id.refesh2);
        //搜索功能
        listView2.setAdapter(new SimpleAdapter(getActivity(),testData(),R.layout.list_contact_item,
                new String[]{"name", "phone"}, new int[]{R.id.name, R.id.number}));


        ivDeleteText2 = (ImageView)view.findViewById(R.id.ivDeleteText2);
        etSearch2 = (EditText)view.findViewById(R.id.etSearch2);
        ivDeleteText2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                etSearch2.setText("");
            }
        });
        //搜索框输入文字监听
        etSearch2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    ivDeleteText2.setVisibility(View.GONE);
                    reboundScrollView.setVisibility(View.VISIBLE);
                    reboundScrollView2.setVisibility(View.GONE);
                } else {
                    ivDeleteText2.setVisibility(View.VISIBLE);
                    reboundScrollView.setVisibility(View.GONE);
                    reboundScrollView2.setVisibility(View.VISIBLE);
                }
                //myhandler.post(eChanged);
                search1();

            }
        });

        reflesh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean net =false;
                net = checkNetWorkStatus(getActivity());
                if (net==false){
                    Toast.makeText(getActivity(),"没有网络，请检查网络！",Toast.LENGTH_LONG).show();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("同步").setMessage("是否从服务器同步数据？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            File file= new File("/data/data/"+getActivity().getPackageName().toString()+"/shared_prefs","finals.xml");
                            if(file.exists()){
                                file.delete();
                                Intent intent = getActivity().getIntent();
                                getActivity().finish();
                                startActivity(intent);
                                Toast.makeText(getActivity().getApplicationContext(), "同步成功！", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getActivity().getApplicationContext(),"数据已经是最新！",Toast.LENGTH_SHORT).show();

                            }
                        }
                    }).setNegativeButton("取消",null).show();

                }

                }


        });

        initDatas();
    }
  private List<Map<String, Object>> testData(){
      List<Map<String,Object>> data = new ArrayList<Map<String, Object>>();
      for (int i = 0 ; i <20 ; i++){
          Map<String, Object> map = new HashMap<String, Object>();
          map . put("name", "ceshi"+i);
          map. put("phone", "qqqq" + i + i);
          data.add(map);


      }

     return data;

  }
    private void search1(){
        String input = etSearch2.getText().toString();
        //Log.i("xml","search方法： constest有："+constest.toString());
        //getmDataSub(peos2, input);//获取更新数据
    }

    //初始化数据
    private void initDatas() {
        constest=getInfo(getActivity().getApplicationContext(),"huancun");
        //根据sp中是否缓存了数据来选择是从本地加载还是联网
        if (constest.size()<2){
            //异步联网查询xml数据，组织机构数据
            Log.i("xml", " 异步联网查询xml数据，组织机构数据");
            QuerXmlData qData = new QuerXmlData();
            qData.execute();
            qData.setOnDataFinishedListener(new OnDataFinishedListener() {

                @Override
                public void onDataSuccessfully(Object data) {
                    //缓存数据，用sp保存
                    List<Map<String, Object>> huancundata = new ArrayList<Map<String, Object>>();
                    huancundata =(List<Map<String, Object>>) data;
                    saveInfo(getActivity().getApplicationContext(),"huancun",huancundata);
                    //Log.i("xml", " fragment6=======");
                    //第一次加载 直接用查询结果显示界面
                    constest = (List<Map<String, Object>>) data;
                    // Log.i("xml", " fragment6=======constest" + constest.toString());
                    for (int i = 0; i < constest.size(); i++) {
                        int id = (Integer) constest.get(i).get("id");
                        int pid = (Integer) constest.get(i).get("pid");
                        String name = constest.get(i).get("name").toString();
                        String phone = constest.get(i).get("phone").toString();
                        mDatas.add(new FileBean(id, pid, name, phone));
                    }
                    // id , pid , name ,dianhua
				/*mDatas.add(new FileBean(1, 0, "name1","18811111111"));
				mDatas.add(new FileBean(2, 1, "name1","18822222222"));
				mDatas.add(new FileBean(3, 1, "name1","18833333333"));
				 */
                    Log.i("xml", " fragment6=======" + mDatas.size() + "tiao");
                    try {
                        mAdapter = new SimpleTreeAdapter<FileBean>(mTree, getActivity().getApplicationContext(), mDatas, 0);

                        mAdapter.setOnTreeNodeClickListener(new OnTreeNodeClickListener() {
                            @Override
                            public void onClick(Node node, int position) {
                                if (node.isLeaf()) {
                                    String tielename = node.getName();

                                    int setctorid = node.getId();
                                    Intent intent = new Intent();
                                    intent.setClass(getActivity(), ContactsActivity.class);

                                    intent.putExtra("sid", setctorid);
                                    intent.putExtra("title", tielename);
                                    getActivity().startActivityForResult(intent, 0);
                                }
                            }

                        });

                        mTree.setAdapter(mAdapter);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onDataFailed() {
                    Log.i("xml", " fragment6=======");
                }
            });


        }
        else{
            Log.i("xml", " fragment6=======缓存中的constest:"+constest.toString());
            for (int i = 0; i < constest.size(); i++) {
                int id = Integer.parseInt(constest.get(i).get("id").toString()) ;
                int pid =Integer.parseInt(constest.get(i).get("pid").toString()) ;
                String name = constest.get(i).get("name").toString();
                String phone = constest.get(i).get("phone").toString();
                mDatas.add(new FileBean(id, pid, name, phone));
            }
            // id , pid , name ,dianhua
				/*mDatas.add(new FileBean(1, 0, "name1","18811111111"));
				mDatas.add(new FileBean(2, 1, "name1","18822222222"));
				mDatas.add(new FileBean(3, 1, "name1","18833333333"));
				 */
            Log.i("xml", " fragment6=======缓存中的mdatas：" + mDatas.size() + "条");
            try {
                mAdapter = new SimpleTreeAdapter<FileBean>(mTree, getActivity().getApplicationContext(), mDatas, 0);

                mAdapter.setOnTreeNodeClickListener(new OnTreeNodeClickListener() {
                    @Override
                    public void onClick(Node node, int position) {
                        if (node.isLeaf()) {
                            String tielename = node.getName();

                            int setctorid = node.getId();
                            Intent intent = new Intent(getActivity(), ContactsActivity.class);
                            intent.putExtra("sid", setctorid);
                            intent.putExtra("title", tielename);
                            getActivity().startActivityForResult(intent, 0);

                        }
                    }

                });

                mTree.setAdapter(mAdapter);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }



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
