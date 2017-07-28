package fragment;

import adapter.MyAdapter;
import adapter.RollViewAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.jude.rollviewpager.RollPagerView;
import com.mc.happybuy.R;
import com.mc.happybuy.SaomiaoActivity;
import com.mc.happybuy.ShoppingInfoActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import constant.AllConstant;
import java.util.ArrayList;
import java.util.List;
import javabean.LikeInformation;
import okhttp3.Call;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.T;

public class HomepagerFragment extends Fragment {
  Unbinder unbinder;
  @BindView(R.id.roll_ad) RollPagerView rollAd;
  @BindView(R.id.shoppingView) GridView shoppingView;
  MyAdapter myAdapter;
  List<LikeInformation> list;
  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.activity_pagermain, null);
    unbinder = ButterKnife.bind(this, v);
    getShopping();
    loadAdImg();
    return v;
  }
  private void getShopping() {
    list = new ArrayList<LikeInformation>();
    OkHttpUtils.get().url(AllConstant.shoppingUrl).build().execute(new StringCallback() {
      @Override public void onError(Call call, Exception e, int id) {
        T.showShort(getContext(),"获取商品信息失败");
      }

      @Override public void onResponse(String response, int id) {
        try {
          JSONObject res = new JSONObject(response);
          JSONArray arr = res.getJSONArray("Shopings");
          int length = arr.length();
          for(int i=0; i<length; i++) {
            JSONObject jsonObject = arr.getJSONObject(i);//根据下标得到json数组中的元素，即得到json对象
            String url = jsonObject.getString("Picture");
            String price = jsonObject.getString("Price");
            String shopInfo = jsonObject.getString("ShopInfo");
            String payPeopleNumber = jsonObject.getString("PayPeopleNumber");
            list.add(new LikeInformation(url,price,shopInfo,payPeopleNumber));
          }
          myAdapter = new MyAdapter(list,getActivity());
          shoppingView.setAdapter(myAdapter);

        } catch (JSONException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });
  shoppingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      LikeInformation  mlikeInformation =list.get(position);
      String url= mlikeInformation.getUrl();
      String tv_price_list_view_tab =mlikeInformation.getPrice();
      String shopInfo =mlikeInformation.getShopInfo();
      String payPeopleNumber =mlikeInformation.getPayPeopleNumber();
      Intent intent = new Intent();
      intent.putExtra("url", url);
      intent.putExtra("tv_price_list_view_tab", tv_price_list_view_tab);
      intent.putExtra("shopInfo", shopInfo);
      intent.putExtra("payPeopleNumber", payPeopleNumber);
      intent.setClass(getActivity(),ShoppingInfoActivity.class);
      startActivity(intent);
    }
  });
  }

  //显示广告图片轮播
  private void loadAdImg() {
    rollAd.setAdapter(new RollViewAdapter(getActivity()));
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({
      R.id.shaomiao, R.id.tianmao, R.id.juhuasuan, R.id.tianmaoguoji, R.id.waimai, R.id.marke,
      R.id.chongzhi, R.id.lvxing, R.id.jinbi, R.id.paimai, R.id.fenlei
  }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.shaomiao:
        startActivity(new Intent(getActivity(), SaomiaoActivity.class));
        break;
      case R.id.tianmao:
      case R.id.juhuasuan:
      case R.id.tianmaoguoji:
      case R.id.waimai:
      case R.id.marke:
      case R.id.chongzhi:
      case R.id.lvxing:
      case R.id.jinbi:
      case R.id.paimai:
      case R.id.fenlei:
        T.showShort(getContext(),"暂时没有开通此功能");
        break;
    }
  }
}

