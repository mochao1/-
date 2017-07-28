package fragment;

import adapter.CountBoard;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.lidroid.xutils.BitmapUtils;
import com.mc.happybuy.R;
import com.mc.happybuy.ReceivingNoteActivity;
import com.mc.happybuy.SaomiaoActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import constant.AllConstant;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javabean.BaseShop;
import javabean.Shoppingcar;
import okhttp3.Call;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.T;

public class ShoppingcarFragment extends Fragment {

  @BindView(R.id.listView) ListView listView;
  Unbinder unbinder;
  EditText et_num_count_board;
  List<Shoppingcar> list;
  List<BaseShop> list_shop=new ArrayList<BaseShop>();
  List<Shoppingcar> checked_list;
  ShoppingCarAdapter adapter;
  @BindView(R.id.cb_all_activity_main) CheckBox cbAllActivityMain;
  @BindView(R.id.noshpping) TextView noshpping;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.activity_shopping, null);
    View v2 = inflater.inflate(R.layout.count_board, null);
    unbinder = ButterKnife.bind(this, v);
    et_num_count_board = (EditText) v2.findViewById(R.id.et_num_count_board);
    getShoppingInformation();
    return v;
  }

  private void getShoppingInformation() {
    OkHttpUtils.get().url(AllConstant.shoppingCartUrl).build().execute(new StringCallback() {
      @Override public void onError(Call call, Exception e, int id) {
        T.showShort(getContext(), "连接失败！");
      }

      @Override public void onResponse(String response, int id) {
        list = new ArrayList<Shoppingcar>();
        try {
          JSONObject res = new JSONObject(response);
          JSONArray arr = res.getJSONArray("ShopingCars");
          int length = arr.length();
          for (int i = 0; i < length; i++) {
            JSONObject jsonObject = arr.getJSONObject(i);//根据下标得到json数组中的元素，即得到json对象
            String url = jsonObject.getString("Picture");
            String price = jsonObject.getString("Price");
            String shopInfo = jsonObject.getString("shopInfo");
            list.add(new Shoppingcar(url, price, shopInfo, false));
          }
          adapter = new ShoppingCarAdapter(list, getActivity());
          listView.setAdapter(adapter);
          if (list.size() > 0) {
            listView.setVisibility(View.VISIBLE);
            noshpping.setVisibility(View.GONE);
          } else {
            listView.setVisibility(View.GONE);
            noshpping.setVisibility(View.VISIBLE);
          }
        } catch (JSONException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick({
      R.id.sure_buy, R.id.cb_all_activity_main, R.id.btn_total_activity_main,R.id.shaomiao,
      R.id.btn_clear_activity_main
  }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.shaomiao:
        startActivity(new Intent(getActivity(), SaomiaoActivity.class));
        break;
      case R.id.cb_all_activity_main:
        boolean b = ((CheckBox) view).isChecked();//被选中
        if (list.size() > 0) {
          for (int i = 0; i < list.size(); i++) {
            list.get(i).setbChecked(b);
          }
          adapter.notifyDataSetChanged();
        } else {
          if (b) {
            cbAllActivityMain.setChecked(false);
            T.showShort(getActivity(), "购物车是空的！");
          }
        }
        break;
      case R.id.btn_total_activity_main:
        countTotal();
        break;
      case R.id.btn_clear_activity_main:
        if (list.size() > 0) {
          clearShopping();
          list.clear();
          adapter.notifyDataSetChanged();
          listView.setVisibility(View.GONE);
          noshpping.setVisibility(View.VISIBLE);
        } else {
          T.showShort(getActivity(), "购物车是空的！");
          listView.setVisibility(View.GONE);
          noshpping.setVisibility(View.VISIBLE);
        }
        break;
      case R.id.sure_buy:
        buyShopping();
        break;
    }
  }

  private void clearShopping() {
    OkHttpUtils.get().url(AllConstant.clearShoppingtUrl).build().execute(new StringCallback() {
      @Override public void onError(Call call, Exception e, int id) {
        T.showShort(getActivity(), "连接失败！");
      }

      @Override public void onResponse(String response, int id) {
        if (response.equals("YES")) {
          T.showShort(getActivity(), "清空购物车成功！");
        }
        if (response.equals("No")) {
          T.showShort(getActivity(), "清空购物车失败！");
        }
      }
    });
  }

  private void buyShopping() {
    int total = 0;
    checked_list = new ArrayList<>();
    if (AllConstant.USERNAME != "登录/注册") {
      if (list.size() == 0) {
        T.showShort(getActivity(), "购物车是空的！");
        return;
      } else {
        for (int i = 0; i < list.size(); i++) {
          //判断若若i方法改变，则总数量也发生改变，总价等于单个商品的价格*点击的数量
          if (list.get(i).getbChecked() && (Integer.parseInt(list.get(i).getCount()) >= 1)) {
            total +=
                Integer.parseInt(list.get(i).getPrice()) * Integer.parseInt(list.get(i).getCount());
            checked_list.add(list.get(i));
            list_shop.add( new BaseShop(list.get(i).getImageUrl(),list.get(i).getPrice(),list.get(i).getShopInfo()));
          }
        }
        if (Integer.parseInt(AllConstant.MONEY) >= total&&checked_list.size()>0) {
          Intent intent = new Intent(getActivity(), ReceivingNoteActivity.class);
          intent.putExtra("price", "shoppingcar");
          intent.putExtra("total", total + "");
          Bundle bundle = new Bundle();
          bundle.putSerializable("list", (Serializable) checked_list);
          bundle.putSerializable("list_shop", (Serializable) list_shop);
          intent.putExtras(bundle);
          startActivity(intent);
        } else if (Integer.parseInt(AllConstant.MONEY) < total){
          T.showShort(getActivity(), "余额不足，请充值！");
        }
        else {
          T.showShort(getActivity(), "你选择的商品数量为0！");
        }
      }
    } else {
      Toast.makeText(getActivity(), "请登录", Toast.LENGTH_SHORT).show();
    }
  }

  private void countTotal() {
    float total = 0;
    //若用户名登录的话说明可以进行购买，数量为0
    // TODO Auto-generated method stub
    if (AllConstant.USERNAME != "登录/注册") {
      if (list.size() == 0) {
        T.showShort(getActivity(), "购物车是空的！");
        return;
      } else {
        for (int i = 0; i < list.size(); i++) {
          //判断若若i方法改变，则总数量也发生改变，总价等于单个商品的价格*点击的数量
          if (list.get(i).getbChecked()) {
            total +=
                Integer.parseInt(list.get(i).getPrice()) * Integer.parseInt(list.get(i).getCount());
          }
        }
        T.showShort(getActivity(), "总金额:" + total + "元");
      }
    } else {
      Toast.makeText(getActivity(), "请登录", Toast.LENGTH_SHORT).show();
    }
  }

  class ShoppingCarAdapter extends BaseAdapter {
    List<Shoppingcar> list;
    Context context;
    int length;

    public ShoppingCarAdapter(List<Shoppingcar> lists, Context context) {
      this.list = lists;
      // 它代表Activity
      this.context = context;
    }

    //返回总列表的项数。若部位0 ，则回调getview方法，并将view对象绘制到listview中
    @Override public int getCount() {
      return list.size();
    }

    //返回position行的数据元素
    @Override public Object getItem(int position) {
      return position;
    }

    //返回position行对应的id
    @Override public long getItemId(int position) {
      return position;
    }

    //参数1需要返回列表项的行号，参数2 被列表服用的列表号  参数3用来装载到列表项中的listview
    //返回position位置上对应的列表项视图。
    @Override public View getView(final int position, View convertView, ViewGroup parent) {
      //优化自定义的adapter，没有可用的view
      ViewHolder holder = null;
      //layoutinflater：初始化一个xml布局文件，将其转换成view对象，然后添加到指定的容器中。
      if (convertView == null) {
        convertView = LayoutInflater.from(getActivity())
            .inflate(R.layout.shopping_car_listview_activity, parent, false);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        TextView tvDesc = (TextView) convertView.findViewById(R.id.tv_desc_item);// 详情
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tv_price_item);// 价格
        CheckBox cb = (CheckBox) convertView.findViewById(R.id.cb_item);
        CountBoard cb2 = (CountBoard) convertView.findViewById(R.id.cb_countbord_shoopingcard);
        final EditText et_num_count_board = (EditText) cb2.findViewById(R.id.et_num_count_board);
        et_num_count_board.addTextChangedListener(new TextWatcher() {

          @Override public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub

          }

          @Override public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub

          }

          @Override public void afterTextChanged(Editable arg0) {
            // TODO Auto-generated method stub
            String numprice = et_num_count_board.getText().toString().trim();
            list.get(position).setCount(numprice);
          }
        });

        holder = new ViewHolder(cb, tvDesc, tvPrice, imageView);
        convertView.setTag(holder);
      } else {
        //假如发生了复用，直接将信息设置在相对应的项目中
        holder = (ViewHolder) convertView.getTag();
      }
      Shoppingcar mShoppingcar = list.get(position);
      String url = mShoppingcar.getImageUrl();
      String price = mShoppingcar.getPrice();
      String shopInfo = mShoppingcar.getShopInfo();
      BitmapUtils mBitmapUtils = new BitmapUtils((Activity) context);
      mBitmapUtils.display(holder.imageView, url);
      //把这些信息保存在holder中
      holder.tvDesc.setText(shopInfo);
      holder.tvPrice.setText("￥" + price + "/元");
      holder.cb.setChecked(mShoppingcar.getbChecked());
      holder.cb.setOnClickListener(new CBClickListener(position));

      return convertView;
    }
  }

  class ViewHolder {
    CheckBox cb;
    TextView tvDesc, tvPrice;
    ImageView imageView;

    //将保存在viewhodler中的信息转变成全局的，以便于调用
    public ViewHolder(CheckBox cb, TextView tvDesc, TextView tvPrice, ImageView imageView) {
      super();
      this.cb = cb;
      this.tvDesc = tvDesc;
      this.tvPrice = tvPrice;
      this.imageView = imageView;
    }
  }

  class CBClickListener implements View.OnClickListener {

    int pos;

    public CBClickListener(int pos) {
      this.pos = pos;
    }

    @Override public void onClick(View v) {
      boolean b = ((CheckBox) v).isChecked();
      list.get(pos).setbChecked(b);
      if (b) {
        for (int i = 0; i < list.size(); i++) {
          if (!list.get(i).getbChecked()) {
            cbAllActivityMain.setChecked(false);
            return;
          }
        }
        cbAllActivityMain.setChecked(true);
      } else {
        cbAllActivityMain.setChecked(false);
      }
      adapter.notifyDataSetChanged();
    }
  }
}
