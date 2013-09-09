package com.company.movieprovider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MovieProviderTest extends Activity {
	EditText display;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		display = (EditText) findViewById(R.id.display);

		findViewById(R.id.queryall).setOnClickListener(
				new Button.OnClickListener() {
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						ContentResolver cr = getContentResolver();
						Cursor cursor = cr.query(Provider.CONTENT_URI, null,
								null, null, null);
						String s = "";
						while (cursor.moveToNext()) {
							s += (cursor.getString(1) + ":"
									+ cursor.getString(2) + "\n");
						}
						display.setText(s);
						cursor.close();
					}
				});
		findViewById(R.id.queryone).setOnClickListener(
				new Button.OnClickListener() {
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						ContentResolver cr = getContentResolver();
						Cursor cursor = cr.query(Provider.CONTENT_URI, null,
								null, null, null);
						cursor.moveToNext();
						String s = "";
						s += (cursor.getString(1) + ":" + cursor.getString(2) + "\n");
						display.setText(s);
						cursor.close();
					}
				});
		findViewById(R.id.add).setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ContentResolver cr = getContentResolver();
				ContentValues values = new ContentValues();
				values.put("TITLE", "Terminator 4");
				values.put("SCORE", 4.5);
				cr.insert(Provider.CONTENT_URI, values);

				values.put("TITLE", "Transporter 2");
				values.put("SCORE", 4.0);
				cr.insert(Provider.CONTENT_URI, values);

				values.put("title", "avatar");
				values.put("score", "5.0");
				cr.insert(Provider.CONTENT_URI, values);
			}
		});
		findViewById(R.id.delete).setOnClickListener(
				new Button.OnClickListener() {
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						ContentResolver cr = getContentResolver();
						cr.delete(Provider.CONTENT_URI, null, null);
					}
				});
	}
}
