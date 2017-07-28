package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.mc.happybuy.R;
import com.mc.happybuy.SaomiaoActivity;

public class ClassfictionFragment extends Fragment {

  @BindView(R.id.shaomiao) RadioButton shaomiao;
  @BindView(R.id.tools) LinearLayout tools;
  @BindView(R.id.tools_scrlllview) ScrollView toolsScrlllview;
  @BindView(R.id.goods_pager) ViewPager goodsPager;
  Unbinder unbinder;
  private String toolsList[];
  private TextView toolsTextViews[];
  private View views[];
  private ShopAdapter shopAdapter;
  private LayoutInflater inflater1;
  private View v;
  private int currentItem = 0;
  private int scrllViewWidth = 0, scrollViewMiddle = 0;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    v = inflater.inflate(R.layout.activity_classfiction, null);
    unbinder = ButterKnife.bind(this, v);
    shopAdapter = new ShopAdapter(getActivity().getSupportFragmentManager());
    inflater1 = LayoutInflater.from(getActivity());
    showToolsView();
    initPager();
    return v;
  }

  /**
   * initPager<br/>
   * 初始化ViewPager控件相关内容
   */
  private void initPager() {
    goodsPager.setAdapter(shopAdapter);
    goodsPager.setOnPageChangeListener(onPageChangeListener);
  }

  /**
   * OnPageChangeListener<br/>
   * 监听ViewPager选项卡变化事的事件
   */

  private ViewPager.OnPageChangeListener onPageChangeListener =
      new ViewPager.OnPageChangeListener() {
        @Override public void onPageSelected(int arg0) {
          if (goodsPager.getCurrentItem() != arg0) goodsPager.setCurrentItem(arg0);
          if (currentItem != arg0) {
            changeTextColor(arg0);
          }
          currentItem = arg0;
        }

        @Override public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override public void onPageScrollStateChanged(int arg0) {
        }
      };

  private void showToolsView() {
    toolsList = new String[] {
        "T恤", "衬衫", "打底裤", "短外套", "居家服", "连衣裙", "牛仔裤", "卫衣", "运动服", "长款毛衣", "体育用品", "家庭用品"
    };
    toolsTextViews = new TextView[toolsList.length];
    views = new View[toolsList.length];

    for (int i = 0; i < toolsList.length; i++) {
      View view = inflater1.inflate(R.layout.item_b_top_nav_layout, null);
      view.setId(i);
      view.setOnClickListener(toolsItemListener);
      TextView textView = (TextView) view.findViewById(R.id.text);
      textView.setText(toolsList[i]);
      tools.addView(view);
      toolsTextViews[i] = textView;
      views[i] = view;
    }
    changeTextColor(0);
  }

  /**
   * 改变textView的颜色
   */
  private void changeTextColor(int id) {
    for (int i = 0; i < toolsTextViews.length; i++) {
      if (i != id) {
        toolsTextViews[i].setBackgroundResource(android.R.color.transparent);
        toolsTextViews[i].setTextColor(0xff000000);
      }
    }
    toolsTextViews[id].setBackgroundResource(android.R.color.white);
    toolsTextViews[id].setTextColor(0xffff5d5e);
  }

  private View.OnClickListener toolsItemListener = new View.OnClickListener() {
    @Override public void onClick(View v) {
      goodsPager.setCurrentItem(v.getId());
    }
  };

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick(R.id.shaomiao) public void onViewClicked() {
    startActivity(new Intent(getActivity(), SaomiaoActivity.class));
  }

  /**
   * ViewPager 加载选项卡
   *
   * @author Administrator
   */

  private class ShopAdapter extends FragmentPagerAdapter {
    public ShopAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override public Fragment getItem(int arg0) {
      Fragment fragment = new Fragment_pro_type();
      Bundle bundle = new Bundle();
      String str = toolsList[arg0];
      bundle.putString("typename", str);
      fragment.setArguments(bundle);
      return fragment;
    }

    @Override public int getCount() {
      return toolsList.length;
    }
  }

  /**
   * 改变栏目位置
   */
  private void changeTextLocation(int clickPosition) {

    int x = (views[clickPosition].getTop() - getScrollViewMiddle() + (getViewheight(
        views[clickPosition]) / 2));
    toolsScrlllview.smoothScrollTo(0, x);
  }

  /**
   * 返回scrollview的中间位置
   */
  private int getScrollViewMiddle() {
    if (scrollViewMiddle == 0) scrollViewMiddle = getScrollViewheight() / 2;
    return scrollViewMiddle;
  }

  /**
   * 返回ScrollView的宽度
   */
  private int getScrollViewheight() {
    if (scrllViewWidth == 0) {
      scrllViewWidth = toolsScrlllview.getBottom() - toolsScrlllview.getTop();
    }
    return scrllViewWidth;
  }

  /**
   * 返回view的宽度
   */
  private int getViewheight(View view) {
    return view.getBottom() - view.getTop();
  }
}
