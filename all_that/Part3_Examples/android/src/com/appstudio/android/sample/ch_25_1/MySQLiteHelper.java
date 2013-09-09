package com.appstudio.android.sample.ch_25_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_MEMOS = "memos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MEMO = "memo";

    private static final String DATABASE_NAME = "memos.db";
    private static final int DATABASE_VERSION = 2;

    // 테이블을 생성하는 sql문
    private static final String DATABASE_CREATE = 
        "create table "+ TABLE_MEMOS + "( " + COLUMN_ID
        + " integer primary key autoincrement, " + COLUMN_MEMO
        + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, 
            int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMOS);
        onCreate(db);
    }
}