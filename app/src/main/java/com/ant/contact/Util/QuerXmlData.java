package com.ant.contact.Util;

import android.R.integer;
import android.os.AsyncTask;
import android.util.Log;

import com.ant.contact.xmlparser.Contacts;
import com.ant.contact.xmlparser.ContactsParser;
import com.ant.contact.xmlparser.PullContactsparser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 异步查询xml数据，通过回调接口返回一个listmap结果集
 */

public class QuerXmlData extends AsyncTask<integer, integer, List<Map<String, Object>>>{
	private List<Contacts> mcontacts; 
	private List<Map<String, Object>> cons;
	OnDataFinishedListener onDataFinishedListener;
	@Override
	protected List<Map<String, Object>> doInBackground(integer... params) {
		cons = new ArrayList<Map<String,Object>>();
		SignMaker sm = new SignMaker();//实例化
		String sign =sm.getsign("username=123","password=123", null);//通过用户名，密码制作sign
		Log.i("xml","签名是："+sign);
		XmlInputStream xmlInputStream = new XmlInputStream();
		//InputStream is = getActivity().getAssets().open("ant.xml");  
		InputStream is= xmlInputStream.getStream("username=123","password=123", null, sign); 
		ContactsParser cParser = new PullContactsparser();
		try {
			mcontacts=cParser.parse(is);								 
			for(Contacts contacts:mcontacts){
				//Log.i("xml", "结果是："+contacts.toString());
				Map<String, Object> map = new HashMap<String, Object>();
				if (contacts.getPhone()==null) {					
					map.put("phone", "---");
				}
				if (contacts.getPhone()!=null) {
					map.put("phone", contacts.getPhone());			
				}
				map.put("id", contacts.getId());
				map.put("pid", contacts.getPid());
				map.put("name", contacts.getName());
				cons.add(map);	
			}
			//Log.i("xml","数据查询完毕1");
			//Log.i("xml","------cons中有"+cons.size()+"条数据");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return cons;
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
