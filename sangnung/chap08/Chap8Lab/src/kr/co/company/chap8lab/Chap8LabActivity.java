package kr.co.company.chap8lab;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class Chap8LabActivity extends Activity {
	private MyView mMyView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mMyView = (MyView) findViewById(R.id.myview);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void redraw(View v) {
		mMyView.invalidate();
	}
}
