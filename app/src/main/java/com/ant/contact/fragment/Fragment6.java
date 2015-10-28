package com.ant.contact.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ant.contact.Activity.ContactsActivity;
import com.ant.contact.R;
import com.ant.contact.Util.OnDataFinishedListener;
import com.ant.contact.Util.QuerXmlData;
import com.ant.contact.adapter.SimpleTreeAdapter;
import com.ant.contact.bean.FileBean;
import com.ant.contact.bean.Node;
import com.ant.contact.bean.TreeListViewAdapter;
import com.ant.contact.bean.TreeListViewAdapter.OnTreeNodeClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Fragment6 extends Fragment {
	private List<FileBean> mDatas = new ArrayList<FileBean>();
	private ListView mTree;
	private List<Map<String, Object>> constest;
	private TreeListViewAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//View view = inflater.inflate(R.layout.activity_main2, null);
		View view = inflater.inflate(R.layout.fragment1_1, null);
		init(view);
		return view;
	}

	private void init(View view) {
		//mTree = (ListView) view.findViewById(R.id.id_tree);
		mTree = (ListView) view.findViewById(R.id.list_1_1);
		initDatas();
	}
	//
	private void initDatas()
	{
		QuerXmlData qData = new QuerXmlData();
		qData.execute();
		qData.setOnDataFinishedListener(new OnDataFinishedListener() {

			@Override
			public void onDataSuccessfully(Object data) {
				Log.i("xml", " fragment6=======");
				constest=(List<Map<String, Object>>) data;
				Log.i("xml", " fragment6=======constest"+constest.toString());
				for (int i = 0; i < constest.size(); i++) {
					int id = (Integer) constest.get(i).get("id");
					int pid=(Integer) constest.get(i).get("pid");
					String name = constest.get(i).get("name").toString();
					String phone = constest.get(i).get("phone").toString();
					mDatas.add(new FileBean(id, pid, name, phone));
				}
				// id , pid , name ,dianhua
				/*mDatas.add(new FileBean(1, 0, "name1","18811111111"));
				mDatas.add(new FileBean(2, 1, "name1","18822222222"));
				mDatas.add(new FileBean(3, 1, "name1","18833333333"));
				 */
				Log.i("xml", " fragment6======="+mDatas.size()+"tiao");
				try
				{
					mAdapter = new SimpleTreeAdapter<FileBean>(mTree, getActivity().getApplicationContext(), mDatas, 0);

					mAdapter.setOnTreeNodeClickListener(new OnTreeNodeClickListener()
					{
						@Override
						public void onClick(Node node, int position)
						{
							if (node.isLeaf())
							{	
								String tielename =node.getName();
								
								int setctorid = node.getId();
								Intent intent = new Intent(getActivity().getApplicationContext(), ContactsActivity.class);
								intent.putExtra("sid", setctorid);
								intent.putExtra("title", tielename);
								startActivity(intent);
							
							}
						}

					});

					mTree.setAdapter(mAdapter);
				} catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
			}

			@Override
			public void onDataFailed() {
				Log.i("xml", " fragment6=======");
			}
		});


	}
}
