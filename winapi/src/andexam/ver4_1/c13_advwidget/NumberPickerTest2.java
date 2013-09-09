package andexam.ver4_1.c13_advwidget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.widget.*;
import android.widget.NumberPicker.OnValueChangeListener;

public class NumberPickerTest2 extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.numberpickertest);

		NumberPicker picker1 = (NumberPicker)findViewById(R.id.picker1);
		picker1.setMinValue(0);
		picker1.setMaxValue(5);
		picker1.setWrapSelectorWheel(false);

		NumberPicker picker2 = (NumberPicker)findViewById(R.id.picker2);
		picker2.setMinValue(0);
		picker2.setMaxValue(20);
		picker2.setValue(10);
		picker2.setOnLongPressUpdateInterval(100);

		NumberPicker picker3 = (NumberPicker)findViewById(R.id.picker3);
		picker3.setMinValue(0);
		picker3.setMaxValue(6);
		picker3.setDisplayedValues(new String[] {"일요일", "월요일", 
				"화요일", "수요일", "목요일", "금요일", "토요일"});

		NumberPicker picker4 = (NumberPicker)findViewById(R.id.picker4);
		picker4.setMinValue(0);
		picker4.setMaxValue(2);
		picker4.setFormatter(mFormatter);

		NumberPicker picker5 = (NumberPicker)findViewById(R.id.picker5);
		picker5.setMinValue(100);
		picker5.setMaxValue(200);
		picker5.setOnValueChangedListener(new OnValueChangeListener() {
			public void onValueChange(NumberPicker picker, int oldVal,	int newVal) {
				Toast.makeText(NumberPickerTest2.this, "Value : " + newVal, 0).show();
			}
		});
	}

	NumberPicker.Formatter mFormatter = new NumberPicker.Formatter() {
		public String format(int value) {
			switch (value) {
			case 0:
				return "Zerg";
			case 1:
				return "Terran";
			case 2:
				return "Protoss";
			}
			return null;
		}
	};
}
