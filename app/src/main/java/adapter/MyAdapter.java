package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lidroid.xutils.BitmapUtils;
import com.mc.happybuy.R;
import java.util.List;
import javabean.LikeInformation;

public class MyAdapter extends BaseAdapter {
  List<LikeInformation> list;
  Context context;
  int length;

  public MyAdapter(List<LikeInformation> lists, Context context) {
    this.list = lists;
    //它代表Activity
    this.context = context;
  }

  @Override public int getCount() {
    return list.size();
  }

  @Override public Object getItem(int arg0) {
    return arg0;
  }

  @Override public long getItemId(int arg0) {
    return arg0;
  }

  @Override public View getView(int index, View view, ViewGroup arg2) {
    // 用来保存显示组件的引用
    ViewHolder viewHolder=null;
    //从内存的角度考虑
    if(view==null){
      //把一个布局转为View，
      view= ((Activity)context).getLayoutInflater().inflate(R.layout.list_view_tab, null);
      viewHolder = new ViewHolder();
      //把viewHolder保存到view对象里面
      view.setTag(viewHolder);
      //**************  创建View的时候，只需要找一次组件的引用，把这个引用保存到viewHolder对象里面
      //2,从view里面获得ImageView组件
      viewHolder.imageView = (ImageView)view.findViewById(R.id.iv_list_view_tab);
      //3,从view里面获得TextView组件
      viewHolder.tv_information_list_view_tab =(TextView)view.findViewById(R.id.tv_information_list_view_tab);
      viewHolder.tv_price_list_view_tab =(TextView)view.findViewById(R.id.tv_price_list_view_tab);
      viewHolder.tv_purchasenumber_list_view_tab =(TextView)view.findViewById(R.id.tv_purchasenumber_list_view_tab);
    }else{
      viewHolder=(ViewHolder)view.getTag();
    }
    //5，准备数据
    LikeInformation  mlikeInformation =list.get(index);
    //6，从CommodityInformation对象获得数据
    String url= mlikeInformation.getUrl();
    String tv_price_list_view_tab =mlikeInformation.getPrice();
    String shopInfo =mlikeInformation.getShopInfo();
    String payPeopleNumber =mlikeInformation.getPayPeopleNumber();
    //7，向组件添加数据
    BitmapUtils mBitmapUtils = new BitmapUtils((Activity)context);
    mBitmapUtils.display(viewHolder.imageView, url);

    viewHolder.tv_information_list_view_tab.setText(shopInfo);
    viewHolder.tv_price_list_view_tab.setText("￥"+tv_price_list_view_tab+"/元");
    viewHolder.tv_purchasenumber_list_view_tab.setText(payPeopleNumber);
    return view;
  }

  //  把View里面获得组件的引用保存到这里
  class ViewHolder{
    ImageView  imageView;
    TextView tv_information_list_view_tab;
    TextView tv_price_list_view_tab;
    TextView tv_purchasenumber_list_view_tab;
  }

}
