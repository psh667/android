package andexam.ver4_1.c26_cp;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class EnglishWord extends Activity {
	WordDBHelper mHelper;
	EditText mText;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.englishword);

		mHelper = new WordDBHelper(this);
		mText = (EditText)findViewById(R.id.edittext);
	}
	
	public void mOnClick(View v) {
		SQLiteDatabase db;
		ContentValues row;
		switch (v.getId()) {
		case R.id.insert:
			db = mHelper.getWritableDatabase();
			// insert 메서드로 삽입
			row = new ContentValues();
			row.put("eng", "boy");
			row.put("han", "머스마");
			db.insert("dic", null, row);
			// SQL 명령으로 삽입
			db.execSQL("INSERT INTO dic VALUES (null, 'girl', '가시나');");
			mHelper.close();
			mText.setText("Insert Success");
			break;
		case R.id.delete:
			db = mHelper.getWritableDatabase();
			// delete 메서드로 삭제
			db.delete("dic", null, null);
			// SQL 명령으로 삭제
			//db.execSQL("DELETE FROM dic;");
			mHelper.close();
			mText.setText("Delete Success");
			break;
		case R.id.update:
			db = mHelper.getWritableDatabase();
			// update 메서드로 갱신
			row = new ContentValues();
			row.put("han", "소년");
			db.update("dic", row, "eng = 'boy'", null);
			// SQL 명령으로 갱신
			//db.execSQL("UPDATE dic SET han = '소년' WHERE eng = 'boy';");
			mHelper.close();
			mText.setText("Update Success");
			break;
		case R.id.select:
			db = mHelper.getReadableDatabase();
			Cursor cursor;
			// query 메서드로 읽기
			//cursor = db.query("dic", new String[] {"eng", "han"}, null, 
			//		null, null, null, null);
			// SQL 명령으로 읽기
			cursor = db.rawQuery("SELECT eng, han FROM dic", null);
		
			String Result = "";
			while (cursor.moveToNext()) {
				String eng = cursor.getString(0);
				String han = cursor.getString(1);
				Result += (eng + " = " + han + "\n");
			}

			if (Result.length() == 0) {
				mText.setText("Empyt Set");
			} else {
				mText.setText(Result);
			}
			cursor.close();
			mHelper.close();
			break;
		}
	}
}

class WordDBHelper extends SQLiteOpenHelper {
	public WordDBHelper(Context context) {
		super(context, "EngWord.db", null, 1);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE dic ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
		"eng TEXT, han TEXT);");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS dic");
		onCreate(db);
	}
}
