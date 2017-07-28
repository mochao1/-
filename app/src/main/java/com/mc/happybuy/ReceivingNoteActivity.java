package com.mc.happybuy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import constant.AllConstant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javabean.BaseShop;
import javabean.ConsigneeInfo;
import javabean.LikeInformation;
import javabean.Shoppingcar;
import okhttp3.Call;
import utils.ExitUtils;
import utils.T;

public class ReceivingNoteActivity extends Activity {

  @BindView(R.id.et_consignee) EditText etConsignee;
  @BindView(R.id.et_phonenum) EditText etPhonenum;
  @BindView(R.id.district) Spinner district;
  @BindView(R.id.district_detail) EditText districtDetail;
  private String diqu,name,diqu_detail,phone,price;
  int COME_In;
  boolean b,c;
  LikeInformation shop;
  List<Shoppingcar> checked_list;
  List<BaseShop> list_shop;
  String shopInfo="";
  String str;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_receiving_note);
    ButterKnife.bind(this);
    ExitUtils.activities.add(this);
    if(getIntent().getStringExtra("price").equals("main_page")){
      shop= (LikeInformation) getIntent().getSerializableExtra("shop");
      list_shop= (List<BaseShop>) getIntent().getSerializableExtra("list_shop");
      shopInfo=shop.getShopInfo();
      price=shop.getPrice();
      COME_In=1;
    }
    else if(getIntent().getStringExtra("price").equals("shoppingcar")){
      price=getIntent().getStringExtra("total");
      checked_list= (List<Shoppingcar>) getIntent().getSerializableExtra("list");
      list_shop= (List<BaseShop>) getIntent().getSerializableExtra("list_shop");
      for(int i=0;i<checked_list.size();i++){
        shopInfo=shopInfo+checked_list.get(i).getShopInfo()+"/";
      }
      COME_In=2;
    }
  }
  @OnClick(R.id.btn_sure) public void onViewClicked() {
    ConsigneeInfo shipuser;
    diqu=district.getSelectedItem().toString();
    name=etConsignee.getText().toString().trim();
    diqu_detail=districtDetail.getText().toString().trim();
    phone=etPhonenum.getText().toString().trim();
    Pattern pattern = Pattern.compile("1[3|4|5|7|8][0-9]{9}");
    Matcher matcher = pattern.matcher(phone);
    b = matcher.find();
    c=phone.length()==11;
    if(name.equals("")||diqu_detail.equals("")||phone.equals("")){
      T.showShort(ReceivingNoteActivity.this,"请填写好信息！");
      return;
    }
    else {
      if (!b||!c) {
        T.showShort(ReceivingNoteActivity.this, "请正确填写手机格式！");
      } else {
        shipuser=new ConsigneeInfo(name,phone,diqu+diqu_detail);
        //AllConstant.SHIPUSER.add(shipuser);
        for(int i=0;i<list_shop.size();i++){
          list_shop.get(i).setConsigneeInfo(shipuser);
          AllConstant.BUYSHOP.add(list_shop.get(i));
        }
        int yue = 0;
        if (COME_In == 1) {
          yue = (Integer.parseInt(AllConstant.MONEY) - Integer.parseInt(price));
        } else if (COME_In == 2) {
          yue = (Integer.parseInt(AllConstant.MONEY) - Integer.parseInt(price));
        }
        final String yue_price = yue + "";
        OkHttpUtils.get()
            .url(AllConstant.updateMoneytUrl)
            .addParams("Balance", "" + yue_price)
            .addParams("UserName", AllConstant.USERNAME)
            .addParams("Info",shopInfo)
            .build()
            .execute(new StringCallback() {
              @Override public void onError(Call call, Exception e, int id) {
                T.showShort(getBaseContext(), "连接失败！");
              }

              @Override public void onResponse(String response, int id) {
                if ("YES".equals(response)) {
                  // 发送广播
                  Intent intent = new Intent();
                  intent.putExtra("shopInfo",shopInfo);
                  intent.setAction(AllConstant.SHOP_SUCCESS);
                  sendBroadcast(intent);
                  AllConstant.MONEY = yue_price;
                  T.showShort(getBaseContext(), "购买成功！");
                  T.showShort(getBaseContext(), "总共消费：" + price + "元");
                  int num= (int) (Math.random()*100);
                  if(num<80){
                    startActivity(new Intent(ReceivingNoteActivity.this,RubberActivity.class));
                    finish();
                    return;
                  }
                } else if ("No".equals(response)) {
                  T.showShort(getBaseContext(), "购买失败！");
                }
                finish();
              }
            });
      }
    }
  }
}
