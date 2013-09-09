package andexam.ver4_1.c31_gesture;

import andexam.ver4_1.*;
import java.util.*;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class PinchZoom extends Activity {
	final static float STEP = 200;
	TextView mtxtRatio;
	float mRatio = 1.0f;
	int mBaseDist;
	float mBaseRatio;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pinchzoom);

		mtxtRatio = (TextView)findViewById(R.id.ratio);
		mtxtRatio.setText("" + mRatio);
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (event.getPointerCount() == 2) {
			int action = event.getAction();
			int pureaction = action & MotionEvent.ACTION_MASK;
			if (pureaction == MotionEvent.ACTION_POINTER_DOWN) {
				mBaseDist = getDistance(event);
				mBaseRatio = mRatio;
			} else {
				float delta = (getDistance(event) - mBaseDist) / STEP;
				float multi = (float)Math.pow(2, delta);
				mRatio = Math.min(1024.0f, Math.max(0.1f, mBaseRatio * multi));
				mtxtRatio.setText(String.format("%.2f", mRatio));
			}
		}
		return true; 
	}

	int getDistance(MotionEvent event) {
		int dx = (int)(event.getX(0) - event.getX(1));
		int dy = (int)(event.getY(0) - event.getY(1));
		return (int)(Math.sqrt(dx * dx + dy * dy));
	}
}
