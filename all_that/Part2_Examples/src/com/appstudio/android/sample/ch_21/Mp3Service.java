package com.appstudio.android.sample.ch_21;

import com.appstudio.android.sample.R;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class Mp3Service extends Service {

	private MediaPlayer mp = null;

	@Override
	public void onCreate() {
		Toast.makeText(this, "서비스 생성", Toast.LENGTH_SHORT).show();
	}



	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show();

		Notification notification = new Notification(R.drawable.ic_launcher,
				getText(R.string.app_name), System.currentTimeMillis());
		Intent notificationIntent = new Intent(this, Mp3Service.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(this, "Mp3Sample", "gone ",
				pendingIntent);
		startForeground(startId, notification);

		playSong();
		return START_STICKY;
	}

	private void playSong() {
		try {
			mp = MediaPlayer.create(this, R.raw.gone);
			mp.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		// thread.quit();
		mp.stop();
		Toast.makeText(this, "서비스 종료", Toast.LENGTH_SHORT).show();
	}
}
