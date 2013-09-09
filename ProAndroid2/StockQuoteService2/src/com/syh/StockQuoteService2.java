package  com.syh;
// 이 파일은 StockQuoteService2.java이다.

import com.syh.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public  class  StockQuoteService2 extends Service
{
	private  NotificationManager notificationMgr;
	public class StockQuoteServiceImpl extends IStockQuoteService.Stub
	{
		
		@Override
		public String getQuote(String ticker, Person requester)
		throws RemoteException   {
			return  "안녕하세요, "+requester.getName()+"!"+ticker+"의 주가는 20.0 입니다.";
		}
	
	}
	
	@Override
	public void  onCreate()  {
		super.onCreate();
		
		notificationMgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		displayNotificationMessage("onCreate()가 StockQuoteService2에서 호출됨");
	}
	@Override
	public void  onDestroy()
	{
		displayNotificationMessage("onDestroy()가 StockQuoteService2에서 호출됨");
		super.onDestroy();
	}
	
	@Override
	public void  onStart(Intent intent, int startId)  {
		super.onStart(intent, startId);
	}
	
	@Override
	public  IBinder onBind(Intent intent)
	{
		displayNotificationMessage("onBind()가 StockQuoteService2에서 호출됨");
		return new StockQuoteServiceImpl();
	}
	
	private void  displayNotificationMessage(String  message)
	{
		Notification notification  = new Notification(R.drawable.note,
		message,System.currentTimeMillis());
		
		PendingIntent contentIntent  =
		PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
		
		notification.setLatestEventInfo(this, "StockQuoteService2",message, contentIntent);
		
		notificationMgr.notify(R.id.app_notification_id, notification);
	}
}
