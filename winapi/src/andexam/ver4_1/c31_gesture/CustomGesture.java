package andexam.ver4_1.c31_gesture;

import andexam.ver4_1.*;
import java.util.*;

import android.app.*;
import android.gesture.*;
import android.gesture.GestureOverlayView.*;
import android.os.*;
import android.widget.*;

public class CustomGesture extends Activity{
	TextView mtxtCount;
	int mCount = 10;
	GestureLibrary mLibrary;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customgesture);

		mtxtCount = (TextView)findViewById(R.id.count);
		mtxtCount.setText("" + mCount);

		mLibrary = GestureLibraries.fromRawResource(this, R.raw.hangul);
		if (mLibrary.load() == false) {
			finish();
		}

		GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
		gestures.addOnGesturePerformedListener(mListener);
	}

	OnGesturePerformedListener mListener = new OnGesturePerformedListener() {
		public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
			ArrayList<Prediction> predictions = mLibrary.recognize(gesture);

			if (predictions.size() != 0) {
				Prediction prediction = predictions.get(0);
				String name = prediction.name;

				if (prediction.score > 1.0) {
					if (name.equals("kiyuk")) {
						mCount++;
					} else if (name.equals("niun")) {
						mCount--;
					} else if (name.equals("digut")) {
						mCount = 10;
					}
					mtxtCount.setText("" + mCount);
				}
			}
		}
	};
}
