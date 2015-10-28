package com.ant.contact.xmlparser;

import java.io.InputStream;
import java.util.List;

public interface ContactsParser {
	/** 
     * 解析输入流 得到Contacts对象集合 
     * @param is 
     * @return 
     * @throws Exception 
     */  
    public List<Contacts> parse(InputStream is) throws Exception;  
      
    /** 
     * 序列化Contacts对象集合 得到XML形式的字符串 
     * @param contacts
     * @return 
     * @throws Exception 
     */  
    public String serialize(List<Contacts> contacts) throws Exception; 
}
