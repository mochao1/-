package fragment;

import adapter.Pro_type_adapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.mc.happybuy.R;
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

public class Fragment_pro_type extends Fragment implements OnItemClickListener {
	private List<LikeInformation> list;
	private ImageView hint_img;
	private GridView mGridView;
	private Pro_type_adapter adapter;
	private ProgressBar progressBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pro_type, null);
		progressBar=(ProgressBar) view.findViewById(R.id.progressBar);
		hint_img=(ImageView) view.findViewById(R.id.hint_img);
		mGridView = (GridView) view.findViewById(R.id.listView);
		String typename = getArguments().getString("typename");
		OkHttpUtils.get()
				.url(AllConstant.shoppingImgtUrl)
				.addParams("TypeName", typename)
				.build()
				.execute(new StringCallback() {
			@Override public void onError(Call call, Exception e, int id) {
				T.showShort(getContext(),"连接失败！");
			}

			@Override public void onResponse(String response, int id) {
				list = new ArrayList<LikeInformation>();
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
					progressBar.setVisibility(View.GONE);
					adapter=new Pro_type_adapter(getActivity(), list);
					mGridView.setAdapter(adapter);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		mGridView.setOnItemClickListener(this);
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			
				LikeInformation  mlikeInformation =list.get(arg2);
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
	
	
	
	
}
