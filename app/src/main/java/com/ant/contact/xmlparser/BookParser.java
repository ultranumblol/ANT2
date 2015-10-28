package com.ant.contact.xmlparser;

import java.io.InputStream;
import java.util.List;

public interface BookParser {
	 /** 
     * ���������� �õ�Book���󼯺� 
     * @param is 
     * @return 
     * @throws Exception 
     */  
    public List<Book> parse(InputStream is) throws Exception;  
      
    /** 
     * ���л�Book���󼯺� �õ�XML��ʽ���ַ��� 
     * @param books 
     * @return 
     * @throws Exception 
     */  
    public String serialize(List<Book> books) throws Exception;  
}  

