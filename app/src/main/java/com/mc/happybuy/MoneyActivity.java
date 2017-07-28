package com.mc.happybuy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import constant.AllConstant;
import fragment.PersoncenterFragment;
import okhttp3.Call;
import utils.ExitUtils;
import utils.T;

public class MoneyActivity extends Activity {

  @BindView(R.id.moneyNum) EditText moneyNum;
  int num;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_money);
    ButterKnife.bind(this);
    ExitUtils.activities.add(this);
  }

  @OnClick({ R.id.back, R.id.sure }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.back:
        finish();
        break;
      case R.id.sure:
        num=Integer.parseInt(moneyNum.getText().toString().trim());
        addMoney();
        break;
    }
  }

  private void addMoney() {
    OkHttpUtils.get()
        .url(AllConstant.addMoneytUrl)
        .addParams("Balance", ""+num)
        .addParams("UserName", AllConstant.USERNAME)
        .build()
        .execute(new StringCallback() {
          @Override public void onError(Call call, Exception e, int id) {
            T.showShort(MoneyActivity.this, "连接失败！");
          }

          @Override public void onResponse(String response, int id) {
            if("YES".equals(response)){
            T.showShort(MoneyActivity.this, "充值成功！");
              int sy=Integer.parseInt(AllConstant.MONEY);
              AllConstant.MONEY=sy+num+"";
              Intent intent = new Intent();
              intent.putExtra("money", AllConstant.MONEY);
              setResult(UserInfoActivity.CODE_RESULT_MONEY, intent);
              finish();
            }
            else if("No".equals(response)){
              T.showShort(MoneyActivity.this, "充值失败！");
            }
          }
        });
  }
}
