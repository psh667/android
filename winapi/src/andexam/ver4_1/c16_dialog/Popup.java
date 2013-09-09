package andexam.ver4_1.c16_dialog;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class Popup extends Activity {
	PopupWindow popup;
	View popupview;
	LinearLayout linear;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup);

		linear = (LinearLayout)findViewById(R.id.linear);
		popupview = View.inflate(this, R.layout.popupview, null);
		popup = new PopupWindow(popupview,200,150,true);

		final Button btnshow = (Button)findViewById(R.id.btnshow);
		btnshow.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// 지정한 좌표에 놓기
				popup.showAtLocation(linear,Gravity.NO_GRAVITY,100,150);
				// 가운데 놓기
				//popup.showAtLocation(linear,Gravity.CENTER,0,0);
				// 가운데 + 50, 80에 놓기
				//popup.showAtLocation(linear,Gravity.CENTER,50,80);
				// 화면 벗어나기
				//popup.showAtLocation(linear,Gravity.NO_GRAVITY,800,1200);
				// 수평 중앙, 수직 바닥 
				//popup.showAtLocation(linear,Gravity.CENTER_HORIZONTAL|
				//	Gravity.BOTTOM,0,0);
				// 수평 중앙, 수직 바닥 + (50,50)
				//popup.showAtLocation(linear,Gravity.CENTER_HORIZONTAL|
				//		Gravity.BOTTOM,50,50);
				// 버튼 아래에 놓기
				//popup.setAnimationStyle(-1);
				//popup.showAsDropDown(btnshow);
			}
		});

		Button btnclose = (Button)popupview.findViewById(R.id.btnclose);
		btnclose.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				popup.dismiss();
			}
		});
	}
}
