package com.appstudio.android.sample.ch_20;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Reminders;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.appstudio.android.sample.R;

public class CalendarRemindersActivity extends Activity {

	private long reminderID = 0;
	private EditText eventName = null;
	private EditText eventId = null;
	private ListView lv = null;
	private Spinner sp = null;
	private int spVal = 0;
	
	private String[] items = {"METHOD_DEFAULT", "METHOD_ALERT", "METHOD_EMAIL", "METHOD_SMS"};
	
	private static final int PROJECTION_ID_INDEX 		= 0;
	private static final int PROJECTION_MINUTES_INDEX 	= 1;
	private static final int PROJECTION_METHOD_INDEX 	= 2;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_calendar_reminder);
        
        init();
    }
    
    public void reminder_onClick(View v){
    	if(v.getId() == R.id.btn_do){
    		if(eventName.getText().length()>0){
				int min = Integer.parseInt(eventName.getText().toString());
				
				insertEvent(min, spVal);
				setListView();
			}else{
				Toast.makeText(CalendarRemindersActivity.this, "시간 입력값이 없습니다.", Toast.LENGTH_SHORT).show();
			}
    	}else if(v.getId() == R.id.btn_del){
    		deleteEvent(reminderID);
			eventName.setText("");
			reminderID = 0;
			setListView();
    	}else if(v.getId() == R.id.btn_eventid){
    		setListView();
    	}
    }
    private void init(){
		
    	lv = (ListView)findViewById(R.id.listView);
    	eventName = (EditText)findViewById(R.id.et_event_name);
    	eventId = (EditText)findViewById(R.id.et_eventid);
    	
    	sp = (Spinner)findViewById(R.id.spinner);
    	
    	sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				spVal = arg2;
				Toast.makeText(getApplicationContext(), arg2 +"", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
    	ArrayAdapter<String> aAdater = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
    	aAdater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	sp.setAdapter(aAdater);
        
    	lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				TextView titleTv = (TextView)arg1.findViewById(R.id.tv_min);
				eventName.setText( titleTv.getText().toString() );
				
				TextView methodTv = (TextView)arg1.findViewById(R.id.tv_method);
				
				TextView idTv = (TextView)arg1.findViewById(R.id.tv_id);
				reminderID = Long.parseLong(idTv.getText().toString());
			}
		});
    	
    	setListView();
    
	}
    
    private void insertEvent(int minutes,int methodType){
    	if(eventId.getText().length() > 0){
    		ContentResolver cr = getContentResolver();
        	ContentValues values = new ContentValues();
        	values.put(Reminders.MINUTES, 15);
        	values.put(Reminders.EVENT_ID, eventId.getText().toString());
        	values.put(Reminders.METHOD, Reminders.METHOD_ALERT);
        	Uri uri = cr.insert(Reminders.CONTENT_URI, values);
    	}
    }
    private void deleteEvent(long reminderID){
    	ContentResolver cr = getContentResolver();
    	Uri deleteUri = ContentUris.withAppendedId(Reminders.CONTENT_URI, reminderID);
    	int rows = cr.delete(deleteUri, null, null);
    }
    
    private void setListView(){
    	Cursor remindersCurs =  getRemindersData();
    	
    	if(remindersCurs!=null){
    		String[] cols = new String[]{Reminders._ID,Reminders.MINUTES,Reminders.METHOD};
        	int[] to = new int[]{R.id.tv_id, R.id.tv_min, R.id.tv_method};
        	SimpleCursorAdapter adapter = new SimpleCursorAdapter(lv.getContext(), R.layout.cal_reminder_list_rows, remindersCurs, cols, to,0);
        	lv.setAdapter(adapter);
    	}
    	
    }
    
    private Cursor getRemindersData() {
    	Cursor curs = null;
    	if(eventId.getText().length() > 0){
    		ContentResolver cr = getContentResolver();
        	
        	String[] EVENT_PROJECTION2 = new String[] {
    				CalendarContract.Reminders._ID, 
    				CalendarContract.Reminders.MINUTES,
    				CalendarContract.Reminders.METHOD};
        	
    		curs = CalendarContract.Reminders.query(cr,Long.parseLong(eventId.getText().toString()),EVENT_PROJECTION2);

    		while (curs.moveToNext()) {
    			String id 	   = curs.getString(PROJECTION_ID_INDEX);
    			String minutes = curs.getString(PROJECTION_MINUTES_INDEX);
    			String methods = curs.getString(PROJECTION_METHOD_INDEX);
    		}
    	}else{
    		Toast.makeText(this, "이벤트 아이디 값을 입력하세요.", Toast.LENGTH_LONG).show();
    	}
    	
		return curs;
    }
    
}
