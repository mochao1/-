package adapter;

/**
 * Created by messi.mo on 2017/5/21.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.mc.happybuy.BuyActivity;
import com.mc.happybuy.R;
import com.mc.happybuy.StartActivity;
import constant.AllConstant;
import java.util.List;
import javabean.BaseShop;

public class HorizontalScrollViewAdapter
{

  private Context mContext;
  private LayoutInflater mInflater;
  private List<BaseShop> mDatas;

  public HorizontalScrollViewAdapter(Context context, List<BaseShop> mDatas)
  {
    this.mContext = context;
    mInflater = LayoutInflater.from(context);
    this.mDatas = mDatas;
  }

  public int getCount()
  {
    return mDatas.size();
  }

  public Object getItem(int position)
  {
    return mDatas.get(position);
  }

  public long getItemId(int position)
  {
    return position;
  }

  public View getView(int position, View convertView, ViewGroup parent)
  {
    ViewHolder viewHolder = null;
    if (convertView == null)
    {
      viewHolder = new ViewHolder();
      convertView = mInflater.inflate(
          R.layout.activity_index_gallery_item, parent, false);
      viewHolder.mImg = (ImageView) convertView
          .findViewById(R.id.id_index_gallery_item_image);
      viewHolder.mPrice = (TextView) convertView
          .findViewById(R.id.id_index_gallery_item_price);
      viewHolder.mInfo = (TextView) convertView
          .findViewById(R.id.id_index_gallery_item_info);

      convertView.setTag(viewHolder);
    } else
    {
      viewHolder = (ViewHolder) convertView.getTag();
    }
    Glide.with(mContext)
        .load(mDatas.get(position).getUrl())
        .into(viewHolder.mImg);
    viewHolder.mPrice.setText("￥" +mDatas.get(position).getPrice()+"元");
    viewHolder.mInfo.setText(mDatas.get(position).getShopInfo());

    return convertView;
  }

  private class ViewHolder
  {
    ImageView mImg;
    TextView mInfo;
    TextView mPrice;
  }

}