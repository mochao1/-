package javabean;

import java.io.Serializable;

public class BaseShop implements Serializable{
  String url;
  String price;
  String shopInfo;

  public ConsigneeInfo getConsigneeInfo() {
    return consigneeInfo;
  }

  public void setConsigneeInfo(ConsigneeInfo consigneeInfo) {
    this.consigneeInfo = consigneeInfo;
  }

  ConsigneeInfo consigneeInfo;
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

  public BaseShop() {
    super();
    // TODO Auto-generated constructor stub
  }

  public BaseShop(String url, String price, String shopInfo) {
    super();
    this.url = url;
    this.price = price;
    this.shopInfo = shopInfo;
  }

  @Override public String toString() {
    return "BaseShop{"
        + "url='"
        + url
        + '\''
        + ", price='"
        + price
        + '\''
        + ", shopInfo='"
        + shopInfo
        + '\''
        + ", consigneeInfo="
        + consigneeInfo
        + '}';
  }
}
