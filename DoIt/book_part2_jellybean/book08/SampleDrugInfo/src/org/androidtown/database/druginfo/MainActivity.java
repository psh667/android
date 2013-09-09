package org.androidtown.database.druginfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	public static final String TAG = "SampleDrugInfoActivity";
	Button btnWriteDB;
	TextView txtView;

	String drugDatabaseFile = "/sdcard/druginfo.db";
	String masterSourceFile = "/sdcard/master.dat";
	String detailsSourceFile = "/sdcard/details.dat";

	/**
	 * Database instance
	 */
	SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtView = (TextView) findViewById(R.id.txtView);

        btnWriteDB = (Button) findViewById(R.id.btnWriteDB);
        btnWriteDB.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				// open Database
				openDatabase(drugDatabaseFile);

				// create MASTER database
				createTableFromSource("MASTER");

				// create DETAILS database
				createTableFromSource("DETAILS");

				// close Database
				closeDatabase();
			}
		});


    }
    

    /**
     * Create a database file from the designated data source file
     *
     * @param sourceType
     */
    public void createTableFromSource(String sourceType) {

    	if (sourceType.equals("MASTER")) {

	    	// write on SD card file data from the text box
			try {
				File myFile = new File(masterSourceFile);
				FileInputStream fIn = new FileInputStream(myFile);
				BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn, "euc-kr"));
				String aLine = "";
				String aBuffer = "";

				println("creating master table [MASTER].");
				createMasterTable();



				int count = 0;
				int recordCount = 0;
				while ((aLine = myReader.readLine()) != null) {
					println("processing line #" + count);
					boolean isInserted = insertMasterData(aLine);
					if (isInserted) {
						recordCount++;
					}

					count++;

					//if (count > 10) {
					//	break;
					//}
				}

				myReader.close();
				println("Done reading SD 'master.dat' --> " + count + " lines, " + recordCount + " records.");


				println("querying master table for 'Acarbose%'...");
				queryMasterTable();


			} catch (Exception ex) {
				ex.printStackTrace();
				println("Exception : " + ex.toString());

			}

    	} else if(sourceType.equals("DETAILS")) {

	    	// write on SD card file data from the text box
			try {
				File myFile = new File(detailsSourceFile);
				FileInputStream fIn = new FileInputStream(myFile);
				BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn, "euc-kr"));
				String aLine = "";
				String aBuffer = "";

				println("creating details table [DETAILS].");
				createDetailsTable();



				int count = 0;
				int recordCount = 0;
				while ((aLine = myReader.readLine()) != null) {
					println("processing line #" + count);
					boolean isInserted = insertDetailsData(aLine);
					if (isInserted) {
						recordCount++;
					}

					count++;

					//if (count > 20) {
					//	break;
					//}
				}

				myReader.close();
				println("Done reading SD 'details.dat' --> " + count + " lines, " + recordCount + " records.");

				println("querying details table for 'ACAR'...");
				queryDetailsTable();


			} catch (Exception ex) {
				ex.printStackTrace();
				println("Exception : " + ex.toString());

			}

    	}

    }


    public void println(String msg) {
    	Log.d(TAG, msg);
    	txtView.append("\n" + msg);
    }

    public void openDatabase(String databaseFile) {
    	println("creating or opening database [" + drugDatabaseFile + "].");
		db = SQLiteDatabase.openDatabase(
				databaseFile, null, SQLiteDatabase.OPEN_READWRITE+SQLiteDatabase.CREATE_IF_NECESSARY);

	}

    public void closeDatabase() {
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
	public void createMasterTable() {

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

	}

	/**
	 * Insert MASTER data
	 *
	 * @param aLine
	 */
	public boolean insertMasterData(String aLine) {
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
	public void queryMasterTable() {
		String aSQL = "select DRUGCODE, DRUGNAME, PRODKRNM "
			+ " from MASTER"
			+ " where DRUGNAME like ?";

		String[] args = {"Aspirin%"};

		Cursor outCursor = db.rawQuery(aSQL, args);
		int recordCount = outCursor.getCount();
		println("cursor count : " + recordCount + "\n");

		for (int i = 0; i < recordCount; i++) {
			outCursor.moveToNext();
			String productName = outCursor.getString(2);
			println("#" + i + " Α¦Η°Έν : " + productName);
		}

		outCursor.close();
	}


    /**
     * Create DETAILS table
     *
     * DRUGCODE, CLASSCODE, CLASSNAME, DETAILS
	 *
     */
	public void createDetailsTable() {

		db.execSQL("drop table if exists DETAILS");
		db.execSQL("create table DETAILS("
				+ " DRUGCODE text, "
				+ " CLASSCODE text, "
				+ " CLASSNAME text, "
				+ " DETAILS text)" );

	}

	/**
	 * Insert DETAILS data
	 *
	 * @param aLine
	 */
	public boolean insertDetailsData(String aLine) {
		// split the input line
		String[] tokens = aLine.split("\\|");
		if (tokens != null && tokens.length > 3) {
			println("length of tokens : " + tokens.length);
			db.execSQL( "insert into DETAILS(DRUGCODE, CLASSCODE, CLASSNAME, DETAILS) values (" +
					"'" + tokens[0] + "'," +
					"'" + tokens[1] + "'," +
					"'" + tokens[2] + "'," +
					"'" + tokens[3] + "')");
			return true;
		} else {
			println("the input line is invalid.");
		}

		return false;
	}

	/**
	 * Query DETAILS table for example where clause
	 * DRUGCODE = 'ACAR'
	 */
	public void queryDetailsTable() {
		String aSQL = "select DRUGCODE, CLASSNAME, DETAILS "
			+ " from DETAILS"
			+ " where DRUGCODE = ?";

		String[] args = {"ACAR"};

		Cursor outCursor = db.rawQuery(aSQL, args);
		int recordCount = outCursor.getCount();
		println("cursor count : " + recordCount + "\n");

		for (int i = 0; i < recordCount; i++) {
			outCursor.moveToNext();
			String className = outCursor.getString(1);
			String details = outCursor.getString(2);
			println("#" + i + " [" + className + "] " + details);
		}

		outCursor.close();
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
