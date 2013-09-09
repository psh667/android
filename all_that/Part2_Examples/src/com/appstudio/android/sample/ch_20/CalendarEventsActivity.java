package com.appstudio.android.sample.ch_20;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.appstudio.android.sample.R;

public class CalendarEventsActivity extends Activity {

	private long calEventID = 0;
	private EditText eventName = null;
	private EditText etCalId = null;
	private ListView lv = null;
	
	private int mYear 	= 2012;
	private int mMonth 	= 8;
	private int mDay 	= 15;
	private int mHour 	= 11;
	private int mMinute = 0;
	
	private static final int PROJECTION_DTSTART_INDEX 		= 0;
	private static final int PROJECTION_DTEND_INDEX 		= 1;
	private static final int PROJECTION_TITLE_INDEX 		= 2;
	private static final int PROJECTION_DESCRIPTION_INDEX 	= 3;
	private static final int PROJECTION_ID_INDEX 			= 4;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_calendar_events);
        init();
    }
    
    public void cal_event_onClick(View v){
    	if(v.getId() == R.id.btn_do){
    		if(calEventID==0){
				insertEvent(eventName.getText().toString());
			}else{
				updateEvent(calEventID);
			}
			
    	}else if(v.getId() == R.id.btn_del){
    		deleteEvent(calEventID);
    	}else if(v.getId() == R.id.btn_calid){
    		setListView();
    	}
    	
    	eventName.setText("");
		calEventID = 0;
		setListView();
    }
    private void init(){
    	lv = (ListView)findViewById(R.id.listView);
    	eventName = (EditText)findViewById(R.id.et_event_name);
    	etCalId = (EditText)findViewById(R.id.et_calid);
    
    	lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				TextView titleTv = (TextView)arg1.findViewById(R.id.tv_title);
				eventName.setText( titleTv.getText().toString() );
				
				TextView idTv = (TextView)arg1.findViewById(R.id.tv_id);
    			calEventID = Long.parseLong(idTv.getText().toString());
			}
		});
    	setListView();
    }
    
    private void setListView(){
    	Cursor eventCurs =  getEvents();
    	
    	if(eventCurs!=null){
    		String[] cols = new String[]{CalendarContract.Events.TITLE,CalendarContract.Events._ID};
        	int[] to = new int[]{R.id.tv_title, R.id.tv_id};
        	SimpleCursorAdapter adapter = new SimpleCursorAdapter(lv.getContext(), R.layout.cal_events_list_rows, eventCurs, cols, to,0);
        	lv.setAdapter(adapter);
    	}
    	
    }

    private Cursor getEvents(){
    	Cursor curs = null;
    	
    	if(etCalId.getText().length()>0){
    		long startMillis = 0;
    		long endMillis = 0;
    		Calendar beginTime = Calendar.getInstance();
    		beginTime.set(mYear, mMonth-1, mDay, mHour, mMinute);
    		startMillis = beginTime.getTimeInMillis();
    		Calendar endTime = Calendar.getInstance();
    		endTime.set(mYear, mMonth-1, mDay+10, mHour, mMinute);
    		endMillis = endTime.getTimeInMillis();

    		ContentResolver cr = getContentResolver();
    		
    		String[] EVENT_PROJECTION2 = new String[] {
    				CalendarContract.Events.DTSTART,
    				CalendarContract.Events.DTEND,
    				CalendarContract.Events.TITLE,
    				CalendarContract.Events.DESCRIPTION,
    				CalendarContract.Events._ID };

    		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    		String selection2 = CalendarContract.Events.CALENDAR_ID + " = ? AND "+CalendarContract.Events.DTEND + " < ? ";
    		String[] selectionArgs2 = new String[] { etCalId.getText().toString() + "", endMillis+""};
     
    		curs = cr.query(CalendarContract.Events.CONTENT_URI,
    				EVENT_PROJECTION2, selection2, selectionArgs2, null);

    		while (curs.moveToNext()) {

    			Date tempDate = new Date();

    			tempDate.setTime(curs.getLong(PROJECTION_DTSTART_INDEX));
    			String startDt = simpleDateFormat.format(tempDate);
    			
    			tempDate.setTime(curs.getLong(PROJECTION_DTEND_INDEX));
    			String endDt = simpleDateFormat.format(tempDate);
    			
    			String title = curs.getString(PROJECTION_TITLE_INDEX);
    			String desc = curs.getString(PROJECTION_DESCRIPTION_INDEX);
    			String id = curs.getString(PROJECTION_ID_INDEX);
    		}
    	}else{
    		Toast.makeText(this, "캘린더 아이디를 입력하세요.", Toast.LENGTH_LONG).show();
    	}
    	
		
		return curs;
    }
    
    private void insertEvent(String title){
    	
    	long startMillis = 0; 
    	long endMillis = 0;     
    	Calendar beginTime = Calendar.getInstance();
    	beginTime.set(mYear, mMonth-1, mDay, mHour, mMinute);
    	startMillis = beginTime.getTimeInMillis();
    	Calendar endTime = Calendar.getInstance();
    	endTime.set(mYear, mMonth-1, mDay+10, mHour+1, mMinute);
    	endMillis = endTime.getTimeInMillis();
    	
    	ContentResolver cr = getContentResolver();
    	ContentValues values = new ContentValues();
    	values.put(Events.DTSTART, startMillis);
    	values.put(Events.DTEND, endMillis);
    	values.put(Events.TITLE, title);
    	values.put(Events.DESCRIPTION, "Event Test");
    	values.put(Events.CALENDAR_ID, etCalId.getText().toString());
    	values.put(Events.EVENT_TIMEZONE, "Asia/Seoul");
    	Uri uri = cr.insert(Events.CONTENT_URI, values);

    	calEventID = Long.parseLong(uri.getLastPathSegment());

    }
    
    private void updateEvent(long eventID){
    	ContentResolver cr = getContentResolver();
    	ContentValues values = new ContentValues();

    	values.put(Events.TITLE, eventName.getText().toString()); 
    	Uri myUri = ContentUris.withAppendedId(Events.CONTENT_URI, eventID);
    	int rows = cr.update(myUri, values, null, null);
    }

    private void deleteEvent(long eventID){
    	ContentResolver cr = getContentResolver();
    	Uri deleteUri = null;
    	deleteUri = ContentUris.withAppendedId(Events.CONTENT_URI, eventID);
    	int rows = cr.delete(deleteUri, null, null);
    }
  
}
