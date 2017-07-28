package com.mc.happybuy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import constant.AllConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javabean.BaseShop;
import javabean.LikeInformation;
import okhttp3.Call;
import utils.ExitUtils;
import utils.T;

public class ShoppingInfoActivity extends Activity {
  @BindView(R.id.iv_pictures_commodity_information) ImageView ivPicturesCommodityInformation;
  @BindView(R.id.tv_details_commodity_information) TextView tvDetailsCommodityInformation;
  @BindView(R.id.tv_price_commodity_information) TextView tvPriceCommodityInformation;
  @BindView(R.id.tv_number_commodity_information) TextView tvNumberCommodityInformation;
  private String imageurl, tv_price_list_view_tab, shopInfo, payPeopleNumber;
  LikeInformation shoppingInfo;
  List<BaseShop> list_shop=new ArrayList<>();
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shopping_info);
    ButterKnife.bind(this);
    ExitUtils.activities.add(this);
    //获得商品的基本信息
    Intent intent = getIntent();
    imageurl = intent.getStringExtra("url");
    tv_price_list_view_tab = intent.getStringExtra("tv_price_list_view_tab");
    //信息
    shopInfo = intent.getStringExtra("shopInfo");
    payPeopleNumber = intent.getStringExtra("payPeopleNumber");
    shoppingInfo=new LikeInformation(imageurl,tv_price_list_view_tab,shopInfo,payPeopleNumber);
    //取出图片，并设置图片所带的值信息
    Glide.with(ShoppingInfoActivity.this).load(imageurl).into(ivPicturesCommodityInformation);
    tvDetailsCommodityInformation.setText(shopInfo);
    tvPriceCommodityInformation.setText("￥" + tv_price_list_view_tab + "/元");
    tvNumberCommodityInformation.setText("购买人数:" + payPeopleNumber);
  }

  @OnClick({
      R.id.im_back_commodity_information, R.id.btn_purchase_commodity_information,
      R.id.btn_add_commodity_information
  }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.im_back_commodity_information:
        finish();
        break;
      case R.id.btn_purchase_commodity_information:
        if(AllConstant.USERNAME != "登录/注册"){
          if(Integer.parseInt(AllConstant.MONEY)>=Integer.parseInt(tv_price_list_view_tab)){
            list_shop.add(new BaseShop(shoppingInfo.getUrl(),shoppingInfo.getPrice(),shoppingInfo.getShopInfo()));
            Intent intent=new Intent(getBaseContext(),ReceivingNoteActivity.class);
            intent.putExtra("price","main_page");
            Bundle bundle=new Bundle();
            bundle.putSerializable("shop",shoppingInfo);
            bundle.putSerializable("list_shop",(Serializable)list_shop);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
          }
          else {
            T.showShort(ShoppingInfoActivity.this, "余额不足，请充值！");
          }
        }
        else {
          T.showShort(ShoppingInfoActivity.this, "请登录再购买！");
        }
        break;
      case R.id.btn_add_commodity_information:
            addTocar();
        break;
    }
  }
  private void addTocar(){
    //判断用户名是否登录
    if(AllConstant.USERNAME != "登录/注册"){
      OkHttpUtils.get()
          .url(AllConstant.shoppingInserttUrl)
          .addParams("Picture", imageurl)
          .addParams("Price",tv_price_list_view_tab)
          .addParams("shopInfo",shopInfo)
          .build()
          .execute(new StringCallback() {
            @Override public void onError(Call call, Exception e, int id) {
              T.showShort(ShoppingInfoActivity.this, "连接失败！");
            }

            @Override public void onResponse(String response, int id) {
              T.showShort(ShoppingInfoActivity.this, "添加购物车成功！");
            }
          });


    }else{
      //否则弹出请登录提醒
      T.showShort(ShoppingInfoActivity.this, "请登录再购买！");
    }
  }
}
