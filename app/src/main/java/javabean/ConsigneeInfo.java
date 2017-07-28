package javabean;

import java.io.Serializable;

public class ConsigneeInfo implements Serializable{
	private String Username;
	private String phonenumber;
	private String address;

	public  String getUsername() {
		return Username;
	}
	public void setUsername(String  Username) {
		this.Username = Username;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public ConsigneeInfo(String Username, String phonenumber,String address) {
		super();
		this.Username = Username;
		this.phonenumber = phonenumber;
		this.address = address;
	}
	public ConsigneeInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override public String toString() {
		return "收货人：" + Username+"\t"+"联系电话："+phonenumber+"\n"+"收货地址："+address;
	}
}
