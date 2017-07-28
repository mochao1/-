package com.mc.happybuy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import utils.ExitUtils;

public class ScannerActivity extends Activity implements ZXingScannerView.ResultHandler {

  private ZXingScannerView mZXingScannerView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mZXingScannerView = new ZXingScannerView(this);
    setContentView(mZXingScannerView);
    ExitUtils.activities.add(this);
  }

  @Override protected void onResume() {
    super.onResume();
    mZXingScannerView.setResultHandler(this); // 设置处理结果回调
    mZXingScannerView.startCamera(); // 打开摄像头
  }

  @Override protected void onPause() {
    super.onPause();
    mZXingScannerView.stopCamera(); // 活动失去焦点的时候关闭摄像头
  }

  @Override public void handleResult(Result result) { // 实现回调接口，将数据回传并结束活动
    Intent data = new Intent();
    data.putExtra("text", result.getText());
    setResult(RESULT_OK, data);
    finish();
  }

}