package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lidroid.xutils.BitmapUtils;
import com.mc.happybuy.R;
import java.util.List;
import javabean.LikeInformation;

public class Pro_type_adapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<LikeInformation> list;
	private Context context;
	private LikeInformation likeInformation;

	public Pro_type_adapter(Context context, List<LikeInformation> list) {
		mInflater = LayoutInflater.from(context);
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		if (list != null && list.size() > 0)
			return list.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final MyView view;
		if (convertView == null) {
			view = new MyView();
			convertView = mInflater.inflate(R.layout.list_pro_type_item, null);
			view.icon = (ImageView) convertView
					.findViewById(R.id.iv_image_list_type_item);
			view.name = (TextView) convertView
					.findViewById(R.id.tv_information_list_type_item);
			view.price = (TextView) convertView
					.findViewById(R.id.tv_price_list_type_item);
			view.num = (TextView) convertView
					.findViewById(R.id.tv_purchasenumber_list_type_item);
			convertView.setTag(view);
		} else {
			view = (MyView) convertView.getTag();
		}
		if (list != null && list.size() > 0) {
			likeInformation = list.get(position);
			view.name.setText(likeInformation.getShopInfo());
			view.price.setText(likeInformation.getPrice()+"/元");
			view.num.setText(likeInformation.getPayPeopleNumber());
			String url = likeInformation.getUrl();
			System.out.println("fragment" + url);
			BitmapUtils mBitmapUtils = new BitmapUtils((Activity) context);
			mBitmapUtils.display(view.icon, url);
		}
		return convertView;
	}

	private class MyView {
		private ImageView icon;
		private TextView name, price, num;
	}

}
