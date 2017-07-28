package adapter;

/**
 * Created by messi.mo on 2017/4/10.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.mc.happybuy.R;

/**
 * Created by messi.mo on 2016/10/19.
 * 图片轮播适配器
 */

public class RollViewAdapter extends StaticPagerAdapter {
  Context context;
  private int[] imageIds = {
      R.mipmap.top1, R.mipmap.top2, R.mipmap.top3, R.mipmap.top4, R.mipmap.top5
  };
  public RollViewAdapter(Context context) {
    this.context=context;
  }
  @Override
  public View getView(ViewGroup container,int position) {
    ImageView view = new ImageView(container.getContext());
    view.setImageResource(imageIds[position]);
    view.setScaleType(ImageView.ScaleType.CENTER_CROP);
    view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
      view.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {

        }
      });
    return view;
  }

  @Override
  public int getCount() {
    return imageIds.length;
  }

}
