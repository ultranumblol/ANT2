package com.ant.contact.Util;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
/**
 * ǩ��������
 * @author qwerr
 *
 */
public class SignMaker {
	/**
	 * ��ü��ܵ�ǩ��
	 * @param name ������û�������ʽ��username=xxx��
	 * @param passwd ��������룬��ʽ��password=xxx��
	 * @return  ����md5���ܵ�ǩ��
	 */
	public String getsign(String name,String passwd,String setctorid){
		MD5Util md5Util = new MD5Util();
		ArrayList<String> pass = new ArrayList<String>();
		if (setctorid==null) {
			pass.add(name);
			pass.add(passwd);
			//Log.i("xml", "11111111111111"+pass.toString());
		}if(setctorid!=null) {
			pass.add(name);
			pass.add(passwd);
			pass.add(setctorid);
			//Log.i("xml", "2222222222"+pass.toString());
			
		}
		Log.i("xml", "==========="+pass.toString());
		Collections.sort(pass);//���������Ԫ�ذ�����ĸ����		
	    	     String result = ""; 
	    	     String seprater = "&"; 
	    	     if (pass.size()==2) {
	    	    	 result=pass.get(0)+seprater+pass.get(1); 	     					
				}if(pass.size()==3) {
					result=pass.get(0)+seprater+pass.get(1)+seprater+pass.get(2); 
				}
		String sign1=md5Util.MD5(result);
		Log.i("xml","�������ݣ�"+result +"���ܺ�"+sign1);	
		return sign1;
		
		
	}
}
