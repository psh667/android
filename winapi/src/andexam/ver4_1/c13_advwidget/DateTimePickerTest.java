package andexam.ver4_1.c13_advwidget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class DateTimePickerTest extends Activity {
	DatePicker mDate;
	TextView mTxtDate;
	TimePicker mTime;
	TextView mTxtTime;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datetimepickertest);
		
		// 날짜 선택기
		mDate = (DatePicker)findViewById(R.id.datepicker);
		mTxtDate = (TextView)findViewById(R.id.txtdate);
		mDate.init(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(), 
			new DatePicker.OnDateChangedListener() {
			public void onDateChanged(DatePicker view, int year, 
				int monthOfYear, int dayOfMonth) {
				mTxtDate.setText(String.format("%d/%d/%d", year, 
						monthOfYear + 1, dayOfMonth));
				}
			}
		);
		
		// 시간 선택기
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

		// 선택기로부터 날짜 조사
		findViewById(R.id.btnnow).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String result = String.format("%d/%d/%d %d:%d", mDate.getYear(),
						mDate.getMonth() + 1, mDate.getDayOfMonth(),
						mTime.getCurrentHour(), mTime.getCurrentMinute());
				Toast.makeText(DateTimePickerTest.this, result, 0).show();
			}
		});		
	}
}
