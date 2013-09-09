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
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class CustomNotificationActivity extends Activity {
	private static final int CUSTOM_HELLO_ID = 1;
	Context context;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customnotificationmain);
		context = getApplicationContext();
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new ButtonOnClickHandler());
	}
	
	class ButtonOnClickHandler implements OnClickListener{
		@Override
		public void onClick(View v) {
			String ns = Context.NOTIFICATION_SERVICE;
			NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
			
			int icon = R.drawable.notification_icon;
			CharSequence tickerText = "첫번째 노티피케이션";
			long when = System.currentTimeMillis();
			
			Notification notification = new Notification(icon, tickerText, when);
			
			RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.customnotificationlayout);
			contentView.setImageViewResource(R.id.image, R.drawable.notification_icon);
			contentView.setTextViewText(R.id.title, "Custom notification");
			contentView.setTextViewText(R.id.text, "This is a custom layout");
			notification.contentView = contentView;
			
			Intent notificationIntent = new Intent(context, CustomNotificationActivity.class);
			PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
			notification.contentIntent = contentIntent;
			mNotificationManager.notify(CUSTOM_HELLO_ID, notification);
		}
		
	}

}
