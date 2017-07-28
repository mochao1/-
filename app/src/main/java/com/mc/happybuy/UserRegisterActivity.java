package com.mc.happybuy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import constant.AllConstant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.Call;
import utils.ExitUtils;
import utils.T;

public class UserRegisterActivity extends Activity {

  @BindView(R.id.et_username) EditText etUsername;
  @BindView(R.id.et_password) EditText etPassword;
  @BindView(R.id.sure_password) EditText surePassword;
  @BindView(R.id.et_phonenumber) EditText etPhonenumber;
  @BindView(R.id.et_shippingaddress) EditText etShippingaddress;
  @BindView(R.id.sex_man) RadioButton sexMan;
  @BindView(R.id.sex_women) RadioButton sexWomen;
  boolean b,c;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ExitUtils.activities.add(this);
    setContentView(R.layout.activity_register);
    ButterKnife.bind(this);

  }

  @OnClick({ R.id.sex_man, R.id.sex_women, R.id.btn_egistered })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.sex_man:
        break;
      case R.id.sex_women:
        break;
      case R.id.btn_egistered:
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String sure1_password = surePassword.getText().toString().trim();
        String phonenumber = etPhonenumber.getText().toString().trim();
        String shippingaddress = etShippingaddress.getText().toString().trim();
        String sex = "";
        Pattern pattern = Pattern.compile("1[3|4|5|7|8][0-9]{9}");
        Matcher matcher = pattern.matcher(phonenumber);
        b = matcher.find();
        c=phonenumber.length()==11;
        if (sexMan.isChecked()) {
          sex = "男";
        } else {
          sex = "女";
        }
        if ((sexMan.isChecked()||sexWomen.isChecked())&&!phonenumber.isEmpty()&&!sure1_password.isEmpty()&&!username.isEmpty()&&!password.isEmpty()&&!shippingaddress.isEmpty()) {
          if(password.equals(sure1_password)){
            if(b&&c) {
              OkHttpUtils.get()
                  .url(AllConstant.userInsertUrl)
                  .addParams("UserName", username)
                  .addParams("PassWord", password)
                  .addParams("Sex", sex)
                  .addParams("PhoneNumber", phonenumber)
                  .addParams("Address", shippingaddress)
                  .build()
                  .execute(new StringCallback() {
                    @Override public void onError(Call call, Exception e, int id) {
                      T.showShort(UserRegisterActivity.this, "连接超时！");
                    }

                    @Override public void onResponse(String response, int id) {
                      if(response.equals("YES")){
                        T.showShort(UserRegisterActivity.this, "注册成功！");
                        finish();
                      }
                      if(response.equals("NO"))
                      T.showShort(UserRegisterActivity.this, "用户名已存在！");
                    }
                  });
            }
            else {
              T.showShort(UserRegisterActivity.this,"请正确输入手机号格式！");
            }
          }
          else {
            T.showShort(UserRegisterActivity.this, "两次密码不一致！");
          }

        } else {
          T.showShort(UserRegisterActivity.this,"填写信息不能为空！！");
        }
        break;
    }
  }
}
