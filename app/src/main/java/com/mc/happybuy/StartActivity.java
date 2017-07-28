package com.mc.happybuy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import constant.AllConstant;
import java.util.ArrayList;
import java.util.List;
import javabean.ImgEntity;
import okhttp3.Call;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import service.MyService;
import utils.ExitUtils;
import utils.T;

public class StartActivity extends Activity {
  @BindView(R.id.imageView) ImageView imageView;
  @BindView(R.id.titleWord) TextView titleWord;
  @BindView(R.id.enter) Button enter;
  List<ImgEntity> imgEntities;
  @BindView(R.id.time) TextView time;
  private CountDownTimer mTimer;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_start);
    ButterKnife.bind(this);
    ExitUtils.activities.add(this);
    initService();
    getData();
  }

  private void initService() {
    startService(new Intent(StartActivity.this,MyService.class));
  }

  //倒计时显示
  private void showTime() {
    if (mTimer != null) {
      mTimer.cancel();
      mTimer = null;
    }
    mTimer = new CountDownTimer(6 * 1000, 1000) {
      int second = 5;

      @Override public void onTick(long millisUntilFinished) {
        mCountHandler.obtainMessage(1, second--).sendToTarget();
      }

      @Override public void onFinish() {
        if (mTimer != null) {
          mTimer.cancel();
          mTimer = null;
        }
        startActivity(new Intent(StartActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.layoutin, R.anim.layoutout);
        finish();
      }
    };
    mTimer.start();
  }

  private Handler mCountHandler = new Handler() {
    @Override public void handleMessage(Message msg) {
      super.handleMessage(msg);
      time.setText(msg.obj + "秒");
    }
  };
  private void getData() {
    OkHttpUtils.get().url(AllConstant.imgUrl).build().execute(new StringCallback() {
      @Override public void onError(Call call, Exception e, int id) {
        T.showShort(StartActivity.this, "连接失败");
      }

      @Override public void onResponse(String response, int id) {
        try {
          final JSONObject res = new JSONObject(response);
          JSONArray arr = res.getJSONArray("images");
          if (arr.length() != 0) {
            imgEntities = new ArrayList<ImgEntity>();
            for (int i = 0; i < arr.length(); i++) {
              JSONObject tmp = arr.getJSONObject(i);
              imgEntities.add(new ImgEntity(tmp.getString("image")));
            }
            Glide.with(StartActivity.this).load(imgEntities.get(4).getImageUrl()).into(imageView);
            new Handler().postDelayed(new Runnable() {
              @Override public void run() {
                Shader shader =
                    new LinearGradient(0, 0, titleWord.getRight(), 300, Color.LTGRAY, Color.GREEN,
                        Shader.TileMode.MIRROR);
                titleWord.getPaint().setShader(shader);
                titleWord.setVisibility(View.VISIBLE);
                enter.setVisibility(View.VISIBLE);
                showTime();
              }
            }, 100);
          }
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    });
  }

  @OnClick(R.id.enter) public void onViewClicked() {
    if (mTimer != null) {
      mTimer.cancel();
      mTimer = null;
    }
    startActivity(new Intent(this, MainActivity.class));
    overridePendingTransition(R.anim.layoutin, R.anim.layoutout);
    finish();
  }
}
