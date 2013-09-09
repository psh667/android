package com.appstudio.android.sample.ch_20;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appstudio.android.sample.R;

public class CalendarTableActivity extends Activity {

	public static final String[] EVENT_PROJECTION = new String[] {
		CalendarContract.Calendars._ID,
		CalendarContract.Calendars.ACCOUNT_NAME,
		CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
		CalendarContract.Calendars.OWNER_ACCOUNT
	};

	private static final int PROJECTION_ID_INDEX 			= 0;
	private static final int PROJECTION_ACCOUNT_NAME_INDEX 	= 1;
	private static final int PROJECTION_DISPLAY_NAME_INDEX 	= 2;
	private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
	
	private TextView tv = null;
	private EditText etDisplay = null;
	private EditText etAccount = null;

	private long calID = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.atv_calendar_table);
		
		init();
	}

	private void init(){
		tv = (TextView)findViewById(R.id.textView1);
		etDisplay = (EditText)findViewById(R.id.et_displayname);
		etAccount = (EditText)findViewById(R.id.et_account);
		
		getCalendarData();
		
	}
	
	public void cal_table_onClick(View v){
		 if(v.getId()==R.id.btn_account){
			 getCalendarData();
		 }else if(v.getId() == R.id.btn_change){
			 updateCalendarData(calID);
				getCalendarData();
		 }
	}
	private void updateCalendarData(long calID) {
		
		ContentValues values = new ContentValues();

		if(etDisplay.getText().length()>0){
			values.put(Calendars.CALENDAR_DISPLAY_NAME, etDisplay.getText().toString());
			Uri updateUri = ContentUris.withAppendedId(Calendars.CONTENT_URI, calID);
			int rows = getContentResolver().update(updateUri, values, null, null);
		}
	}
	private void getCalendarData() {

		if(etAccount.getText().length()>0){
			Cursor cur = null;
			ContentResolver cr = getContentResolver();

			Uri uri = CalendarContract.Calendars.CONTENT_URI;

			String selection = CalendarContract.Calendars.ACCOUNT_NAME
					+ " = ? AND " + CalendarContract.Calendars.ACCOUNT_TYPE
					+ " = ? AND " + CalendarContract.Calendars.OWNER_ACCOUNT
					+ " = ?";

			
			String[] selectionArgs = new String[] { etAccount.getText().toString(),"com.google", etAccount.getText().toString() };

			cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);

			

			while (cur.moveToNext()) {
				calID = 0;
				String displayName = null;
				String accountName = null;
				String ownerName = null;

				calID = cur.getLong(PROJECTION_ID_INDEX);
				displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
				accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
				ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);
				
				tv.setText("DISPLAY_NAME :" +displayName+"\n");
				tv.setText(tv.getText().toString() + "ID :" +calID+"\n");
				tv.setText(tv.getText().toString() + "ACCOUNT_NAME :" +accountName+"\n");
			}
		}else{
			Toast.makeText(this, "계정정보를 입력하세요.", Toast.LENGTH_LONG).show();
		}
		

	}

}
