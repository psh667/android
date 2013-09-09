package kr.co.company.chap16lab;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Chap16LabActivity extends Activity {
	// Constants
	public static final String DATABASE_NAME = "scores.db";
	public static final String SCORE_TABLE = "core";
	public static final String COLUMN_ID = "ID";
	public static final String COLUMN_SCORE = "SCORE";
	public static final String COLUMN_NAME = "NAME";
	private SQLiteDatabase scoreDB;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final EditText editTextName = (EditText) findViewById(R.id.name);
		final EditText editTextScore = (EditText) findViewById(R.id.score);
		final TextView view = (TextView) findViewById(R.id.board);
		Button saveScore = (Button) findViewById(R.id.save);
		saveScore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = editTextName.getText().toString();
				int score = 0;
				try {
					score = Integer.parseInt(editTextScore.getText().toString());
				} catch (NumberFormatException e) {
					return;
				}
				ContentValues values = new ContentValues();
				values.put(COLUMN_NAME, name);
				values.put(COLUMN_SCORE, score);
				scoreDB.insert(SCORE_TABLE, null, values);
				Cursor c = scoreDB.query(SCORE_TABLE, new String[] {
						COLUMN_NAME, COLUMN_SCORE }, null, null, null, null,
						COLUMN_SCORE);
				StringBuilder builder = new StringBuilder();
				c.moveToLast();
				for (int i = c.getCount() - 1; i >= 0; i--) {
					builder.append(c.getString(0));
					builder.append("    ");
					builder.append(c.getString(1));
					builder.append("\n");
					c.moveToPrevious();
				}
				view.setText(builder);
				c.close();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		scoreDB = openOrCreateDatabase(DATABASE_NAME,
				SQLiteDatabase.CREATE_IF_NECESSARY
						| SQLiteDatabase.OPEN_READWRITE, null);
		scoreDB.execSQL("CREATE TABLE IF NOT EXISTS " + SCORE_TABLE + " ("
				+ COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME
				+ " VARCHAR, " + COLUMN_SCORE + " INT)");
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (scoreDB.isOpen()) {
			scoreDB.close();
		}
	}

}
