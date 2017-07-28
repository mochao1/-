package com.mc.happybuy;

import adapter.HorizontalScrollViewAdapter;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import constant.AllConstant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utils.ExitUtils;

public class BuyActivity extends Activity {

  @BindView(R.id.back) ImageView back;
  @BindView(R.id.id_content) ImageView idContent;
  @BindView(R.id.id_horizontalScrollView) MyHorizontalScrollView myScrollView;
  @BindView(R.id.et_consignee) TextView etConsignee;
  private HorizontalScrollViewAdapter mAdapter;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wait_ship);
    ButterKnife.bind(this);
    ExitUtils.activities.add(this);
    mAdapter = new HorizontalScrollViewAdapter(this, AllConstant.BUYSHOP);
    myScrollView.setCurrentImageChangeListener(
        new MyHorizontalScrollView.CurrentImageChangeListener() {
          @Override public void onCurrentImgChanged(int position, View viewIndicator) {
            Glide.with(BuyActivity.this)
                .load(AllConstant.BUYSHOP.get(position).getUrl())
                .into(idContent);
            etConsignee.setText(AllConstant.BUYSHOP.get(position).getConsigneeInfo().toString());
            viewIndicator.setBackgroundColor(Color.parseColor("#AA024DA4"));
          }
        });
    myScrollView.setOnItemClickListener(new MyHorizontalScrollView.OnItemClickListener() {
      @Override public void onClick(View view, int pos) {
        Glide.with(BuyActivity.this).load(AllConstant.BUYSHOP.get(pos).getUrl()).into(idContent);
        view.setBackgroundColor(Color.parseColor("#AA024DA4"));
        etConsignee.setText(AllConstant.BUYSHOP.get(pos).getConsigneeInfo().toString());
      }
    });
    //设置适配器
    myScrollView.initDatas(mAdapter);
  }
}
