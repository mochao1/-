package com.mc.happybuy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import constant.AllConstant;
import fragment.PersoncenterFragment;
import javabean.UserInformation;
import utils.ExitUtils;
import utils.T;

public class UserInfoActivity extends Activity {

  @BindView(R.id.username) TextView username;
  @BindView(R.id.yue) TextView yue;
  @BindView(R.id.dizhi) TextView dizhi;
  @BindView(R.id.phone) TextView phone;
  @BindView(R.id.sex) TextView sex;
  @BindView(R.id.imgtouxiang) ImageView imgtouxiang;
  UserInformation user;
  private static final int CODE_GALLERY_REQUEST = 0xa0;
  private static final int CODE_CAMERA_REQUEST = 0xa1;
  private static final int CODE_RESULT_REQUEST = 0xa2;
  public static final int CODE_RESULT_MONEY = 0xa3;
  // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
  private static int output_X = 480;
  private static int output_Y = 480;
  public Bitmap destBmp;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_userinfo);
    ExitUtils.activities.add(this);
    ButterKnife.bind(this);
    destBmp = getIntent().getParcelableExtra("bitmap");
    if (destBmp != null) {
      imgtouxiang.setImageBitmap(destBmp);
    }
    user = (UserInformation) getIntent().getSerializableExtra("user");
    setUserInfo();
  }

  private void setUserInfo() {
    yue.setText(AllConstant.MONEY+"元");
    dizhi.setText(user.getAddress());
    phone.setText(user.getPhonenumber());
    username.setText(user.getUsername());
    sex.setText(user.getSex());
  }

  @OnClick({ R.id.back, R.id.userMenu, R.id.touxiang, R.id.sex,R.id.chongzhi})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.back:
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable("bitmap", destBmp);
        intent.putExtras(bundle);
        setResult(PersoncenterFragment.PERSON_IMG, intent);
        finish();
        break;
      case R.id.userMenu:
        break;
      case R.id.touxiang:
        choseHeadImageFromGallery();
        break;
      case R.id.sex:
        break;
      case R.id.chongzhi:
        startActivityForResult(new Intent(UserInfoActivity.this,MoneyActivity.class),CODE_RESULT_MONEY);
        break;
    }
  }
  // 从本地相册选取图片作为头像
  private void choseHeadImageFromGallery() {
    Intent intentFromGallery = new Intent();
    // 设置文件类型
    intentFromGallery.setType("image/*");
    intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
  }

  /**
   * 裁剪原始的图片
   */
  public void cropRawPhoto(Uri uri) {

    Intent intent = new Intent("com.android.camera.action.CROP");
    intent.setDataAndType(uri, "image/*");

    // 设置裁剪
    intent.putExtra("crop", "true");

    // aspectX , aspectY :宽高的比例
    intent.putExtra("aspectX", 1);
    intent.putExtra("aspectY", 1);

    // outputX , outputY : 裁剪图片宽高
    intent.putExtra("outputX", output_X);
    intent.putExtra("outputY", output_Y);
    intent.putExtra("return-data", true);

    startActivityForResult(intent, CODE_RESULT_REQUEST);
  }

  /**
   * 提取保存裁剪之后的图片数据，并设置头像部分的View
   */
  private void setImageToHeadView(Intent intent) {
    Bundle extras = intent.getExtras();
    if (extras != null) {
      Bitmap photo = extras.getParcelable("data");
      destBmp = roundBitmap(photo, 0);
      imgtouxiang.setImageBitmap(destBmp);
    }
  }

  public Bitmap roundBitmap(Bitmap source, int r) {
    //获取原图的宽高
    int srcW = source.getWidth();
    int srcH = source.getHeight();

    //得到中心点坐标
    int x = srcW / 2;
    int y = srcH / 2;

    int radius = 0;
    //得到半径
    if (r <= 1) {
      radius = Math.min(srcW, srcH) / 2;
    } else {
      int side = Math.min(srcW, srcH) / 2;
      radius = Math.min(side, r);
    }

    //创建一个和原图一样大小的Bitmap
    Bitmap dest = Bitmap.createBitmap(srcW, srcH, Bitmap.Config.ARGB_8888);
    //获取新Bitmap的Canvas
    Canvas canvas = new Canvas(dest);

    //在新Bitmap的画布上画圆
    Paint paint = new Paint();
    paint.setColor(Color.argb(255, 255, 0, 0));
    //消除锯齿
    paint.setAntiAlias(true);
    //设置填充封闭区域
    paint.setStyle(Paint.Style.FILL);
    canvas.drawCircle(x, y, radius, paint);

    //设置Bitmap重叠方式
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

    //将原图与目前已经绘制的圆形求交集，原图内容在上，圆形背景在下
    canvas.drawBitmap(source, 0, 0, paint);

    return dest;
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

    // 用户没有进行有效的设置操作，返回
    if (resultCode == RESULT_CANCELED) {
      T.showShort(this, "取消");
      return;
    }
    if (requestCode == CODE_GALLERY_REQUEST) {
      cropRawPhoto(intent.getData());
    }
    if (requestCode == CODE_RESULT_MONEY) {
      yue.setText(AllConstant.MONEY+"元");
    }
    if (requestCode == CODE_RESULT_REQUEST) {
      if (intent != null) {
        setImageToHeadView(intent);
      }
    }
    super.onActivityResult(requestCode, resultCode, intent);
  }

}
