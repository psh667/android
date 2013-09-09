package com.appstudio.android.sample.ch_20;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.appstudio.android.sample.R;

public class CalendarInstanceActivity extends Activity {

	private ListView lv = null;
	private int mYear 	= 2012;
	private int mMonth 	= 8;
	private int mDay 	= 10;
	private int mHour 	= 11;
	private int mMinute = 0;
	
	private ArrayList<String> mInstanceList = null;
	private ArrayAdapter<String> mAdapter = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_calendar_instance);
        init();
    }
    
    private void init(){
    	mInstanceList = new ArrayList<String>();
    	lv = (ListView)findViewById(R.id.listView);
    	getInstanceData();
    	
    	mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mInstanceList);
    	lv.setAdapter(mAdapter);
    }

    private void getInstanceData() {
    	ContentResolver cr = getContentResolver();
    	
    	String[] EVENT_PROJECTION2 = new String[] {
				 CalendarContract.Instances._ID
			   , CalendarContract.Instances.BEGIN
			   , CalendarContract.Instances.END
			   , CalendarContract.Instances.END_DAY
			   , CalendarContract.Instances.END_MINUTE
			   , CalendarContract.Instances.START_DAY
			   , CalendarContract.Instances.START_MINUTE
			   , CalendarContract.Instances.EVENT_ID
			   };
    	
    	
    	Calendar beginTime = Calendar.getInstance();
    	beginTime.set(mYear, mMonth-1, mDay, mHour, mMinute);
    	long startMillis = beginTime.getTimeInMillis();
    	Calendar endTime = Calendar.getInstance();
    	endTime.set(mYear, mMonth-1, mDay+10, mHour, mMinute);
    	long endMillis = endTime.getTimeInMillis();
    			
		Cursor curs = CalendarContract.Instances.query(cr,EVENT_PROJECTION2,startMillis,endMillis);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		while (curs.moveToNext()) {
			  
			Date tempDate = new Date();
			tempDate.setTime(curs.getLong(1));
			String startDt = simpleDateFormat.format(tempDate);
			String id = curs.getString(0);
			
			tempDate.setTime(curs.getLong(2));
			String endDt = simpleDateFormat.format(tempDate);
			
			
			mInstanceList.add(startDt + "~" + endDt);

		}
		
    }

}
