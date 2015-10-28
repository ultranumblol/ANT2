package com.ant.contact.xmlparser;

import java.io.InputStream;
import java.util.List;

public interface PeopleParser {
	/** 
     * ���������� �õ�People���󼯺� 
     * @param is 
     * @return 
     * @throws Exception 
     */  
    public List<People> parse(InputStream is) throws Exception;  
      
    /** 
     * ���л�People���󼯺� �õ�XML��ʽ���ַ��� 
     * @param people
     * @return 
     * @throws Exception 
     */  
    public String serialize(List<People> people) throws Exception; 
}
