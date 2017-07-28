package com.mc.happybuy;

import adapter.ShopItemAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v17.leanback.widget.HorizontalGridView;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constant.AllConstant;
import utils.ExitUtils;

public class BuyItemActivity extends Activity {

  @BindView(R.id.et_consignee) TextView etConsignee;
  @BindView(R.id.id_content) ImageView idContent;
  @BindView(R.id.shop_item) HorizontalGridView shopItem;
  ShopItemAdapter mAdapter;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_buy_item);
    ButterKnife.bind(this);
    ExitUtils.activities.add(this);
    mAdapter=new ShopItemAdapter(this, AllConstant.BUYSHOP,etConsignee,idContent);
    shopItem.setAdapter(mAdapter);
  }

  @OnClick(R.id.back) public void onViewClicked() {
    finish();
  }
}
