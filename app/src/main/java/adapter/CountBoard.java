package adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import com.mc.happybuy.R;
import utils.T;

public class CountBoard extends FrameLayout{
	Button jia, jian;
	EditText num;
	public static int number;
	public CountBoard(Context context, AttributeSet attrs) {
		super(context, attrs);

		LayoutInflater.from(context).inflate(R.layout.count_board,this,true);
		jia = (Button)findViewById(R.id.btn_add_count_board);
		jia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String strNum = num.getText().toString();
				int n = Integer.parseInt(strNum) + 1;
				//如果大于等于0才修改显示
				if(n >=100) {
					T.showShort(getContext(),"最大购买数量为99");
					num.setText("" + 99);

				}
				num.setText("" + n);


			}
		});
		jian = (Button)findViewById(R.id.btn_del_count_board);
		jian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String strNum = num.getText().toString();
				int n = Integer.parseInt(strNum) - 1;
				//如果大于等于0才修改显示
				if(n >= 0) {
					num.setText("" + n);

				}

			}
		});


		num = (EditText)findViewById(R.id.et_num_count_board);
		//输入框中文本变化监听
		num.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String strNum = num.getText().toString();
				if(strNum.isEmpty()) {
					num.setText("" + 0);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				String strNum = num.getText().toString();
				int n = Integer.parseInt(strNum);
				if(strNum.isEmpty()) {
					num.setText("" + 0);
				}
				if(n>=100){
					T.showShort(getContext(),"最大购买数量为99");
					num.setText("" + 99);
				}
			}
		});

	}

	public int getNum() {
		return Integer.parseInt(num.getText().toString());
	}


}
