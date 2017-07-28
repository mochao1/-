package constant;

import android.content.Context;
import android.graphics.Bitmap;
import java.util.ArrayList;
import java.util.List;
import javabean.BaseShop;
import javabean.ConsigneeInfo;

/**
 * Created by messi.mo on 2017/5/10.
 */

public class AllConstant {
  public static final String url="http://192.168.43.92:8080/";
  public static final String imgUrl=url+"CrystalWashSrever/myyru.do";
  public static final String shoppingUrl=url+"CrystalWashSrever/Shoping.do";
  public static final String userUrl=url+"CrystalWashSrever/User.do";
  public static final String userInsertUrl=url+"CrystalWashSrever/UserInser.do";
  public static final String shoppingImgtUrl=url+"CrystalWashSrever/ShopingTypeServlet.do";
  public static final String shoppingInserttUrl=url+"CrystalWashSrever/ShopingInsertServlet.do";
  public static final String shoppingCartUrl=url+"CrystalWashSrever/ShoppingCarServlet.do";
  public static final String addMoneytUrl=url+"CrystalWashSrever/AddMoneyServlet.do";
  public static final String PrizetUrl=url+"CrystalWashSrever/PrizeServlet.do";
  public static final String GetPrizetUrl=url+"CrystalWashSrever/GetPrizeServlet.do";
  public static final String updateMoneytUrl=url+"CrystalWashSrever/userUpdateServlet.do";
  public static final String clearShoppingtUrl=url+"CrystalWashSrever/ShoppingCarDeleteServlet.do";
  public static String USERNAME="登录/注册";
  public static String MONEY="";
  public static String PRE="mc_pre";
  public static String USER="user";
  public static String SHOP_SUCCESS="shopping.success";
  public static final String ACTION_Broad = "com.mc.mBroad";
  public static final String ACTION_Service = "com.mc.mService";
  public static int sign=0;
  public static List<ConsigneeInfo> SHIPUSER=new ArrayList<>();
  public static List<BaseShop> BUYSHOP=new ArrayList<>();
}
