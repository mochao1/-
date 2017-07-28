package com.mc.happybuy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import constant.AllConstant;
import fragment.PersoncenterFragment;
import java.util.ArrayList;
import java.util.List;
import javabean.UserInformation;
import okhttp3.Call;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.ExitUtils;
import utils.SharePreUtil;
import utils.T;

public class LogoActivity extends Activity {

  UserInformation  user;
  int userID;
  String UserName, Sex, PassWord, PhoneNumber, Address, Balance;
  public static String usrename;
  public static String password;
  public static String musername;
  public static String phonenumber;
  public static String mprice;
  public static String maddress;
  @BindView(R.id.logo_username) EditText logoUsername;
  @BindView(R.id.logo_password) EditText logoPassword;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ExitUtils.activities.add(this);
    setContentView(R.layout.activity_logo);
    ButterKnife.bind(this);
  }

  @OnClick({ R.id.logo_username, R.id.logo_password, R.id.logo_go, R.id.log_register })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.logo_username:
        break;
      case R.id.logo_password:
        break;
      case R.id.logo_go:
        usrename = logoUsername.getText().toString()
            .trim();
        password = logoPassword.getText().toString().trim();
        OkHttpUtils.get()
            .url(AllConstant.userUrl)
            .addParams("UserName", usrename)
            .addParams("PassWord", password)
            .build().execute(new StringCallback() {
          @Override public void onError(Call call, Exception e, int id) {
            T.showShort(LogoActivity.this,"连接超时！");
          }

          @Override public void onResponse(String response, int id) {
            if("NO".equals(response)){
              T.showShort(LogoActivity.this,"用户名或密码错误！");
              return;
            }
            try {
              user=new UserInformation();
              JSONObject res = new JSONObject(response);
              JSONArray arr = res.getJSONArray("users");
              int length = arr.length();
              for(int i=0; i<length; i++) {
                JSONObject jsonObject = arr.getJSONObject(i);//根据下标得到json数组中的元素，即得到json对象
                String username = jsonObject.getString("UserName");
                String phoneNumber = jsonObject.getString("PhoneNumber");
                String price = jsonObject.getString("Balance");
                String address = jsonObject.getString("Address");
                String sex = jsonObject.getString("Sex");
                if(usrename.equals(username)){
                  AllConstant.USERNAME=username;
                  AllConstant.MONEY=price;
                  T.showShort(LogoActivity.this,"登录成功！");
                  user.setUsername(username);
                  user.setAddress(address);
                  user.setPhonenumber(phoneNumber);
                  user.setSex(sex);
                  user.setPrice(price);
                  //SharePreUtil.putObject(AllConstant.PRE,LogoActivity.this,AllConstant.USER,UserInformation.class,user);
                  Intent intent=new Intent();
                  Bundle bundle=new Bundle();
                  bundle.putSerializable("user",user);
                  intent.putExtras(bundle);
                  intent.putExtra("name",logoUsername.getText().toString());
                  setResult(PersoncenterFragment.PERSON_INFO,intent);
                  finish();
                  return;
                }
              }
            }catch (JSONException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
        });
        break;
      case R.id.log_register:
        startActivity(new Intent(LogoActivity.this,UserRegisterActivity.class));
        break;
    }
  }
}
