package andexam.ver4_1.c13_advwidget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class TimePickerTest extends Activity {
	TimePicker mTime;
	TextView mTxtTime;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timepickertest);
		
		mTime = (TimePicker)findViewById(R.id.timepicker);
		mTxtTime = (TextView)findViewById(R.id.txttime);
		mTime.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				mTxtTime.setText(String.format("%d:%d", hourOfDay, minute));
			}
		});

		// 24시간제 토글
		findViewById(R.id.btntoggle24).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mTime.setIs24HourView(!mTime.is24HourView());
			}
		});		

		// 선택기로부터 시간 조사
		findViewById(R.id.btnnow).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String result = String.format("%d시 %d분", 
						mTime.getCurrentHour(), mTime.getCurrentMinute());
				Toast.makeText(TimePickerTest.this, result, 0).show();
			}
		});		
	}
}
