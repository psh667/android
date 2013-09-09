package andexam.ver4_1.c29_br;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class CustomNotiView extends Activity {
	static final int NAPNOTI = 1;
	NotificationManager mNotiManager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.napalarm);
		mNotiManager = (NotificationManager)getSystemService(
				NOTIFICATION_SERVICE);
	}
		
	public void mOnClick(View v) {
		Toast.makeText(CustomNotiView.this, "안녕히 주무세요", 0).show();
		v.postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent(CustomNotiView.this, NapEnd.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				PendingIntent content = PendingIntent.getActivity(
						CustomNotiView.this, 0, intent, 0);

				RemoteViews napView = new RemoteViews(getPackageName(), 
						R.layout.customnotiview);

				Notification noti = new Notification.Builder(CustomNotiView.this)
				.setTicker("일어나세요")
				.setSmallIcon(R.drawable.napalarm)
				.setContentIntent(content)
				.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
				.setVibrate(new long[] {1000,1000,500,500,200,200,200,200,200,200})
				.setLights(0xff00ff00, 500, 500)
				.setContent(napView)
				.build();

				noti.flags |= (Notification.FLAG_INSISTENT | Notification.FLAG_SHOW_LIGHTS);
				
				mNotiManager.notify(NapAlarm.NAPNOTI, noti);
			}
		}, 5 * 1000);
	}
}