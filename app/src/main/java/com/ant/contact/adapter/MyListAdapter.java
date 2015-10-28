package com.ant.contact.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ant.contact.R;

import java.util.List;
import java.util.Map;

public class MyListAdapter extends BaseAdapter{
	private Context mContext; 
	private List<Map<String, Object>> list;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.list.size();
	}

	public MyListAdapter(Context mContext, List<Map<String, Object>> list) {
		super();
		this.mContext = mContext;
		this.list = list;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	class Holder{
		TextView tv1;
		TextView tv2;
		
	}
	public void updateListView(List<Map<String, Object>> list){  
        this.list = list;  
        notifyDataSetChanged();  
    }  
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView==null) {
			holder=new Holder();
			convertView= LayoutInflater.from(mContext).inflate(R.layout.list_contact_item, null); 
			holder.tv1 = (TextView) convertView.findViewById(R.id.name);
			holder.tv2=(TextView) convertView.findViewById(R.id.number);
			convertView.setTag(holder);
		}else{
			holder=(Holder) convertView.getTag();
			
		}
		holder.tv1 .setText(list.get(position).get("name").toString()); 
		holder.tv2.setText(list.get(position).get("phone").toString()); 
		return convertView;
	}

}
