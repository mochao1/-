package javabean;

import java.io.Serializable;

public class Shoppingcar implements Serializable{
private String imageUrl;
private String price;
private String shopInfo;
private Boolean bChecked;
private String count="0";
public String getCount() {
	return count;
}
public void setCount(String count) {
	this.count = count;
}
public String getImageUrl() {
	return imageUrl;
}
public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
}
public String getPrice() {
	return price;
}
public void setPrice(String price) {
	this.price = price;
}
public String getShopInfo() {
	return shopInfo;
}
public void setShopInfo(String shopInfo) {
	this.shopInfo = shopInfo;
}
public Boolean getbChecked() {
	return bChecked;
}
public void setbChecked(Boolean bChecked) {
	this.bChecked = bChecked;
}
public Shoppingcar() {
	super();
	// TODO Auto-generated constructor stub
}
public Shoppingcar(String imageUrl, String price, String shopInfo,
		Boolean bChecked) {
	super();
	this.imageUrl = imageUrl;
	this.price = price;
	this.shopInfo = shopInfo;
	this.bChecked = bChecked;
}

}
