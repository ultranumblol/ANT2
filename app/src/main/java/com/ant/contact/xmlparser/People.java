package com.ant.contact.xmlparser;

public class People {
	private int id;//��ϵ��id
	private int sid;//����id
	private String name;//��ϵ������
	private String phone;//��ϵ�˵绰
	private String rank;//����
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
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
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	@Override  
    public String toString() {  
        return "��ϵ��: [����=" + name + ", �绰=" + phone + "]";  
    }  
}
