package adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.mc.happybuy.BuyActivity;
import com.mc.happybuy.R;
import constant.AllConstant;
import java.util.List;
import javabean.BaseShop;

public class ShopItemAdapter extends RecyclerView.Adapter<ShopItemAdapter.MyViewHolder> {

  private final Context mContext;
  private List<BaseShop> mDatas;
  TextView etConsignee;
  ImageView idContent;
  public ShopItemAdapter(Context context, List<BaseShop> mDatas,TextView etConsignee,ImageView idContent) {
    this.mContext = context;
    this.mDatas=mDatas;
    this.etConsignee=etConsignee;
    this.idContent=idContent;
  }

  @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = View.inflate(mContext, R.layout.activity_index_gallery_item, null);
    return new MyViewHolder(view);
  }

  @Override public void onBindViewHolder(final MyViewHolder holder, final int position) {
    holder.mPrice.setText("￥" +mDatas.get(position).getPrice()+"元");
    holder.mInfo.setText(mDatas.get(position).getShopInfo());
    Glide.with(mContext)
        .load(mDatas.get(position).getUrl())
        .into(holder.mImg);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Glide.with(mContext).load(AllConstant.BUYSHOP.get(position).getUrl()).into(idContent);
        etConsignee.setText(AllConstant.BUYSHOP.get(position).getConsigneeInfo().toString());
    }
    });

  }

  @Override public int getItemCount() {

    return mDatas.size();
  }

  class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView mImg;
    TextView mInfo;
    TextView mPrice;

    public MyViewHolder(View itemView) {
      super(itemView);
      mPrice = (TextView) itemView.findViewById(R.id.id_index_gallery_item_price);
      mInfo = (TextView) itemView.findViewById(R.id.id_index_gallery_item_info);
      mImg = (ImageView) itemView.findViewById(R.id.id_index_gallery_item_image);
    }
  }
}