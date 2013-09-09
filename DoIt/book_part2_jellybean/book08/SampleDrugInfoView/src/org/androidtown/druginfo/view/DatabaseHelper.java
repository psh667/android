package org.androidtown.druginfo.view;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;


public class DatabaseHelper  {
	
	public static final String TAG = "DatabaseHelper"; 
	public static SQLiteDatabase	db;
	public static String drugDatabaseFile = "/sdcard/druginfo.db";

	public DatabaseHelper() {
		
	}
	
	
	public static void openDatabase(String databaseFile) {
    	
    	println("creating or opening database [" + drugDatabaseFile + "].");

    	try {
    		db = SQLiteDatabase.openDatabase(
    	    				databaseFile, null, SQLiteDatabase.OPEN_READWRITE);

    		
    		//Toast.makeText(this, "DB was opened!", 1).show();
    	} catch (SQLiteException ex) {
    		//Toast.makeText(this, ex.getMessage(), 1).show();
    	}
    }

    public static void closeDatabase() {
		try {
			// close database
			db.close();
		} catch(Exception ext) {
			ext.printStackTrace();
			println("Exception in closing database : " + ext.toString());
		}
    }
    
    /**
     * Create MASTER table
     * 
     * DRUGCODE, DRUGNAME, PRODENNM, PRODKRNM, PHRMNAME, DISTRNAME, REPDGID, REPDGNAME 
	 *
     */    
    private static void createMasterTable() {
    	try {
    		db.execSQL("drop table if exists MASTER");
    		db.execSQL("create table MASTER(" 
    				+ " DRUGCODE text, " 
    				+ " DRUGNAME text, "
    				+ " PRODENNM text, "
    				+ " PRODKRNM text, "
    				+ " PHRMNAME text, "
    				+ " DISTRNAME text, "
    				+ " REPDGID text, "
    				+ " REPDGNAME text)" );
    		
    		//Toast.makeText(this, "Table was created!", 1).show();
    	} catch (SQLiteException ex) {
    		//Toast.makeText(this, ex.getMessage(), 1).show();
    	}
    	
    }
    
	/**
	 * Insert MASTER data
	 * 
	 * @param aLine
	 */
	public static boolean insertMasterData(String aLine) {
		// split the input line
		String[] tokens = aLine.split("\\|");
		if (tokens != null && tokens.length > 7) {
			println("length of tokens : " + tokens.length);
			db.execSQL( "insert into MASTER(DRUGCODE, DRUGNAME, PRODENNM, PRODKRNM, PHRMNAME, DISTRNAME, REPDGID, REPDGNAME) values (" +
					"'" + tokens[0] + "'," +
					"'" + tokens[1] + "'," +
					"'" + tokens[2] + "'," +
					"'" + tokens[3] + "'," +
					"'" + tokens[4] + "'," +
					"'" + tokens[5] + "'," +
					"'" + tokens[6] + "'," +
					"'" + tokens[7] + "')");
			return true;
		} else {
			println("the input line is invalid.");
		}
		
		return false;
	}
	
	/**
	 * Query MASTER table for example where clause
	 * DRUGNAME like 'Acarbose%'
	 */
	public static Cursor queryMasterTable(String strSearchWord ) {
		String aSQL = "select DRUGCODE, DRUGNAME, PRODKRNM, DISTRNAME "
			+ " from MASTER"
			+ " where DRUGNAME like ?";
		
		String[] args = {strSearchWord};

		Cursor outCursor = db.rawQuery(aSQL, args);
		
		return (outCursor);
	}
	
	/**
	 * Query DETAILS table for example where clause
	 * DRUGCODE = 'ACAR'
	 */
	public static Cursor queryDetailsTable(String strDrugCode) {
		String aSQL = "select DRUGCODE, CLASSCODE, CLASSNAME, DETAILS "
			+ " from DETAILS"
			+ " where DRUGCODE = ?";

		String[] args = {strDrugCode};

		Cursor outCursor = db.rawQuery(aSQL, args);
		
		return (outCursor);
		
	}    
	

	
	public static void println(String msg) {
    	Log.d(TAG, msg);
    	//txtView.append("\n" + msg);
    }

	
	
}