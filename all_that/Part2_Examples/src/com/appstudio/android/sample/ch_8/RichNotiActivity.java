package com.appstudio.android.sample.ch_8;

import android.app.Activity;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.appstudio.android.sample.R;

public class RichNotiActivity extends Activity {
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rich_noti);
	}
	
	
	public void btnOnclick(View v){
		NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		if(v.getId() == R.id.btnBase){
			Notification notification = new Notification.Builder(this)
			.setContentTitle("기본알람")
			.setContentText("기본알람의내용")
			//status바에 보여지며 가장 왼쪽에 보여지는 이미지
			.setSmallIcon(R.drawable.notification_icon).build();
			
			notification.flags |=Notification.FLAG_AUTO_CANCEL;
			notificationManager.notify(0,notification);
		}else if(v.getId() == R.id.btnBigPicture){
			Builder builder = new Notification.Builder(this);
			builder.setContentTitle("BP알람")
			.setContentText("BP알람내용")
			//status바에 보여지며 가장 왼쪽에 보여지는 이미지
			.setSmallIcon(R.drawable.notification_icon)
			//클릭했을때 다른 액티비티로 이동하는 코드
			.addAction(R.drawable.ic_launcher, "go activity", getNotificationPendingIntent());
			
			Notification notification = new Notification.BigPictureStyle(builder)
			.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.transition_one)).build();
			
			notification.flags |=Notification.FLAG_AUTO_CANCEL;
			notificationManager.notify(0,notification);
		}else if(v.getId() == R.id.btnBigText){
			String msg = "이 예제는 젤리빈의 리치 노티피케이션에 대한 예제입니다. 예제를 통해서 학습합니다. 새로운 기능이니 만큼 충분한 연습이 필요합니다. 또한 사용자에게 불편함이 없게 적절하게 사용해야 하겠습니다.";
			
			Builder builder = new Notification.Builder(this);
			builder.setContentTitle("BT알람")
			.setContentText("BT알람내용")
			//status바에 보여지며 가장 왼쪽에 보여지는 이미지
			.setSmallIcon(R.drawable.notification_icon)
			.setAutoCancel(true)
			.setPriority(Notification.PRIORITY_HIGH)
			//클릭했을때 다른 액티비티로 이동하는 코드
			.addAction(R.drawable.ic_launcher, "go activity", getNotificationPendingIntent());
			
			Notification notification = new Notification.BigTextStyle(builder)
			.bigText(msg).build();
			
			notificationManager.notify(0,notification);
		}else if(v.getId() == R.id.btnInboxStyle){
			Builder builder = new Notification.Builder(this);
			builder.setContentTitle("IS알람")
			.setContentText("IS알람내용")
			.setSmallIcon(R.drawable.notification_icon)
			.addAction(R.drawable.ic_launcher, "go activity", getNotificationPendingIntent());
			
			Notification notification = new Notification.InboxStyle(builder)
			.addLine("내용1번")
			.addLine("내용2번")
			.addLine("내용3번")
			.addLine("내용4번")
			.setSummaryText("+2 more").build();
			
			notification.flags |=Notification.FLAG_AUTO_CANCEL;
			notificationManager.notify(0,notification);
		}
		
		
	}

	private PendingIntent getNotificationPendingIntent(){
		return PendingIntent.getActivity(this, 0, new Intent(this, HandleNotiActivity.class),0);
	}
}
