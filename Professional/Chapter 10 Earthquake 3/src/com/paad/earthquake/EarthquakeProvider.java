package com.paad.earthquake;

import java.util.HashMap;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.LiveFolders;
import android.text.TextUtils;
import android.util.Log;

public class EarthquakeProvider extends ContentProvider {
	
	public static final Uri CONTENT_URI = Uri.parse("content://com.paad.provider.earthquake/earthquakes");
	public static final Uri LIVE_FOLDER_URI = Uri.parse("content://com.paad.provider.earthquake/live_folder");
	public static final Uri SEARCH_URI = Uri.parse("content://com.paad.provider.earthquake/" + SearchManager.SUGGEST_URI_PATH_QUERY);

	// 하부 데이터베이스.
	private SQLiteDatabase earthquakeDB;
	private static final String TAG = "EarthquakeProvider";
	private static final String DATABASE_NAME = "earthquakes.db";
	private static final int DATABASE_VERSION = 1;
	private static final String EARTHQUAKE_TABLE = "earthquakes";

	// 열 이름.
	public static final String KEY_ID = "_id";
	public static final String KEY_DATE = "date";
	public static final String KEY_DETAILS = "details";
	public static final String KEY_LOCATION_LAT = "latitude";
	public static final String KEY_LOCATION_LNG = "longitude";
	public static final String KEY_MAGNITUDE = "magnitude";
	public static final String KEY_LINK = "link";

	// 열 인덱스.
	public static final int DATE_COLUMN = 1;
	public static final int DETAILS_COLUMN = 2;
	public static final int LONGITUDE_COLUMN = 3;
	public static final int LATITUDE_COLUMN = 4;
	public static final int MAGNITUDE_COLUMN = 5;
	public static final int LINK_COLUMN = 6;
	
	// 서로 다른 URI 요청들을 구분하는데 사용할 상수들을 만든다.
	private static final int QUAKES = 1;
	private static final int QUAKE_ID = 2;
	private static final int LIVE_FOLDER = 3;
	private static final int SEARCH = 4;

	private static final UriMatcher uriMatcher;

	// UriMatcher 객체를 할당한다.
	// 'earthquakes'로 끝나는 URI는 지진 정보 전부를 요청하는 것이고,
	// '/[행 ID]'로 끝나는 URI는 지진 정보 하나를 요청하는 것이다.
	static {
	    uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	    uriMatcher.addURI("com.paad.provider.Earthquake", "earthquakes", QUAKES);
	    uriMatcher.addURI("com.paad.provider.Earthquake", "earthquakes/#", QUAKE_ID);
	    uriMatcher.addURI("com.paad.provider.earthquake", "live_folder", LIVE_FOLDER);
	    uriMatcher.addURI("com.paad.provider.earthquake", SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH);
	    uriMatcher.addURI("com.paad.provider.earthquake", SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SEARCH);
	    uriMatcher.addURI("com.paad.provider.earthquake", SearchManager.SUGGEST_URI_PATH_SHORTCUT, SEARCH);
	    uriMatcher.addURI("com.paad.provider.earthquake", SearchManager.SUGGEST_URI_PATH_SHORTCUT + "/*", SEARCH);
	}
	
	static final HashMap<String, String> LIVE_FOLDER_PROJECTION;
	
	static {
	    LIVE_FOLDER_PROJECTION = new HashMap<String, String>();
	    LIVE_FOLDER_PROJECTION.put(LiveFolders._ID, KEY_ID + " AS " + LiveFolders._ID);
	    LIVE_FOLDER_PROJECTION.put(LiveFolders.NAME, KEY_DETAILS + " AS " + LiveFolders.NAME);
	    LIVE_FOLDER_PROJECTION.put(LiveFolders.DESCRIPTION, KEY_DATE + " AS " + LiveFolders.DESCRIPTION);
	}
	
	private static final HashMap<String, String> SEARCH_PROJECTION_MAP;

	static {
	    SEARCH_PROJECTION_MAP = new HashMap<String, String>();
	    SEARCH_PROJECTION_MAP.put(SearchManager.SUGGEST_COLUMN_TEXT_1, KEY_DETAILS + " AS " + SearchManager.SUGGEST_COLUMN_TEXT_1);
	    SEARCH_PROJECTION_MAP.put("_id", KEY_ID + " AS " + "_id");
	}


	// 데이터베이스의 생성과 개방 그리고 버전 제어를 관리하는 도우미 클래스.
	private static class EarthquakeDatabaseHelper extends SQLiteOpenHelper {
	    private static final String DATABASE_CREATE =
	        "create table " + EARTHQUAKE_TABLE + " ("
	        + KEY_ID + " integer primary key autoincrement, "
	        + KEY_DATE + " INTEGER, "
	        + KEY_DETAILS + " TEXT, "
	        + KEY_LOCATION_LAT + " FLOAT, "
	        + KEY_LOCATION_LNG + " FLOAT, "
	        + KEY_MAGNITUDE + " FLOAT, "
	        + KEY_LINK + " TEXT);";

