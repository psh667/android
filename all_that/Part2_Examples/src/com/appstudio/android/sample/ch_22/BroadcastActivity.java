package com.appstudio.android.sample.ch_22;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class BroadcastActivity extends Activity {

	public static String USER_DEFINED_MSG = "com.appstudio.android.msg";
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.atv_broadcast);

		Button btn_send = (Button)findViewById(R.id.it_send);
		btn_send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 Intent serviceIntent = new Intent();
				 serviceIntent.setAction(USER_DEFINED_MSG);
				 serviceIntent.putExtra("data", "user defined msg");
				 sendBroadcast(serviceIntent);
			}
		});
		
		Button btn_register = (Button) findViewById(R.id.it_register);
		btn_register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SampleBroadcastReceiver receiver = new SampleBroadcastReceiver(); 
				IntentFilter filter= new IntentFilter();
				filter.addAction(USER_DEFINED_MSG);
				registerReceiver(receiver, filter);
			}
		});
	}
}
	