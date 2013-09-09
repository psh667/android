package kr.co.company.pingpong;

import android.app.Activity;
import android.os.Bundle;

public class PingPongActivity extends Activity {
	MySurfaceView view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = new MySurfaceView(this);
		setContentView(view);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
}