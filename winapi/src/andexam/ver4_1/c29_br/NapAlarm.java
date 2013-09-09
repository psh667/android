package andexam.ver4_1.c29_br;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class NapAlarm extends Activity {
	static final int NAPNOTI = 1;
	NotificationManager mNotiManager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.napalarm);
		mNotiManager = (NotificationManager)getSystemService(
				NOTIFICATION_SERVICE);
	}

	public void mOnClick(View v) {
		Toast.makeText(NapAlarm.this, "안녕히 주무세요", 0).show();
		v.postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent(NapAlarm.this, NapEnd.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				PendingIntent content = PendingIntent.getActivity(
						NapAlarm.this, 0, intent, 0);

				
				Notification noti = new Notification.Builder(NapAlarm.this)
				.setTicker("일어나세요")
				.setContentTitle("기상 시간")
				.setContentText("일어나! 일할 시간이야.")
				.setSubText("일을 해야 돈을 벌고 돈을 벌어야 밥먹고 살지!!")
				.setSmallIcon(R.drawable.napalarm)
				.setLargeIcon(BitmapFactory.decodeResource(getResources(), 
						R.drawable.harubang))
				.setContentIntent(content)
				.build();

				mNotiManager.notify(NapAlarm.NAPNOTI, noti);
			}
		}, 5 * 1000);
	}
}
