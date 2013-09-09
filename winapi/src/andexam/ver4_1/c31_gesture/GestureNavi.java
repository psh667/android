package andexam.ver4_1.c31_gesture;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.GestureDetector.*;
import android.widget.*;

public class GestureNavi extends Activity {
	TextView mResult;
	TextView mtxtCount;
	int mCount = 10;
	GestureDetector mDetector;
	final static int DISTANCE = 200;
	final static int VELOCITY = 300;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gesturenavi);

		mResult = (TextView)findViewById(R.id.result);
		mtxtCount = (TextView)findViewById(R.id.count);
		mDetector = new GestureDetector(this, mGestureListener);
		mDetector.setIsLongpressEnabled(false);
		mtxtCount.setText("" + mCount);
	}

	public boolean onTouchEvent(MotionEvent event) { 
		return mDetector.onTouchEvent(event); 
	}

	OnGestureListener mGestureListener = new OnGestureListener() {
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,	
				float velocityY) {
			if (Math.abs(velocityX) > VELOCITY) {
				if (e1.getX() - e2.getX() > DISTANCE) {
					mCount--;
				}
				if (e2.getX() - e1.getX() > DISTANCE) {
					mCount++;
				}
			}
			mtxtCount.setText("" + mCount);
			mResult.setText("거리 = " + (int)Math.abs(e1.getX() - e2.getX()) + 
					", 속도 = " + (int)velocityX);
			return true;
		}

		public boolean onDown(MotionEvent e) {
			return false;
		}

		public void onLongPress(MotionEvent e) {
		}

		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			return false;
		}

		public void onShowPress(MotionEvent e) {
		}

		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}
	};
}
