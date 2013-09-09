package com.appstudio.android.sample.ch_20;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
	public static final Uri CONTENT_URI = Uri
			.parse("content://com.appstudio.android.sample.ch_11_5" + "/"+ DBManager._DB_TABLENAME);
	private DBManager dbHelper;
	public final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.provider."+ DBManager._DB_TABLENAME;

	@Override
	public boolean onCreate() {
		dbHelper = new DBManager(getContext());
		return dbHelper.isReadyDb();
	}

	@Override
	public String getType(Uri uri) {
		return CONTENT_TYPE;

	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if (dbHelper.insertDB(values)) {
			Uri _uri = CONTENT_URI;
			getContext().getContentResolver().notifyChange(_uri, null);
			return _uri;
		} else {
			throw new SQLException("Failed to insert row into " + uri);
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;
		count = dbHelper.deleteDB(selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int count = 0;
		count = dbHelper.updateDB(values, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {
		Cursor c = dbHelper.selectDB(uri, projection, selection, selectionArgs,
				sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}
}
