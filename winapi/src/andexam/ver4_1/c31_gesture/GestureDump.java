package andexam.ver4_1.c31_gesture;

import andexam.ver4_1.*;
import java.util.*;

import android.app.*;
import android.os.*;
import android.view.*;
import android.view.GestureDetector.*;
import android.widget.*;

public class GestureDump extends Activity {
	ArrayList<String> arGesture = new ArrayList<String>(); 
	TextView mResult;
	GestureDetector mDetector;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gesturedump);

		mResult = (TextView)findViewById(R.id.result);
		mDetector = new GestureDetector(this, mGestureListener);
		mDetector.setOnDoubleTapListener(mDoubleTapListener);
	}

	public boolean onTouchEvent(MotionEvent event) { 
		return mDetector.onTouchEvent(event); 
	}

	OnGestureListener mGestureListener = new OnGestureListener() {
		public boolean onDown(MotionEvent e) {
			AppendText(String.format("Down : %d, %d", (int)e.getX(), (int)e.getY()));
			return false;
		}

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,	
				float velocityY) {
			AppendText(String.format("Fling : (%d,%d)-(%d,%d) (%d,%d)", 
					(int)e1.getX(), (int)e1.getY(), (int)e2.getX(), (int)e2.getY(), 
					(int)velocityX, (int)velocityY));
			return false;
		}

		public void onLongPress(MotionEvent e) {
			AppendText("LongPress");
		}

		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			AppendText(String.format("Scroll : (%d,%d)-(%d,%d) (%d,%d)", 
					(int)e1.getX(), (int)e1.getY(), (int)e2.getX(), (int)e2.getY(), 
					(int)distanceX, (int)distanceY));
			return false;
		}

		public void onShowPress(MotionEvent e) {
			AppendText("ShowPress");
		}

		public boolean onSingleTapUp(MotionEvent e) {
			AppendText("SingleTapUp");
			return false;
		}
	};

	OnDoubleTapListener mDoubleTapListener = new OnDoubleTapListener() {
		public boolean onDoubleTap(MotionEvent e) {
			AppendText("DoubleTap");
			return false;
		}

		public boolean onDoubleTapEvent(MotionEvent e) {
			AppendText("DoubleTapEvent");
			return false;
		}

		public boolean onSingleTapConfirmed(MotionEvent e) {
			AppendText("SingleTapConfirmed");
			return false;
		}
	};

	void AppendText(String text) {
		if (arGesture.size() > 15) {
			arGesture.remove(0);
		}
		arGesture.add(text);
		StringBuilder result = new StringBuilder();
		for (String s : arGesture) {
			result.append(s);
			result.append("\n");
		}
		mResult.setText(result.toString());
	}
}
