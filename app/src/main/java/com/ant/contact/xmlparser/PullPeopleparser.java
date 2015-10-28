package com.ant.contact.xmlparser;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PullPeopleparser implements PeopleParser{

	@Override
	public List<People> parse(InputStream is) throws Exception {
		List<People> snews = null;  
		People people = null;  
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
				snews = new ArrayList<People>();  //��ʼ��contacts����
				break;  
				// �жϵ�ǰ�¼��Ƿ�Ϊ��ǩԪ�ؿ�ʼ�¼�
			case XmlPullParser.START_TAG:  
				if (pullParser.getName().equals("Table")) {  //�жϿ�ʼ��ǩԪ�� 
					people = new People();  	 
				}  

				else if (pullParser.getName().equals("id")) { 
					event=pullParser.next();//�ý�����ָ��id���Ե�ֵ 
					people.setId(Integer.parseInt(pullParser.getText()));
					 
				}  
				else if (pullParser.getName().equals("setctorid")) { 
					event=pullParser.next();//�ý�����ָ��pid���Ե�ֵ  					
					people.setSid(Integer.parseInt(pullParser.getText()));
				} 
				else if (pullParser.getName().equals("name")) { 
					event=pullParser.next();
					people.setName(pullParser.getText());  
				}  
				else if (pullParser.getName().equals("tel")) { 
					event=pullParser.next();
					people.setPhone(pullParser.getText()); 
				}
				else if (pullParser.getName().equals("ranke")) {
					event=pullParser.next();
					people.setRank(pullParser.getText());
				}

				break;  
				// �жϵ�ǰ�¼��Ƿ�Ϊ��ǩԪ�ؽ����¼� 
			case XmlPullParser.END_TAG:  
				if (pullParser.getName().equals("Table")) {  // �жϽ�����ǩԪ��
					snews.add(people);  
					people = null;  
				}  
				break;  

			}  
			 // ������һ��Ԫ�ز�������Ӧ�¼�  
			event = pullParser.next();  
		}  

		return snews;  

	}

	@Override
	public String serialize(List<People> people) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
