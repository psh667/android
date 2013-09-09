package kr.co.company.chap17lab;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import java.util.GregorianCalendar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.view.View;
import android.widget.Toast;

public class Chap17LabActivity extends Activity {
	public static final String[] EVENT_PROJECTION = new String[] {
			Calendars._ID, // 0
			Calendars.ACCOUNT_NAME, // 1
			Calendars.CALENDAR_DISPLAY_NAME // 2
	};
	private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void onClick(View view) {
		
	}

	@SuppressLint("NewApi")
	public void queryCalendar(View view) {
		Cursor cur = null;
		ContentResolver cr = getContentResolver();
		Uri uri = Calendars.CONTENT_URI;
		String selection = null;
		String[] selectionArgs = null;
		cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
		while (cur.moveToNext()) {
			String displayName = null;
			displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
			Toast.makeText(this, "Calendar " + displayName, Toast.LENGTH_SHORT)
					.show();
		}
	}
}