	    public EarthquakeDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
	        super(context, name, factory, version);
	    }

	    @Override
	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL(DATABASE_CREATE);
	    }

	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
	                   + newVersion + ", which will destroy all old data");

	        db.execSQL("DROP TABLE IF EXISTS " + EARTHQUAKE_TABLE);
	        onCreate(db);
	    }
	}
	
	@Override
	public boolean onCreate() {
	    Context context = getContext();

	    EarthquakeDatabaseHelper dbHelper = new EarthquakeDatabaseHelper(context,
	        DATABASE_NAME, null, DATABASE_VERSION);
	    earthquakeDB = dbHelper.getWritableDatabase();
	    return (earthquakeDB == null) ? false : true;
	}

	@Override
	public String getType(Uri uri) {
	    switch (uriMatcher.match(uri)) {
	    	case QUAKES|LIVE_FOLDER : return "vnd.android.cursor.dir/vnd.paad.earthquake";
	        case QUAKES: return "vnd.android.cursor.dir/vnd.paad.earthquake";
	        case QUAKE_ID: return "vnd.android.cursor.item/vnd.paad.earthquake";
	        case SEARCH : return SearchManager.SUGGEST_MIME_TYPE;
	        default: throw new IllegalArgumentException("Unsupported URI: " + uri);
	    }
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sort) {

	    SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

	    qb.setTables(EARTHQUAKE_TABLE);

	    // URI가 하나의 행을 요청하는 형식이라면,
	    // 결과 셋이 요청된 행만을 가지도록 제한한다.
	    switch (uriMatcher.match(uri)) {
	        case QUAKE_ID:
	        	qb.appendWhere(KEY_ID + "=" + uri.getPathSegments().get(1));
	            break;
	        case LIVE_FOLDER :
	        	qb.setProjectionMap(LIVE_FOLDER_PROJECTION);
                break;
	        case SEARCH :
	        	qb.appendWhere(KEY_DETAILS + " LIKE \"%" + uri.getPathSegments().get(1) + "%\"");
	        	qb.setProjectionMap(SEARCH_PROJECTION_MAP);
	        	break;
	        default :
	        	break;
	    }

	    // 지정된 정렬 순서가 없을 때는, 날짜/시간 순으로 정렬한다.
	    String orderBy;
	    if (TextUtils.isEmpty(sort)) {
	        orderBy = KEY_DATE;
	    } else {
	        orderBy = sort;
	    }

	    // 데이터베이스에 질의한다.
	    Cursor c = qb.query(earthquakeDB, projection,
                            selection, selectionArgs,
                            null, null,
                            orderBy);

	    // 커서의 결과 셋이 변경될 경우 통지 받을 수 있도록
	    // 컨텍스트의 ContentResolver를 등록한다.
	    c.setNotificationUri(getContext().getContentResolver(), uri);

	    // 질의 결과 커서를 리턴한다.
	    return c;
	}

	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
	    // 새로운 행을 삽입한다.
	    // 행이 성공적으로 삽입된 경우에는, 행 번호가 리턴 될 것이다.
	    long rowID = earthquakeDB.insert(EARTHQUAKE_TABLE, "quake", initialValues);

	    // 성공적으로 삽입된 새로운 행의 URI를 리턴한다.
	    if (rowID > 0) {
	        Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
	        getContext().getContentResolver().notifyChange(_uri, null);
	        return _uri;
	    }
	    throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
	    int count;

	    switch (uriMatcher.match(uri)) {
	        case QUAKES:
	            count = earthquakeDB.delete(EARTHQUAKE_TABLE, where, whereArgs);
	            break;

	        case QUAKE_ID:
	            String segment = uri.getPathSegments().get(1);
	            count = earthquakeDB.delete(EARTHQUAKE_TABLE, KEY_ID + "="
	                                                + segment
	                                                + (!TextUtils.isEmpty(where) ? " AND ("
	                                                + where + ')' : ""), whereArgs);
	            break;

	        default: throw new IllegalArgumentException("Unsupported URI: " + uri);
	    }

	    getContext().getContentResolver().notifyChange(uri, null);
	    return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
	    int count;
	    switch (uriMatcher.match(uri)) {
	        case QUAKES: count = earthquakeDB.update(EARTHQUAKE_TABLE, values, where, whereArgs);
	            break;

	        case QUAKE_ID: String segment = uri.getPathSegments().get(1);
	            count = earthquakeDB.update(EARTHQUAKE_TABLE, values, KEY_ID
	                                        + "=" + segment
	                                        + (!TextUtils.isEmpty(where) ? " AND ("
	                                        + where + ')' : ""), whereArgs);
	            break;

	        default: throw new IllegalArgumentException("Unknown URI " + uri);
	    }

	    getContext().getContentResolver().notifyChange(uri, null);
	    return count;
	}
}
