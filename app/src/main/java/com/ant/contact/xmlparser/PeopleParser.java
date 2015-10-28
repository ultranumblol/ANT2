package com.ant.contact.xmlparser;

import java.io.InputStream;
import java.util.List;

public interface PeopleParser {
	/** 
     * 解析输入流 得到People对象集合 
     * @param is 
     * @return 
     * @throws Exception 
     */  
    public List<People> parse(InputStream is) throws Exception;  
      
    /** 
     * 序列化People对象集合 得到XML形式的字符串 
     * @param people
     * @return 
     * @throws Exception 
     */  
    public String serialize(List<People> people) throws Exception; 
}
