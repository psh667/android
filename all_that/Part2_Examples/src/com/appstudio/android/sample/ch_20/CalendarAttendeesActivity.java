package com.appstudio.android.sample.ch_20;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Attendees;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.appstudio.android.sample.R;

public class CalendarAttendeesActivity extends Activity {

	
	private long attendeeID = 0;
	private EditText eventName = null;
	private EditText eventId = null;
	private ListView lv = null;
	
	private static final int PROJECTION_ATTENDEE_NAME_INDEX 	= 0;
	private static final int PROJECTION_ATTENDEE_EMAIL_INDEX 	= 1;
	private static final int PROJECTION_ATTENDEE_STATUS_INDEX 	= 2;
	private static final int PROJECTION_ATTENDEE_TYPE_INDEX 	= 3;
	private static final int PROJECTION_ID_INDEX 				= 4;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atv_calendar_attendee);
        
        init();
    }

    
    public void attendee_onClick(View v){
    	if(v.getId() == R.id.btn_do){
    		if(eventName.getText().length()>0){
				insertEvent(eventName.getText().toString());
				setListView();
			}else{
				Toast.makeText(CalendarAttendeesActivity.this, "입력값이 없습니다.", 1000).show();
			}
    	}else if(v.getId() == R.id.btn_del){
    		deleteEvent(attendeeID);
			eventName.setText("");
			attendeeID = 0;
			setListView();
    	}else if(v.getId() == R.id.btn_eventid){
    		setListView();
    	}
    }
    private void init(){
    	lv = (ListView)findViewById(R.id.listView);
    	eventName = (EditText)findViewById(R.id.et_event_name);
    	eventId = (EditText)findViewById(R.id.et_eventid);
    	
    	lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				TextView titleTv = (TextView)arg1.findViewById(R.id.tv_title);
				eventName.setText( titleTv.getText().toString() );
				
				TextView idTv = (TextView)arg1.findViewById(R.id.tv_id);
				attendeeID = Long.parseLong(idTv.getText().toString());
				
			}
		});
    	
    	setListView();
    }
    
    
    private void deleteEvent(long attendeeID){
    	ContentResolver cr = getContentResolver();
    	Uri deleteUri = ContentUris.withAppendedId(Attendees.CONTENT_URI, attendeeID);
    	int rows = cr.delete(deleteUri, null, null);
    }
    
    private Cursor getAttendeesInfo(){
    	Cursor curs = null;
    	if(eventId.getText().length()>0){
    		ContentResolver cr = getContentResolver();
        	
        	String[] EVENT_PROJECTION2 = new String[] {
    				CalendarContract.Attendees.ATTENDEE_NAME, // 0
    				CalendarContract.Attendees.ATTENDEE_EMAIL, // 1
    				CalendarContract.Attendees.ATTENDEE_STATUS, // 2
    				CalendarContract.Attendees.ATTENDEE_TYPE, // 3
    				CalendarContract.Attendees._ID };
        	
        	
        	String selection2 = CalendarContract.Attendees.EVENT_ID + " = ? ";
    		String[] selectionArgs2 = new String[] { eventId.getText().toString()};
     
        	curs = cr.query(CalendarContract.Attendees.CONTENT_URI, EVENT_PROJECTION2, selection2, selectionArgs2, null);
        	
    		while (curs.moveToNext()) {
    			String attendeeName 	= curs.getString(PROJECTION_ATTENDEE_NAME_INDEX);
    			String attendeeEmail 	= curs.getString(PROJECTION_ATTENDEE_EMAIL_INDEX);
    			String attendeeStatus 	= curs.getString(PROJECTION_ATTENDEE_STATUS_INDEX);
    			String attendeeType 	= curs.getString(PROJECTION_ATTENDEE_TYPE_INDEX);
    			String id 				= curs.getString(PROJECTION_ID_INDEX);
    		}
    		
    	}else{
    		Toast.makeText(this, "이벤트 아이디 값이 없습니다.", Toast.LENGTH_LONG).show();
    	}
    	
		return curs;
		
    }
    
    private void insertEvent(String name){
    	if(eventId.getText().length()>0){
    		ContentResolver cr = getContentResolver();
        	ContentValues values = new ContentValues();
        	values.put(Attendees.ATTENDEE_NAME, name);
        	values.put(Attendees.ATTENDEE_EMAIL, "trevor@example.com");
        	values.put(Attendees.ATTENDEE_RELATIONSHIP, Attendees.RELATIONSHIP_ATTENDEE);
        	values.put(Attendees.ATTENDEE_TYPE, Attendees.TYPE_OPTIONAL);
        	values.put(Attendees.ATTENDEE_STATUS, Attendees.ATTENDEE_STATUS_INVITED);
        	values.put(Attendees.EVENT_ID, eventId.getText().toString());
        	Uri uri = cr.insert(Attendees.CONTENT_URI, values);
    	}else{
			Toast.makeText(this, "이벤트 아이디 값이 없습니다.", Toast.LENGTH_LONG).show();
		}
    	

    }
    private void setListView(){
    	Cursor attendeeCurs =  getAttendeesInfo();
    	
    	if(attendeeCurs!=null){
    		String[] cols = new String[]{Attendees.ATTENDEE_NAME,Attendees._ID};
        	int[] to = new int[]{R.id.tv_title, R.id.tv_id};
        	SimpleCursorAdapter adapter = new SimpleCursorAdapter(lv.getContext(), R.layout.cal_attendees_list_rows, attendeeCurs, cols, to,0);
        	lv.setAdapter(adapter);
    	}
    	
    }

}
