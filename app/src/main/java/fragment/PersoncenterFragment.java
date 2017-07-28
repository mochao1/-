package fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.mc.happybuy.BuyItemActivity;
import com.mc.happybuy.LogoActivity;
import com.mc.happybuy.R;
import com.mc.happybuy.UserInfoActivity;
import constant.AllConstant;
import javabean.UserInformation;
import utils.T;

public class PersoncenterFragment extends Fragment {
  @BindView(R.id.touxiang) ImageView touxiang;
  @BindView(R.id.name) TextView name;
  Unbinder unbinder;
  public final static int PERSON_INFO = 0x001;
  public final static int PERSON_IMG = 0x002;
  UserInformation user;
  Bitmap img;
  View v2;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.activity_person, null);
    v2 = inflater.inflate(R.layout.pupup, null);
    unbinder = ButterKnife.bind(this, v);
    name.setText(AllConstant.USERNAME);
    if (img != null) {
      touxiang.setImageBitmap(img);
    } else if (img == null) {
      touxiang.setImageDrawable(getResources().getDrawable(R.mipmap.touxiang));
    }
    return v;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({ R.id.name, R.id.set }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.name:
        String showName = name.getText().toString();
        if (getActivity().getResources().getString(R.string.name).equals(showName)) {
          Intent intent = new Intent(getActivity(), LogoActivity.class);
          startActivityForResult(intent, PERSON_INFO);
        } else {
          //UserInformation a= SharePreUtil.getObject(AllConstant.PRE,getActivity(),AllConstant.USER,UserInformation.class);
          Intent intent = new Intent(getActivity(), UserInfoActivity.class);
          Bundle bundle = new Bundle();
          bundle.putSerializable("user", user);
          bundle.putParcelable("bitmap", img);
          intent.putExtras(bundle);
          startActivityForResult(intent, PERSON_IMG);
        }
        break;
      case R.id.set:
        showMenu(view);
        break;
    }
  }

  private void showMenu(View v) {
    /**
     * 创建弹出窗口对象 参数1：窗口界面 参数2和3：窗口宽高 参数4:窗口能否得到焦点
     */
    View view = v2;
    final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT, true);
    /**
     * 指定位置，显示窗口
     */
    popupWindow.showAtLocation(v, Gravity.START | Gravity.TOP, 0, 0);
    TextView exit = (TextView) view.findViewById(R.id.textView1);
    TextView zhuxiao = (TextView) view.findViewById(R.id.textView2);
    exit.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        AllConstant.USERNAME = "登录/注册";
        AllConstant.BUYSHOP.clear();
        AllConstant.sign = 0;
        name.setText(AllConstant.USERNAME);
        popupWindow.dismiss();
      }
    });
    zhuxiao.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        AllConstant.USERNAME = "登录/注册";
        AllConstant.BUYSHOP.clear();
        AllConstant.sign = 0;
        popupWindow.dismiss();
        startActivity(new Intent(getActivity(), LogoActivity.class));
      }
    });
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == PersoncenterFragment.PERSON_INFO) {
      String userName = data.getStringExtra("name");
      user = (UserInformation) data.getSerializableExtra("user");
      name.setText(userName);
    }
    if (resultCode == PersoncenterFragment.PERSON_IMG) {
      img = data.getParcelableExtra("bitmap");
      touxiang.setImageBitmap(img);
    }
  }

  @OnClick({
      R.id.fukuan, R.id.fahuo, R.id.shouhuo, R.id.pj, R.id.tuikuan, R.id.tianmao, R.id.mayi,
      R.id.huiyuan, R.id.xiaomi, R.id.wemda, R.id.quanzi, R.id.pingjia, R.id.fenxiang, R.id.weibo,
      R.id.weixin, R.id.tengxun, R.id.zhifubao
  }) public void onViewClick(View view) {
    switch (view.getId()) {
      case R.id.shouhuo:
        startActivity(new Intent(getActivity(), BuyItemActivity.class));
        break;
      case R.id.fukuan:
      case R.id.fahuo:
      case R.id.pj:
      case R.id.tuikuan:
      case R.id.tianmao:
      case R.id.mayi:
      case R.id.huiyuan:
      case R.id.xiaomi:
      case R.id.wemda:
      case R.id.quanzi:
      case R.id.pingjia:
      case R.id.fenxiang:
      case R.id.weibo:
      case R.id.weixin:
      case R.id.tengxun:
      case R.id.zhifubao:
        T.showShort(getContext(),"暂时没有开通此功能");
        break;
    }
  }
}
