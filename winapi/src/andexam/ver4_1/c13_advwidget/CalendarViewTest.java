package andexam.ver4_1.c13_advwidget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.widget.*;

public class CalendarViewTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendarviewtest);

		CalendarView calendar = (CalendarView)findViewById(R.id.calendar);
		calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
			public void onSelectedDayChange(CalendarView view, int year,
					int month, int dayOfMonth) {
				Toast.makeText(CalendarViewTest.this, "" + year + "/" + 
						(month + 1) + "/" + dayOfMonth, 0).show();
			}
		});
	}
}