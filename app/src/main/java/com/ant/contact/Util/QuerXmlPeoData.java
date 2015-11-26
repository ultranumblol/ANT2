package com.ant.contact.Util;

import android.R.integer;
import android.os.AsyncTask;

import com.ant.contact.xmlparser.People;
import com.ant.contact.xmlparser.PeopleParser;
import com.ant.contact.xmlparser.PullPeopleparser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * �첽��ѯxml���ݣ�ͨ���ص��ӿڷ���һ��listmap�����
 */

public class QuerXmlPeoData extends AsyncTask<integer, integer, List<Map<String, Object>>>{
	private List<People> mpeoples; 
	private List<Map<String, Object>> peos;
	OnDataFinishedListener onDataFinishedListener;
	private int sid;
	
	public QuerXmlPeoData(int sid) {
		super();
		this.sid = sid;
	}
	@Override
	protected List<Map<String, Object>> doInBackground(integer... params) {
		peos = new ArrayList<Map<String,Object>>();
		SignMaker sm = new SignMaker();//ʵ����
		String setctorid = "setctorid="+sid;
		String sign =sm.getsign("username=123","password=123", setctorid);//ͨ���û�������������sign
		//Log.i("xml","ǩ���ǣ�"+sign);
		XmlInputStream xmlInputStream = new XmlInputStream();
		//InputStream is = getActivity().getAssets().open("ant.xml"); 
		InputStream is= xmlInputStream.getStream("username=123","password=123", setctorid, sign);
		PeopleParser pParser = new PullPeopleparser();
		try {
			mpeoples=pParser.parse(is);								 
			for(People peoples:mpeoples){
				//Log.i("xml", "����ǣ�"+contacts.toString());
				Map<String, Object> map = new HashMap<String, Object>();
				if (peoples.getPhone()==null) {					
					map.put("phone", "---");
				}
				if (peoples.getPhone()!=null) {
					map.put("phone", peoples.getPhone());			
				}
				map.put("id", peoples.getId());
				map.put("sid", peoples.getSid());
				map.put("name", peoples.getName());
				map.put("ranke",peoples.getRank());
				peos.add(map);	
			}
			//Log.i("xml","���ݲ�ѯ���1");
			//Log.i("xml","------cons����"+cons.size()+"������");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return peos;
	}
	 public void setOnDataFinishedListener(
             OnDataFinishedListener onDataFinishedListener) {
         this.onDataFinishedListener = onDataFinishedListener;
     }
	
	@Override
	protected void onPostExecute(List<Map<String, Object>> result) {
		if(result!=null){
            onDataFinishedListener.onDataSuccessfully(result);
        }else{
            onDataFinishedListener.onDataFailed();
        }	
		
	}						

}
