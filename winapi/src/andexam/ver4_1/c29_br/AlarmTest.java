package andexam.ver4_1.c29_br;

import andexam.ver4_1.*;
import java.util.*;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class AlarmTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarmtest);
	}

	public void mOnClick(View v) {
		AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		Intent intent;
		PendingIntent sender;

		switch (v.getId()) {
		case R.id.onetime:
			// 예약에 의해 호출될 BR 지정
			intent = new Intent(this, AlarmReceiver.class);
			sender = PendingIntent.getBroadcast(this, 0, intent, 0);

			// 알람 시간. 10초후
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.add(Calendar.SECOND, 10);

			// 알람 등록
			am.set(AlarmManager.RTC, calendar.getTimeInMillis(), sender);
			break;
		case R.id.repeat:
		case R.id.stop:
			intent = new Intent(this, DisplayScore.class);
			sender = PendingIntent.getBroadcast(this, 0, intent, 0);

			// 6초당 한번 알람 등록
			if (v.getId() == R.id.repeat) {
				am.setRepeating(AlarmManager.ELAPSED_REALTIME, 
						SystemClock.elapsedRealtime(), 
						6000, sender);
			} else {
				am.cancel(sender);
			}
			break;
		}
	}
}
