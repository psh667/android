package andexam.ver4_1.c26_cp;

import andexam.ver4_1.*;
import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.net.*;
import android.text.*;

public class EWProvider extends ContentProvider {
	static final Uri CONTENT_URI = Uri.parse("content://andexam.ver4_1.EnglishWord/word");
	static final int ALLWORD = 1;
	static final int ONEWORD = 2;
	
	static final UriMatcher Matcher;
	static {
		Matcher = new UriMatcher(UriMatcher.NO_MATCH);
		Matcher.addURI("andexam.ver4_1.EnglishWord", "word", ALLWORD);
		Matcher.addURI("andexam.ver4_1.EnglishWord", "word/*", ONEWORD);
	}
	
	SQLiteDatabase mDB;

	public boolean onCreate() {
		WordDBHelper helper = new WordDBHelper(getContext());
		mDB = helper.getWritableDatabase();
		return true;
	}

	public String getType(Uri uri) {
		if (Matcher.match(uri) == ALLWORD) {
			return "vnd.EnglishWord.ver4_1.andexam.cursor.item/word";
		}
		if (Matcher.match(uri) == ONEWORD) {
			return "vnd.EnglishWord.ver4_1.andexam.cursor.dir/word";
		}
		return null;
	}

	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		String sql;
		
		// 전체에 대한 쿼리 명령
		sql = "SELECT eng, han FROM dic";
		
		// 단어 선택 where절 추가
		if (Matcher.match(uri) == ONEWORD) {
			sql += " where eng = '" + uri.getPathSegments().get(1) + "'";
		}

		Cursor cursor = mDB.rawQuery(sql, null);
		return cursor;
	}

	public Uri insert(Uri uri, ContentValues values) {
		long row = mDB.insert("dic", null, values);
		if (row > 0) {
			Uri notiuri = ContentUris.withAppendedId(CONTENT_URI, row);
			getContext().getContentResolver().notifyChange(notiuri, null);
			return notiuri;
		}
		return null;
	}

	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;
		
		//*
		switch (Matcher.match(uri)) {
		case ALLWORD:
			count = mDB.delete("dic", selection, selectionArgs);
			break;
		case ONEWORD:
			String where;
			where = "eng = '" + uri.getPathSegments().get(1) + "'";
			if (TextUtils.isEmpty(selection) == false) {
				where += " AND" + selection;
			}
			count = mDB.delete("dic", where, selectionArgs);
			break;
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
		//*/
		
		/*
		String sql;
		
		// 전체에 대한 쿼리 명령
		sql = "DELETE FROM dic";
		
		// 단어 선택 where절 추가
		if (Matcher.match(uri) == ONEWORD) {
			sql += " where eng = '" + uri.getPathSegments().get(1) + "'";
		}
		mDB.execSQL(sql);
		return 1;
		//*/
	}

	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int count = 0;
		
		switch (Matcher.match(uri)) {
		case ALLWORD:
			count = mDB.update("dic", values, selection, selectionArgs);
			break;
		case ONEWORD:
			String where;
			where = "eng = '" + uri.getPathSegments().get(1) + "'";
			if (TextUtils.isEmpty(selection) == false) {
				where += " AND " + selection;
			}
			count = mDB.update("dic", values, where, selectionArgs);
			break;
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
}
