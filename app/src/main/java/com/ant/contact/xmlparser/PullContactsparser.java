package com.ant.contact.xmlparser;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PullContactsparser implements ContactsParser{

	@Override
	public List<Contacts> parse(InputStream is) throws Exception {
		List<Contacts> snews = null;  
		Contacts contacts = null;  
		// ��android.util.Xml����һ��XmlPullParserʵ��  
		XmlPullParser pullParser = Xml.newPullParser(); 
		// ���������� ��ָ�����뷽ʽ  
		pullParser.setInput(is, "UTF-8");  
		 // ������һ���¼�  
		int event = pullParser.getEventType();  
		while (event != XmlPullParser.END_DOCUMENT) {  
			switch (event) {  
			// �жϵ�ǰ�¼��Ƿ�Ϊ�ĵ���ʼ�¼�  
			case XmlPullParser.START_DOCUMENT:  
				snews = new ArrayList<Contacts>();  //��ʼ��contacts����
				break;  
				// �жϵ�ǰ�¼��Ƿ�Ϊ��ǩԪ�ؿ�ʼ�¼�
			case XmlPullParser.START_TAG:  
				if (pullParser.getName().equals("Table")) {  //�жϿ�ʼ��ǩԪ�� 
					contacts = new Contacts();  	 
				}  

				else if (pullParser.getName().equals("id")) { 
					event=pullParser.next();//�ý�����ָ��id���Ե�ֵ 
					contacts.setId(Integer.parseInt(pullParser.getText()));
					 
				}  
				else if (pullParser.getName().equals("pid")) { 
					event=pullParser.next();//�ý�����ָ��pid���Ե�ֵ  					
					contacts.setPid(Integer.parseInt(pullParser.getText()));
				} 
				else if (pullParser.getName().equals("name")) { 
					event=pullParser.next();
					contacts.setName(pullParser.getText());  
				}  
				else if (pullParser.getName().equals("phone")) { 
					event=pullParser.next();
					contacts.setPhone(pullParser.getText()); 
				}  

				break;  
				// �жϵ�ǰ�¼��Ƿ�Ϊ��ǩԪ�ؽ����¼� 
			case XmlPullParser.END_TAG:  
				if (pullParser.getName().equals("Table")) {  // �жϽ�����ǩԪ��
					snews.add(contacts);  
					contacts = null;  
				}  
				break;  

			}  
			 // ������һ��Ԫ�ز�������Ӧ�¼�  
			event = pullParser.next();  
		}  

		return snews;  

	}

	@Override
	public String serialize(List<Contacts> contacts) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
