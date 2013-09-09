package andexam.ver4_1.c31_gesture;

import andexam.ver4_1.*;
import java.util.*;

import android.app.*;
import android.gesture.*;
import android.gesture.GestureOverlayView.*;
import android.os.*;
import android.webkit.*;
import android.widget.*;

public class GestureOverlay extends Activity {
	WebView mWeb;
	GestureLibrary mLibrary;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gestureoverlay);

		mWeb = (WebView)findViewById(R.id.web);
		mWeb.setWebViewClient(new MyWebClient());
		WebSettings set = mWeb.getSettings();
		set.setJavaScriptEnabled(true);
		set.setBuiltInZoomControls(true);
		mWeb.loadUrl("http://www.google.com");

		mLibrary = GestureLibraries.fromRawResource(this, R.raw.hangul);
		if (mLibrary.load() == false) {
			finish();
		}
		GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
		gestures.addOnGesturePerformedListener(mListener);
	}

	class MyWebClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	OnGesturePerformedListener mListener = new OnGesturePerformedListener() {
		public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
			ArrayList<Prediction> predictions = mLibrary.recognize(gesture);

			if (predictions.size() != 0) {
				Prediction prediction = predictions.get(0);
				String name = prediction.name;

				if (prediction.score > 1.0) {
					if (name.equals("kiyuk")) {
						mWeb.goBack();
					} else if (name.equals("niun")) {
						mWeb.goForward();
					} else if (name.equals("digut")) {
						mWeb.loadUrl("http://www.soenlab.com");
					}
				}
			}
		}
	};
}
