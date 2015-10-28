package com.ant.contact.xmlparser;

import java.io.InputStream;
import java.util.List;

public interface ContactsParser {
	/** 
     * ���������� �õ�Contacts���󼯺� 
     * @param is 
     * @return 
     * @throws Exception 
     */  
    public List<Contacts> parse(InputStream is) throws Exception;  
      
    /** 
     * ���л�Contacts���󼯺� �õ�XML��ʽ���ַ��� 
     * @param contacts
     * @return 
     * @throws Exception 
     */  
    public String serialize(List<Contacts> contacts) throws Exception; 
}
