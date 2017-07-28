package javabean;

import java.io.Serializable;

public class UserInformation implements Serializable{
	private String Username;
	private String phonenumber;
	private String price;
	private String address;

	public String getSex() {return sex;}
	public void setSex(String sex) {this.sex = sex;}
	private String sex;
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public UserInformation(String Username, String phonenumber, String price,
			String address,String Sex) {
		super();
		this.Username = Username;
		this.phonenumber = phonenumber;
		this.price = price;
		this.address = address;
		this.sex = Sex;
	}
	public UserInformation() {
		super();
		// TODO Auto-generated constructor stub
	}

}
