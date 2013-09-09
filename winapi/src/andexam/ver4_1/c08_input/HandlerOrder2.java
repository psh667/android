package andexam.ver4_1.c08_input;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class HandlerOrder2 extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View vw = new MyView(this);
		//* 리스너 - 1순위
		vw.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Toast.makeText(HandlerOrder2.this,"Listener : Touch Event Received",
							Toast.LENGTH_SHORT).show();
					//return true;
				}
				return false;
			}
		});
		//*/
		setContentView(vw);
	}

	protected class MyView extends View {
		public MyView(Context context) {
			super(context);
		}

		//* 뷰의 콜백 메서드 - 2순위
		public boolean onTouchEvent(MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				Toast.makeText(HandlerOrder2.this,"View : Touch Event Received",
						Toast.LENGTH_SHORT).show();
				//return true;
			}
			return false;
		}
		//*/
	}

	//* 액티비티의 콜백 메서드 - 3순위
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			Toast.makeText(HandlerOrder2.this,"Activity : Touch Event Received",
					Toast.LENGTH_SHORT).show();
			//return true;
		}
		return false;
	}
	//*/
}
