package javabean;

import java.io.Serializable;

public class LikeInformation implements Serializable{
  String url;
  String price;
  String shopInfo;
  String payPeopleNumber;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
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

  public String getPayPeopleNumber() {
    return payPeopleNumber;
  }

  public void setPayPeopleNumber(String payPeopleNumber) {
    this.payPeopleNumber = payPeopleNumber;
  }

  public LikeInformation() {
    super();
    // TODO Auto-generated constructor stub
  }

  public LikeInformation(String url, String price, String shopInfo, String payPeopleNumber) {
    super();
    this.url = url;
    this.price = price;
    this.shopInfo = shopInfo;
    this.payPeopleNumber = payPeopleNumber;
  }
}
