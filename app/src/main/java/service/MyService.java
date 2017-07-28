package service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import com.mc.happybuy.R;
import constant.AllConstant;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MyService extends Service {
  private CountDownTimer mTimer;
  private int num = 1; // 通知的编号
  private int id1 = 0; // 通知的id
  Bitmap img;
  String ShopInfo;
  private BroadcastReceiver mReceiver=new BroadcastReceiver() {
    @Override public void onReceive(Context context, Intent intent) {
      String action=intent.getAction();
      if(action.equals(AllConstant.SHOP_SUCCESS)){
        ShopInfo=intent.getStringExtra("shopInfo");
        startTimedown();
      }
    }
  };

  @Override public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override public void onCreate() {
    super.onCreate();
    IntentFilter filter = new IntentFilter();
    filter.addAction(AllConstant.SHOP_SUCCESS);
    registerReceiver(mReceiver, filter);
  }
  @Override public void onDestroy() {
    super.onDestroy();
    unregisterReceiver(mReceiver);
  }
  private void startTimedown() {
    if (mTimer != null) {
      mTimer.cancel();
      mTimer = null;
    }
    mTimer = new CountDownTimer(10 * 1000, 1000) {
      int second = 9;

      @Override public void onTick(long millisUntilFinished) {
        mCountHandler.obtainMessage(1, second--).sendToTarget();
      }

      @Override public void onFinish() {
        if (mTimer != null) {
          mTimer.cancel();
          mTimer = null;
        }
        //img=getBitmap();
        myNotification();
      }
    };
    mTimer.start();
  }
  //private Bitmap getBitmap(){
  //  Bitmap bm = null;
  //  try {
  //    URL iconUrl = new URL(shop.getUrl());
  //    URLConnection conn = iconUrl.openConnection();
  //    HttpURLConnection http = (HttpURLConnection) conn;
  //
  //    int length = http.getContentLength();
  //
  //    conn.connect();
  //    // 获得图像的字符流
  //    InputStream is = conn.getInputStream();
  //    BufferedInputStream bis = new BufferedInputStream(is, length);
  //    bm = BitmapFactory.decodeStream(bis);
  //    bis.close();
  //    is.close();// 关闭流
  //  }
  //  catch (Exception e) {
  //    e.printStackTrace();
  //  }
  //  return bm;
  //}
  private void myNotification(){
    NotificationCompat.Builder builder = new NotificationCompat.Builder(
        this);

    builder.setSmallIcon(R.mipmap.shop);// 设置小图片,可选，在右下角
    builder.setContentTitle("卖家已发货");// 设置标题
    builder.setContentText(ShopInfo);// 设置内容
    builder.setNumber(num++);// 设置通知编号
    builder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
        R.mipmap.shop));// 设置大图片 在左边
    builder.setAutoCancel(true);// 自己维护通知的消失
    Notification notification = builder.build();// 创建通知
    // 获取通知管理器对象,用于帮助通知的显示，取消等等
    NotificationManager notificationManager = (NotificationManager) this
        .getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(id1++, notification);// 显示通知

  }
  private Handler mCountHandler = new Handler() {
    @Override public void handleMessage(Message msg) {
      super.handleMessage(msg);

    }
  };
}
