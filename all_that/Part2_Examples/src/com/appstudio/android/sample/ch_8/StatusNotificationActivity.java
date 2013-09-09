package com.appstudio.android.sample.ch_8;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StatusNotificationActivity extends Activity {
	private static final int HELLO_ID = 1;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statusnotificationmain);
		
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new ButtonOnClickHandler());
	}
	
	class ButtonOnClickHandler implements OnClickListener{
		@Override
		public void onClick(View v) {
			
			String ns = Context.NOTIFICATION_SERVICE;
			NotificationManager mNotificationManager = (NotificationManager)getSystemService(ns);
			
			int icon = R.drawable.notification_icon;
			CharSequence tickerText = "상태바 노티피케이션";
			long when = System.currentTimeMillis();

			Notification.Builder builder = new Notification.Builder(StatusNotificationActivity.this);
			builder.setSmallIcon(icon).setTicker(tickerText).setWhen(when);
			
			Notification notification = builder.getNotification();
			mNotificationManager.notify(HELLO_ID, notification);
			
		}
	}
}
