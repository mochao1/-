package com.mc.happybuy;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fragment.ClassfictionFragment;
import fragment.HomepagerFragment;
import fragment.PersoncenterFragment;
import fragment.ShoppingcarFragment;
import java.util.ArrayList;
import java.util.List;
import utils.ExitUtils;

public class MainActivity extends FragmentActivity {

  @BindView(R.id.homepage) RadioButton homepage;
  @BindView(R.id.classfication) RadioButton classfication;
  @BindView(R.id.shoppingcar) RadioButton shoppingcar;
  @BindView(R.id.personcenter) RadioButton personcenter;
  @BindView(R.id.buttom) RadioGroup mbuttom;
  @BindView(R.id.id_viewpager) ViewPager mViewpager;
  private List<Fragment> mFragments = new ArrayList<Fragment>();
  private long exitTime = 0;
  private FragmentPagerAdapter mAdapter;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ExitUtils.activities.add(this);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    init();
  }

  private void init() {
    mFragments.add(new HomepagerFragment());
    mFragments.add(new ClassfictionFragment());
    mFragments.add(new ShoppingcarFragment());
    mFragments.add(new PersoncenterFragment());
    mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

      @Override
      public int getCount() {
        return mFragments.size();
      }

      @Override
      public Fragment getItem(int arg0) {
        return mFragments.get(arg0);
      }
    };
    mViewpager.setAdapter(mAdapter);
    //mViewpager.setOffscreenPageLimit(0);
    mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

      @Override
      public void onPageSelected(int arg0) {
        switch (arg0) {
          case 0:
            homepage.setChecked(true);
            break;
          case 1:
            classfication.setChecked(true);
            break;
          case 2:
            shoppingcar.setChecked(true);
            break;
          case 3:
            personcenter.setChecked(true);
            break;
          default:
            break;
        }

      }

      @Override
      public void onPageScrolled(int arg0, float arg1, int arg2) {

      }

      @Override
      public void onPageScrollStateChanged(int arg0) {

      }
    });
  }

  @OnClick({ R.id.homepage, R.id.classfication, R.id.shoppingcar, R.id.personcenter })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.homepage:
        mViewpager.setCurrentItem(0, true);
        break;
      case R.id.classfication:
        mViewpager.setCurrentItem(1, true);
        break;
      case R.id.shoppingcar:
        mViewpager.setCurrentItem(2, true);
        break;
      case R.id.personcenter:
        mViewpager.setCurrentItem(3, true);
        break;
    }
  }
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      exit();
      return false;
    }
    return super.onKeyDown(keyCode, event);
  }

  public void exit() {
    if ((System.currentTimeMillis() - exitTime) > 2000) {
      Toast.makeText(getApplicationContext(), "再按一次退出程序",
          Toast.LENGTH_SHORT).show();
      exitTime = System.currentTimeMillis();
    } else {
      ExitUtils.finishAll();
      System.exit(0);
    }
  }
}
