package pro.android;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BackgroundService extends Service
{
	private  NotificationManager notificationMgr;
	
	@Override
	public void  onCreate()  {
		super.onCreate();
		
		notificationMgr  =(NotificationManager)getSystemService( NOTIFICATION_SERVICE);
		
		displayNotificationMessage("백그라운드 서비스 시작");
		
		Thread  thr = new Thread(null, new ServiceWorker(),  "BackgroundService");
		thr.start();
	}
	
	class ServiceWorker  implements  Runnable
	{
		public void  run()  {
			// 여기에 백그라운드 처리 넣기...
			
			// 처리 완료되면 서비스 종료...
			// BackgroundService.this.stopSelf();
		}
	}
	
	@Override
	public void  onDestroy()
	{
		displayNotificationMessage("백그라운드 서비스 종료");
		super.onDestroy();	
	}
	
	@Override
	public void  onStart(Intent intent, int startId)  {
		super.onStart(intent, startId);
	}
	@Override
	public  IBinder onBind(Intent intent)  {
		return null;
	}
	
	private void  displayNotificationMessage(String  message)
	{
		Notification notification  = new Notification(R.drawable.note, message,System.currentTimeMillis());
		
		PendingIntent contentIntent  =
		PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
		
		notification.setLatestEventInfo(this, "백그라운드 서비스",message, contentIntent);
		
		notificationMgr.notify(R.id.app_notification_id, notification);
	}
}
