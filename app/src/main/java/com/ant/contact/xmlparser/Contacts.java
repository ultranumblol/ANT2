package com.ant.contact.xmlparser;

public class Contacts {
	private int id;//��ϵ��id
	private int pid;//��id
	private String name;//��ϵ������
	private String phone;//��ϵ�˵绰
	public int getId() {
		return id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override  
    public String toString() {  
        return "��ϵ��: [����=" + name + ", �绰=" + phone + "]";  
    }  
	
}
