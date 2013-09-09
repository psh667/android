package org.androidtown.calendar.month;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

/**
 * 그리드뷰를 이용해 월별 캘린더를 만드는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {

	/**
	 * 월별 캘린더 뷰 객체
	 */
	CalendarMonthView monthView;
	
	/**
	 * 월별 캘린더 어댑터
	 */
	CalendarMonthAdapter monthViewAdapter;

	/**
	 * 월을 표시하는 텍스트뷰
	 */
	TextView monthText;

	/**
	 * 현재 연도
	 */
	int curYear;
	
	/**
	 * 현재 월
	 */
	int curMonth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 월별 캘린더 뷰 객체 참조
        monthView = (CalendarMonthView) findViewById(R.id.monthView);
        monthViewAdapter = new CalendarMonthAdapter(this);
        monthView.setAdapter(monthViewAdapter);

        // 리스너 설정
        monthView.setOnDataSelectionListener(new OnDataSelectionListener() {
			public void onDataSelected(AdapterView parent, View v, int position, long id) {
				// 현재 선택한 일자 정보 표시
				MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);
				int day = curItem.getDay();

				Log.d("CalendarMonthViewActivity", "Selected : " + day);

			}
		});

        monthText = (TextView) findViewById(R.id.monthText);
        setMonthText();

        // 이전 월로 넘어가는 이벤트 처리
        Button monthPrevious = (Button) findViewById(R.id.monthPrevious);
        monthPrevious.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		monthViewAdapter.setPreviousMonth();
        		monthViewAdapter.notifyDataSetChanged();

        		setMonthText();
        	}
        });

        // 다음 월로 넘어가는 이벤트 처리
        Button monthNext = (Button) findViewById(R.id.monthNext);
        monthNext.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		monthViewAdapter.setNextMonth();
        		monthViewAdapter.notifyDataSetChanged();

        		setMonthText();
        	}
        });

    }

    /**
     * 월 표시 텍스트 설정
     */
    private void setMonthText() {
    	curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();

        monthText.setText(curYear + "년 " + (curMonth+1) + "월");
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
