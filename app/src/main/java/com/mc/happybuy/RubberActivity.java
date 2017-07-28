package com.mc.happybuy;

import android.app.Activity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import constant.AllConstant;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.ExitUtils;
import utils.T;

public class RubberActivity extends Activity {
  ScratchTextView content;
  List<String> list;
  List<String> getPrize=new ArrayList<String>();
  int num, count, check;
  @BindView(R.id.marqueeview) MarqueeView marqueeview;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_guagua);
    ButterKnife.bind(this);
    ExitUtils.activities.add(this);
    AllConstant.sign=0;
    content = ((ScratchTextView) findViewById(R.id.content));
    setGuajaing();
    sethuojaingInfo();
    content.initScratchCard(0xFFFF00FF, 20, 1f);
  }

  private void sethuojaingInfo() {
    OkHttpUtils.get().url(AllConstant.GetPrizetUrl).build().execute(new StringCallback() {
      @Override public void onError(Call call, Exception e, int id) {
        T.showShort(RubberActivity.this, "连接失败");
      }

      @Override public void onResponse(String response, int id) {
        try {
          final JSONObject res = new JSONObject(response);
          JSONArray arr = res.getJSONArray("Prizes");
          if (arr.length() != 0) {
            for (int i = 0; i < arr.length(); i++) {
              JSONObject tmp = arr.getJSONObject(i);
              getPrize.add(tmp.getString("Prize"));
            }
          }
        } catch (JSONException e) {
          e.printStackTrace();
        }
        marqueeview.startWithList(getPrize);
      }
    });
  }

  private void setGuajaing() {
    list = new ArrayList<>();
    for (int i = 0; i < getResources().getStringArray(R.array.choujiang).length; i++) {
      list.add(getResources().getStringArray(R.array.choujiang)[i]);
    }
    num = (int) (Math.random() * 100);
    if (num < 50) {
      content.setText(list.get(0));
    } else if (num >= 50 && num < 70) {
      count = (int) (Math.random() * 8 + 1);
      content.setText(list.get(count));
    } else if (num >= 70 && num < 90) {
      count = (int) (Math.random() * 4 + 9);
      content.setText(list.get(count));
    } else if (num >= 90 && num < 97) {
      count = (int) (Math.random() * 7 + 13);
      content.setText(list.get(count));
    } else if (num >= 97 && num < 100) {
      content.setText(list.get(20));
    }
  }

  @OnClick(R.id.getprize) public void onViewClicked() {
    if (AllConstant.sign >=4) {
    check++;
    if (check >= 2) {
      T.showShort(RubberActivity.this, "你已经领取过奖啦！");
    } else {
      if (content.getText().equals("谢谢惠顾")) {
        T.showShort(RubberActivity.this, "很遗憾，你没有中奖！");
      } else {
        OkHttpUtils.get().url(AllConstant.PrizetUrl).addParams("UserName",AllConstant.USERNAME).addParams("Prize",content.getText().toString()).build().execute(new StringCallback() {
          @Override public void onError(Call call, Exception e, int id) {
            T.showShort(RubberActivity.this, "连接失败！");
          }

          @Override public void onResponse(String response, int id) {
            if(response.equals("YES")){
              T.showShort(RubberActivity.this, "恭喜你获得：" + content.getText());
              getPrize.add(AllConstant.USERNAME+":获得"+content.getText());
            }
          }
        });
        marqueeview.startWithList(getPrize);
      }
    }
  }
  else {
      T.showShort(RubberActivity.this, "你还没有刮奖啦！");
    }
}
}
