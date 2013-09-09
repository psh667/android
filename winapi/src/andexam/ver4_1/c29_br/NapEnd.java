package andexam.ver4_1.c29_br;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class NapEnd extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.napend);

		NotificationManager NM = (NotificationManager) 
			getSystemService(NOTIFICATION_SERVICE);
		NM.cancel(NapAlarm.NAPNOTI);

		Button btn = (Button)findViewById(R.id.end);
		btn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});        
	}
}
