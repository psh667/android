package com.androidbook;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HelloActivity extends Activity {
	/** 액티비티 최초 호출 시에 호출됨 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/** TextView를 생성하고 Hello World!를 기록 */
		TextView tv = new TextView(this);
		tv.setText("Hello World!");
		/** 컨텐트 뷰를 TextView로 지정 */
		setContentView(tv);
	}
}