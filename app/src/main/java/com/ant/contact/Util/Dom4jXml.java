package com.ant.contact.Util;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.Iterator;
import java.util.List;

public class Dom4jXml {
	private String url= "http://hr.chinaant.com/xmlhandler.aspx?username=123&password=123&sign=5B7D1F8CEFBF1C80AEB73329C8A378A1";
	
	 public Dom4jXml() {
		super();
		
	}



	public void parse() throws Exception {  
		 //String url = "http://hr.chinaant.com/xmlhandler.aspx?username=123&password=123&sign=5B7D1F8CEFBF1C80AEB73329C8A378A1";
	        // 创建saxReader对象  
	        SAXReader reader = new SAXReader();  
	        // 通过read方法读取一个文件 转换成Document对象  
	        //Document document = reader.read(new File("src/ant.xml")); 
	        Document document = reader.read(url); 
	        //获取根节点元素对象  获取文档的根节点.
	        Element node = document.getRootElement();  
	        //遍历所有的元素节点  
	        listNodes(node);  
	  
	      /*  // 获取NewDataSet元素节点中，子节点名称为Table元素节点。  
	        Element element = node.element("Table");  
	        //获取element的id属性节点对象  
	        Attribute attr = element.attribute("id");  
	        //删除属性  
	        element.remove(attr);  
	        //添加新的属性  
	        element.addAttribute("name", "作者");  
	        // 在红楼梦元素节点中添加朝代元素的节点  
	        Element newElement = element.addElement("pidname");  
	        newElement.setText("蚂蚁集体");  
	        //获取element中的作者元素节点对象  
	        Element author = element.element("phone");  
	        //删除元素节点  
	        boolean flag = element.remove(author);  
	        //返回true代码删除成功，否则失败  
	        System.out.println(flag);  
	        //添加CDATA区域  
	        element.addCDATA("红楼梦，是一部爱情小说.");  
	        // 写入到一个新的文件中  
	       // writer(document);  
*/	  
	    }  
	
	
	
	 /** 
     * 遍历当前节点元素下面的所有(元素的)子节点 
     *  
     * @param node 
     */  
    public void listNodes(Element node) {  
        System.out.println("当前节点的名称：：" + node.getName());  
        // 获取当前节点的所有属性节点  
        List<Attribute> list = node.attributes();  
        // 遍历属性节点  
        for (Attribute attr : list) {  
            System.out.println(attr.getText() + "-----" + attr.getName()  
                    + "---" + attr.getValue());  
        }  
  
        if (!(node.getTextTrim().equals(""))) {  
            System.out.println("文本内容：：：：" + node.getText());  
        }  
  
        // 当前节点下面子节点迭代器  
        Iterator<Element> it = node.elementIterator();  
        // 遍历  
        while (it.hasNext()) {  
            // 获取某个子节点对象  
            Element e = it.next();  
            // 对子节点进行遍历  
            listNodes(e);  
        }  
    }






	public void parse1() {
		//String url = "http://hr.chinaant.com/xmlhandler.aspx?username=123&password=123&sign=5B7D1F8CEFBF1C80AEB73329C8A378A1";
        // 创建saxReader对象  
        SAXReader reader = new SAXReader();  
        // 通过read方法读取一个文件 转换成Document对象  
        //Document document = reader.read(new File("src/ant.xml")); 
        Document document = null;
		try {
			document = reader.read(url);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        //获取根节点元素对象  获取文档的根节点.
        Element node = document.getRootElement();  
        //遍历所有的元素节点  
       // listNodes(node);  
  
      /*  // 获取NewDataSet元素节点中，子节点名称为Table元素节点。  
        Element element = node.element("Table");  
        //获取element的id属性节点对象  
        Attribute attr = element.attribute("id");  
        //删除属性  
        element.remove(attr);  
        //添加新的属性  
        element.addAttribute("name", "作者");  
        // 在红楼梦元素节点中添加朝代元素的节点  
        Element newElement = element.addElement("pidname");  
        newElement.setText("蚂蚁集体");  
        //获取element中的作者元素节点对象  
        Element author = element.element("phone");  
        //删除元素节点  
        boolean flag = element.remove(author);  
        //返回true代码删除成功，否则失败  
        System.out.println(flag);  
        //添加CDATA区域  
        element.addCDATA("红楼梦，是一部爱情小说.");  
        // 写入到一个新的文件中  
       // writer(document);  
*/	  
		
	}  
}
