package org.androidtown.ui.composite;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.androidtown.ui.composite.DateTimePicker.OnDateTimeChangedListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

/**
 * 날짜와 시간을 한꺼번에 선택할 수 있는 복합위젯을 만드는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {
	final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final TextView text01 = (TextView)findViewById(R.id.text01);
        final DateTimePicker dateTimePicker = (DateTimePicker)findViewById(R.id.dateTimePicker);

        // 이벤트 처리
        dateTimePicker.setOnDateTimeChangedListener(new OnDateTimeChangedListener() {
			public void onDateTimeChanged(DateTimePicker view, int year,
					int monthOfYear, int dayOfYear, int hourOfDay, int minute) {
				Calendar calendar = Calendar.getInstance();
				calendar.set(year, monthOfYear, dayOfYear, hourOfDay, minute);
				
				// 바뀐 시간 텍스트뷰에 표시
				text01.setText(dateFormat.format(calendar.getTime()));
			}
		});

        // 현재 시간 텍스트뷰에 표시
        Calendar calendar = Calendar.getInstance();
        calendar.set(dateTimePicker.getYear(), dateTimePicker.getMonth(), dateTimePicker.getDayOfMonth(), dateTimePicker.getCurrentHour(), dateTimePicker.getCurrentMinute());
        text01.setText(dateFormat.format(calendar.getTime()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
