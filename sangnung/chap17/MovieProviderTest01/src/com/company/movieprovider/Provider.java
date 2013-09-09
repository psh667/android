package com.company.movieprovider;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.net.*;
import android.text.*;

class dbHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "movies.db";
	private static final int DATABASE_VERSION = 1;

	public dbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS movies");
		db.execSQL("CREATE TABLE movies ( _id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, score REAL);");
		ContentValues values = new ContentValues();

		values.put("TITLE", "Terminator 4");
		values.put("SCORE", 4.5);
		db.insert("movies", "title", values);

		values.put("TITLE", "Transporter 2");
		values.put("SCORE", 4.0);
		db.insert("movies", "title", values);

	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS movies");
		onCreate(db);
	}
}

public class Provider extends ContentProvider {
	public static final String TABLE_NAME = "movies";
	public static final Uri CONTENT_URI = Uri
			.parse("content://com.company.movieprovider/movies");

	public static final String CONTENT_TYPE = "vnd.android.cursor.dir/movies";
	public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/movies";
	public static final int MOVIE_COLLECTION = 1;
	public static final int SINGLE_MOVIE = 2;

	private static final UriMatcher matcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		matcher.addURI("com.company.movieprovider", "movies", MOVIE_COLLECTION);
		matcher.addURI("com.company.movieprovider", "movies/#", SINGLE_MOVIE);
	}

	SQLiteDatabase db;

	@Override
	public boolean onCreate() {
		dbHelper helper = new dbHelper(getContext());
		db = helper.getWritableDatabase();
		return (db == null) ? false : true;
	}

	@Override
	public String getType(Uri uri) {
		if (matcher.match(uri) == MOVIE_COLLECTION) {
			return CONTENT_TYPE;
		}
		if (matcher.match(uri) == SINGLE_MOVIE) {
			return CONTENT_ITEM_TYPE;
		}
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sort) {

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables("movies");
		if (matcher.match(uri) == SINGLE_MOVIE) {
			qb.appendWhere("_id=" + uri.getLastPathSegment());
		}
		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, sort);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		ContentValues values;

		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}
		long rowId = db.insert("movies", null, values);
		if (rowId > 0) {
			Uri uri1 = ContentUris.withAppendedId(CONTENT_URI, rowId);
			getContext().getContentResolver().notifyChange(uri1, null);
			return uri1;
		}
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;

		if (matcher.match(uri) == MOVIE_COLLECTION) {
			count = db.delete("movies", selection, selectionArgs);
		} else {
			String s = "_id = '" + uri.getPathSegments().get(1) + "'";
			if (TextUtils.isEmpty(selection) == false) {
				selection += " AND" + selection;
			}
			count = db.delete("movies", s, selectionArgs);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;

	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int rows = 0;

		if (matcher.match(uri) == MOVIE_COLLECTION) {
			rows = db.update("movies", values, selection, selectionArgs);
		} else if (matcher.match(uri) == SINGLE_MOVIE) {
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection) == true) {
				rows = db.update("movies", values, "_ID=" + id, null);
			} else {
				rows = db.update("movies", values, selection + " AND " + "_ID="
						+ id, selectionArgs);
			}
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return rows;
	}
}
