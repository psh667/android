package pro.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class DateTimeActivity extends Activity 
{
	@Override 
	protected void  onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.datetime);

		DatePicker dp  = (DatePicker)this.findViewById(R.id.datePicker);
		dp.init(2008, 11,   10,   null);

		TimePicker tp  = (TimePicker)this.findViewById(R.id.timePicker);
		tp.setIs24HourView(true);
		tp.setCurrentHour(new Integer(10));
		tp.setCurrentMinute(new Integer(10));
		
		this.findViewById(R.id.analclock);
		this.findViewById(R.id.digiclock);
	}
}
