package com.mc.happybuy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import utils.ExitUtils;

public class SaomiaoActivity extends Activity {

  @BindView(R.id.showtv) TextView showtv;
  @BindView(R.id.showweb) WebView showweb;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_saomiao);
    ButterKnife.bind(this);
    ExitUtils.activities.add(this);
    scanCode();
  }
  public void scanCode(){
    startActivityForResult(new Intent(this, ScannerActivity.class), 1);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK) {
      showtv.setText(data.getStringExtra("text")); // 显示识别到的文字
      showweb.loadUrl(data.getStringExtra("text")); // 将识别的内容当作网址加载到WebView
    }
  }
}
