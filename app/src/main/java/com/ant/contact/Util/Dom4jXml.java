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
	        // ����saxReader����  
	        SAXReader reader = new SAXReader();  
	        // ͨ��read������ȡһ���ļ� ת����Document����  
	        //Document document = reader.read(new File("src/ant.xml")); 
	        Document document = reader.read(url); 
	        //��ȡ���ڵ�Ԫ�ض���  ��ȡ�ĵ��ĸ��ڵ�.
	        Element node = document.getRootElement();  
	        //�������е�Ԫ�ؽڵ�  
	        listNodes(node);  
	  
	      /*  // ��ȡNewDataSetԪ�ؽڵ��У��ӽڵ�����ΪTableԪ�ؽڵ㡣  
	        Element element = node.element("Table");  
	        //��ȡelement��id���Խڵ����  
	        Attribute attr = element.attribute("id");  
	        //ɾ������  
	        element.remove(attr);  
	        //����µ�����  
	        element.addAttribute("name", "����");  
	        // �ں�¥��Ԫ�ؽڵ�����ӳ���Ԫ�صĽڵ�  
	        Element newElement = element.addElement("pidname");  
	        newElement.setText("���ϼ���");  
	        //��ȡelement�е�����Ԫ�ؽڵ����  
	        Element author = element.element("phone");  
	        //ɾ��Ԫ�ؽڵ�  
	        boolean flag = element.remove(author);  
	        //����true����ɾ���ɹ�������ʧ��  
	        System.out.println(flag);  
	        //���CDATA����  
	        element.addCDATA("��¥�Σ���һ������С˵.");  
	        // д�뵽һ���µ��ļ���  
	       // writer(document);  
*/	  
	    }  
	
	
	
	 /** 
     * ������ǰ�ڵ�Ԫ�����������(Ԫ�ص�)�ӽڵ� 
     *  
     * @param node 
     */  
    public void listNodes(Element node) {  
        System.out.println("��ǰ�ڵ�����ƣ���" + node.getName());  
        // ��ȡ��ǰ�ڵ���������Խڵ�  
        List<Attribute> list = node.attributes();  
        // �������Խڵ�  
        for (Attribute attr : list) {  
            System.out.println(attr.getText() + "-----" + attr.getName()  
                    + "---" + attr.getValue());  
        }  
  
        if (!(node.getTextTrim().equals(""))) {  
            System.out.println("�ı����ݣ�������" + node.getText());  
        }  
  
        // ��ǰ�ڵ������ӽڵ������  
        Iterator<Element> it = node.elementIterator();  
        // ����  
        while (it.hasNext()) {  
            // ��ȡĳ���ӽڵ����  
            Element e = it.next();  
            // ���ӽڵ���б���  
            listNodes(e);  
        }  
    }






	public void parse1() {
		//String url = "http://hr.chinaant.com/xmlhandler.aspx?username=123&password=123&sign=5B7D1F8CEFBF1C80AEB73329C8A378A1";
        // ����saxReader����  
        SAXReader reader = new SAXReader();  
        // ͨ��read������ȡһ���ļ� ת����Document����  
        //Document document = reader.read(new File("src/ant.xml")); 
        Document document = null;
		try {
			document = reader.read(url);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        //��ȡ���ڵ�Ԫ�ض���  ��ȡ�ĵ��ĸ��ڵ�.
        Element node = document.getRootElement();  
        //�������е�Ԫ�ؽڵ�  
       // listNodes(node);  
  
      /*  // ��ȡNewDataSetԪ�ؽڵ��У��ӽڵ�����ΪTableԪ�ؽڵ㡣  
        Element element = node.element("Table");  
        //��ȡelement��id���Խڵ����  
        Attribute attr = element.attribute("id");  
        //ɾ������  
        element.remove(attr);  
        //����µ�����  
        element.addAttribute("name", "����");  
        // �ں�¥��Ԫ�ؽڵ�����ӳ���Ԫ�صĽڵ�  
        Element newElement = element.addElement("pidname");  
        newElement.setText("���ϼ���");  
        //��ȡelement�е�����Ԫ�ؽڵ����  
        Element author = element.element("phone");  
        //ɾ��Ԫ�ؽڵ�  
        boolean flag = element.remove(author);  
        //����true����ɾ���ɹ�������ʧ��  
        System.out.println(flag);  
        //���CDATA����  
        element.addCDATA("��¥�Σ���һ������С˵.");  
        // д�뵽һ���µ��ļ���  
       // writer(document);  
*/	  
		
	}  
}
