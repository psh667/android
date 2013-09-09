package kr.co.company.chap6lab;

import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;

public class Chap6LabActivity extends Activity {
	private View mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mTextView = findViewById(R.id.color_region);
		RadioButton r1 =
				(RadioButton)findViewById(R.id.radio_button1);
		r1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mTextView.setBackgroundColor(Color.RED);
			}
		});
	}
}
