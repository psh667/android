package org.androidtown.database.cursor;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * 데이터베이스 조회 결과를 커서어댑터를 이용해 선택위젯에 보여주는 방법을 알 수 있습니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {
	public static final String TAG = "SampleCursorAdapter";
	
    private static String DATABASE_NAME = "employeeDB";
    private static String TABLE_NAME = "employee";
    private static int DATABASE_VERSION = 1;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    private TextView status;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = (TextView) findViewById(R.id.status);
        ListView list = (ListView) findViewById(R.id.list);

        boolean isOpen = openDatabase();
		if (isOpen) {
			Cursor cursor = executeRawQueryParam();
			startManagingCursor(cursor);

	        String[] columns = new String[] {"name", "age", "phone"};
	        int[] to = new int[] { R.id.name_entry, R.id.age_entry, R.id.phone_entry };
	        SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.listitem, cursor, columns, to);

	        list.setAdapter(mAdapter);
		}

    }


    private boolean openDatabase() {
    	println("opening database [" + DATABASE_NAME + "].");

    	dbHelper = new DatabaseHelper(this);
    	db = dbHelper.getWritableDatabase();

    	return true;
    }

	private Cursor executeRawQueryParam() {
		println("\nexecuteRawQueryParam called.\n");

		String SQL = "select _id, name, age, phone "
			+ " from " + TABLE_NAME
			+ " where age > ?";
		String[] args= {"10"};

		Cursor c1 = db.rawQuery(SQL, args);

		return c1;
	}

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
        	println("creating table [" + TABLE_NAME + "].");

        	try {
        		String DROP_SQL = "drop table if exists " + TABLE_NAME;
        		db.execSQL(DROP_SQL);
        	} catch(Exception ex) {
        		Log.e(TAG, "Exception in DROP_SQL", ex);
        	}

        	String CREATE_SQL = "create table " + TABLE_NAME + "("
							+ " _id integer PRIMARY KEY autoincrement, "
							+ " name text, "
							+ " age integer, "
							+ " phone text)";

            try {
            	db.execSQL(CREATE_SQL);
            } catch(Exception ex) {
        		Log.e(TAG, "Exception in CREATE_SQL", ex);
        	}

            println("inserting records.");

            try {
            	db.execSQL( "insert into " + TABLE_NAME + "(name, age, phone) values ('John', 20, '010-7788-1234');" );
            	db.execSQL( "insert into " + TABLE_NAME + "(name, age, phone) values ('Mike', 35, '010-8888-1111');" );
    			db.execSQL( "insert into " + TABLE_NAME + "(name, age, phone) values ('Sean', 26, '010-6677-4321');" );
            } catch(Exception ex) {
        		Log.e(TAG, "Exception in insert SQL", ex);
        	}

        }

        public void onOpen(SQLiteDatabase db) {
        	println("opened database [" + DATABASE_NAME + "].");

        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion +
                  " to " + newVersion + ".");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    private void println(String msg) {
    	Log.d(TAG, msg);
    	status.append("\n" + msg);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
