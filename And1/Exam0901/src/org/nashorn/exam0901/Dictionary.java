package org.nashorn.exam0901;

import android.database.sqlite.*;
import android.content.*;

import static android.provider.BaseColumns._ID;

import static org.nashorn.exam0901.Constants.TABLE_NAME;
import static org.nashorn.exam0901.Constants.WORD;
import static org.nashorn.exam0901.Constants.DEFINITION;

public class Dictionary extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "dictionary.db";
	private static final int DATABASE_VERSION = 1;
	
	public Dictionary(Context ctx)
	{
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL("CREATE TABLE "+TABLE_NAME+" ("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
				WORD+" TEXT, "+DEFINITION+" TEXT);");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
		onCreate(db);
	}
}