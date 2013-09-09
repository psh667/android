package org.nashorn.exam0405;
 
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;
import android.preference.*; 

public class TimePickerPreference extends DialogPreference implements TimePicker.OnTimeChangedListener 
{
	private String timeValue;
 
	public TimePickerPreference(Context context, AttributeSet attrs) 
	{
		super(context, attrs);
		setPersistent(true);
	}
	public TimePickerPreference(Context context, AttributeSet attrs, int style) 
	{
		super(context, attrs, style);
		setPersistent(true);
	}
 
	@Override
	protected View onCreateDialogView() 
	{
		TimePicker timePicker = new TimePicker(getContext());
		timePicker.setOnTimeChangedListener(this);
		int hour = getHour();
		int minute = getMinute();
		if (hour >= 0 && minute >= 0) {
			timePicker.setCurrentHour(hour);
			timePicker.setCurrentMinute(minute);
		}
		return timePicker;
	}
 
	@Override
	public void onTimeChanged(TimePicker view, int hour, int minute) {
 		persistString(hour + ":" + minute);
	}
 
	@Override
	public void setDefaultValue(Object defaultValue) {
		super.setDefaultValue(defaultValue);
 
		if (!(defaultValue instanceof String)) {
			return;
		}
 
		if (!((String) defaultValue).matches("[0-2]*[0-9]:[0-5]*[0-9]")) {
			return;
		}
 
		this.timeValue = (String) defaultValue;
	}
 
	private int getHour() {
		String time = getPersistedString(this.timeValue);
		if (time == null || !time.matches("[0-2]*[0-9]:[0-5]*[0-9]")) {
			return -1;
		}
		return Integer.valueOf(time.split(":")[0]);
	}
 
	private int getMinute() {
		String time = getPersistedString(this.timeValue);
		if (time == null || !time.matches("[0-2]*[0-9]:[0-5]*[0-9]")) {
			return -1;
		}
		return Integer.valueOf(time.split(":")[1]);
	}
}