package com.appstudio.android.sample.ch_20;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class DBManager extends SQLiteOpenHelper {
	  private static final String DB_NAME  = "ch_11_5_db";
	  private static final int DB_VERSION  = 1;
	  public static final String _DB_TABLENAME = "userinfo";
	  public static final String _ID  = "_id";
	  public static final String DESC  = "description";
	  
	  private static final String _CREATE_STATEMENT  = 
	    "create table "+ _DB_TABLENAME+"("
	    + _ID + " integer primary key autoincrement, "
	    + DESC + " text);";
	  
	  private SQLiteDatabase userDB;   
	  

	  public DBManager(Context context) {
	    super(context, DB_NAME, null, DB_VERSION);
	    userDB = this.getWritableDatabase();
	  }
	  
	  public boolean isReadyDb(){
	    return (userDB == null) ? false : true;
	  }

	  @Override
	  public void onCreate(SQLiteDatabase db) {
	    db.execSQL(_CREATE_STATEMENT);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
	  }
	  
	  
	  public boolean insertDB(ContentValues values) {
	   if (userDB.insert(_DB_TABLENAME, "", values) > 0) {
	     return true;
	   }else{
	     return false;
	   }
	  }
	  
	  public Cursor selectDB(Uri uri, String[] projection, String selection,String[] selectionArgs,  String sortOrder) {
	    
	    SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
	    sqlBuilder.setTables(_DB_TABLENAME);
	    Cursor c = sqlBuilder.query(userDB, projection, 
	        selection,selectionArgs, null, null, sortOrder);
	    return c;
	  }
	  
	  
	  public int deleteDB(String selection, String[] selectionArgs) {
	    return userDB.delete(DBManager._DB_TABLENAME, 
	        selection,selectionArgs);
	  }
	  
	  public int updateDB(ContentValues values, String selection, String[] selectionArgs) {
	    return userDB.update(DBManager._DB_TABLENAME, 
	        values, selection,selectionArgs);
	  }
	}
