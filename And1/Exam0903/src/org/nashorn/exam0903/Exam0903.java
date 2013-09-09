package org.nashorn.exam0903;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Exam0903 extends Activity {
	private static String DB_PATH = "/sdcard/";
    private static String DB_NAME = "dictionary.sqlite";
    private int listcount = 0;
    private int currentPos = 0;
    private boolean isPlaying = false;
	private String[] wordList = null;
	private String[] definitionList = null;    
    
    private boolean checkDataBase(){
    	SQLiteDatabase checkDB = null;
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    	}catch(SQLiteException e){
    		//database does't exist yet.
    	}
 
    	if(checkDB != null){
    		checkDB.close();
    	}
 
    	return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException{
    	InputStream myInput = this.getAssets().open(DB_NAME);
    	String outFileName = DB_PATH + DB_NAME;
    	OutputStream myOutput = new FileOutputStream(outFileName);
    	byte[] buffer = new byte[1024];
    	
    	int total_length = 0;
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    		total_length+=length;
    	}
    	
    	total_length+=length;

    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }

    public void createDataBase() throws IOException{
	 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		//NOTHING
    	}else{
        	try {
    			copyDataBase();
    		} catch (IOException e) {
        		throw new Error("Error copying database");
        	}
    	}
    }
    
    public void loadData()
    {
    	try {
			createDataBase();
		} catch (IOException ioe) {
			Toast.makeText(this, "DB 파일을 생성할 수 없습니다.", Toast.LENGTH_LONG).show();
		}
		
        listcount = 0;
    	try { 
	    	Cursor cursor;
	    	SQLiteDatabase db = SQLiteDatabase.openDatabase(DB_PATH+DB_NAME, null, 1);
	    	
	    	String[] FROM = {	"*"  	};
	    	cursor = db.query("vocabulary", FROM, null, null, null, null, null);
	    	startManagingCursor(cursor);
	    	
	    	wordList = new String[cursor.getCount()];
	        definitionList = new String[cursor.getCount()];
			
	    	while(cursor.moveToNext())
			{
	    		String word = cursor.getString(0); 
	    		String definition = cursor.getString(1);
	    		
	    		wordList[listcount] = word;
	    		definitionList[listcount] = definition;
	    		
	    		listcount++;
			}
	    	 
	     	if(db != null)
	    		db.close();
    	
    	} catch (Exception e) { 
    		Toast.makeText(this, "ERROR IN CODE:"+e.toString(), Toast.LENGTH_LONG).show();
    	}
    }
    
    public void setWordData()
    {
    	if (isPlaying == true)
    	{
    		TextView wordText = (TextView)findViewById(R.id.word);
    		TextView definitionText = (TextView)findViewById(R.id.definition);
    		
    		wordText.setText(wordList[currentPos]);
    		definitionText.setText(definitionList[currentPos]);
    		
    		currentPos++;
    		if (currentPos >= listcount)
    		{
    			currentPos = 0;
    		}
    	}
    }
    
    public void setWord()
    {
		setWordData();
		
		Handler mHandler = new Handler();
		   mHandler.postDelayed(new Runnable() { 
		      @Override
		      public void run() {
		    	  setWord();
		      }
		   }, 1000);
    }
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        loadData();
    	
    	Button startButton = (Button)findViewById(R.id.start);
    	startButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				isPlaying = true;
			}
		});
    	
    	Button stopButton = (Button)findViewById(R.id.stop);
    	stopButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				isPlaying = false;
			}
		});
    	
    	setWord();
    }
}