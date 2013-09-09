package com.appstudio.android.sample.ch_25_1;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MemoDatasource {
    private SQLiteDatabase	database;
    private MySQLiteHelper	dbHelper;
    private String[] allColumns	= { MySQLiteHelper.COLUMN_ID,
                                  MySQLiteHelper.COLUMN_MEMO };

    public MemoDatasource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Memo createMemo(String memo) {
        Cursor cursor = null;
        try {
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_MEMO, memo);
            long insertId = database.insert(
                    MySQLiteHelper.TABLE_MEMOS, null,values);
            // 지금 막 insert한것을 다시 조회하여 확인
            cursor = database.query(MySQLiteHelper.TABLE_MEMOS
                    ,allColumns, MySQLiteHelper.COLUMN_ID
                    + " = " + insertId,null, null, null, null);
            cursor.moveToFirst();
            return cursorToMemo(cursor);
        } finally {
            closeCursor(cursor);
        }
    }

    public void deleteMemo(Memo memo) {
        long id = memo.getId();
        database.delete(MySQLiteHelper.TABLE_MEMOS,
                MySQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public List<Memo> getAllMemos() {
        List<Memo> memos = new ArrayList<Memo>();
        Cursor cursor = null;
        try {
            cursor = database.query(MySQLiteHelper.TABLE_MEMOS,
                    allColumns, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Memo memo = cursorToMemo(cursor);
                memos.add(memo);
                cursor.moveToNext();
            }
            return memos;
        } finally {
            closeCursor(cursor);
        }
    }

    private void closeCursor(Cursor cursor) {
        try {
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
        }
    }

    private Memo cursorToMemo(Cursor cursor) {
        Memo memo = new Memo();
        int idIndex = 
            cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID);
        int memoIndex = cursor.getColumnIndex(
                MySQLiteHelper.COLUMN_MEMO);
        memo.setId(cursor.getLong(idIndex));
        memo.setMemo(cursor.getString(memoIndex));
        return memo;
    }
}
