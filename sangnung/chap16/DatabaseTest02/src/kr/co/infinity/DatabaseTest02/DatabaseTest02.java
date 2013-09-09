package kr.co.infinity.DatabaseTest02;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

class dbHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "contacts.db";
	private static final int DATABASE_VERSION = 2;

	public dbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE contact ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "name TEXT, tel TEXT);");
		for (int i = 0; i < 20; i++) {
			db.execSQL("INSERT INTO contact VALUES (null, " + "'This is a sample data " + i
					+ "'" + ", '010-9801-100" + i + "');");
		}
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS contact");
		onCreate(db);
	}
}

public class DatabaseTest02 extends Activity {
	dbHelper helper;
	SQLiteDatabase db;
	EditText edit_name, edit_tel;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		helper = new dbHelper(this);
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM contact", null);
		startManagingCursor(cursor);
		String[] from = { "name", "tel" };
		int[] to = { android.R.id.text1, android.R.id.text2 };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_2, cursor, from, to);
		ListView list = (ListView) findViewById(R.id.list);
		list.setAdapter(adapter);

	}

}
