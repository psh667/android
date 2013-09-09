package andexam.ver4_1.c07_output;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class ToastTest extends Activity {
	Toast mToast = null;
	int count;
	String str;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toasttest);
		
		findViewById(R.id.shortmsg).setOnClickListener(mClickListener);
		findViewById(R.id.longmsg).setOnClickListener(mClickListener);
		findViewById(R.id.count1).setOnClickListener(mClickListener);
		findViewById(R.id.count2).setOnClickListener(mClickListener);
		findViewById(R.id.customview).setOnClickListener(mClickListener);
	}

	Button.OnClickListener mClickListener = new Button.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.shortmsg:
				Toast.makeText(ToastTest.this, "잠시 나타나는 메시지", 
						Toast.LENGTH_SHORT).show();
				break;
			case R.id.longmsg:
				Toast.makeText(ToastTest.this, "조금 길게 나타나는 메시지", 
						Toast.LENGTH_LONG).show();
				break;
			case R.id.count1:
				str = "현재 카운트 = " + count++;
				if (mToast != null) {
					mToast.cancel();
				}
				mToast = Toast.makeText(ToastTest.this, str, Toast.LENGTH_SHORT);
				mToast.show();
				break;
			case R.id.count2:
				str = "현재 카운트 = " + count++;
				if (mToast == null) { 
					mToast = Toast.makeText(ToastTest.this, str, Toast.LENGTH_SHORT);
				} else {
					mToast.setText(str);
				}
				mToast.show();
				break;
			case R.id.customview:
				LinearLayout linear = (LinearLayout)View.inflate(ToastTest.this, 
						R.layout.toastview, null);
				Toast t2 = new Toast(ToastTest.this);
				t2.setView(linear);
				t2.show();
				break;
			}
		}
	};
}
