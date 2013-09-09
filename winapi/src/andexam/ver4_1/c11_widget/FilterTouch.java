package andexam.ver4_1.c11_widget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class FilterTouch extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filtertouch);
	}
	
	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btnad:
			Toast.makeText(this, "광고를 보았습니다.", 1).show();
			break;
		case R.id.btncard:
			Toast.makeText(this, "물품 대금을 카드 결제합니다.", 1).show();
			break;
		case R.id.calltoast:
			LinearLayout linear = (LinearLayout)View.inflate(this, 
					R.layout.faketoast, null);
			Toast fake = new Toast(this);
			fake.setView(linear);
			fake.setGravity(Gravity.LEFT | Gravity.TOP, 0, 20);
			fake.setDuration(Toast.LENGTH_LONG);
			fake.show();
			break;
		}
	}	
}
