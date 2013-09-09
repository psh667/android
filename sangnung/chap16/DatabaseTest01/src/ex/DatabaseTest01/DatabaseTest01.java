package ex.DatabaseTest01;

import android.app.*;
import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.os.*;
import android.text.Editable;
import android.view.*;
import android.widget.*;

class dbHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "mycontacts.db";
	private static final int DATABASE_VERSION = 2;

	public dbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE contact ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "name TEXT, tel TEXT);");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS contact");
		onCreate(db);
	}
}

public class DatabaseTest01 extends Activity {
	dbHelper helper;
	SQLiteDatabase db;
	EditText edit_name, edit_tel;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		helper = new dbHelper(this);
		try {
			db = helper.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = helper.getReadableDatabase();
		}

		edit_name = (EditText) findViewById(R.id.name);
		edit_tel = (EditText) findViewById(R.id.tel);

		findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				String name = edit_name.getText().toString();
				String tel = edit_tel.getText().toString();
				db.execSQL("INSERT INTO contact VALUES (null, '" + name
						+ "', '" + tel + "');");
			}
		});
		findViewById(R.id.query).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String name = edit_name.getText().toString();
				Cursor cursor;
				cursor = db.rawQuery(
						"SELECT name, tel FROM contact WHERE name='" + name
								+ "';", null);

				while (cursor.moveToNext()) {
					String tel = cursor.getString(1);
					edit_tel.setText(tel);
				}
			}
		});
	}

}
