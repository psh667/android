package org.androidtown.database.helper;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 데이터베이스 헬퍼를 사용하는 가장 기본적인 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

	public static final String TAG = "MainActivity";
	
	TextView status;
	CustomerDatabase database;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    	// open database
    	if (database != null) {
    		database.close();
    		database = null;
    	}
    	
    	database = CustomerDatabase.getInstance(this);
    	boolean isOpen = database.open();
    	if (isOpen) {
    		
    		Log.d(TAG, "Customer database is open.");
    	} else {
    		
    		Log.d(TAG, "Customer database is not open.");
    	}
        
        
        Button insertBtn = (Button) findViewById(R.id.insertBtn);
        insertBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		int count = insertRecord();
        		println(count + " records inserted.");
        	}
        });
        
        Button deleteBtn = (Button) findViewById(R.id.deleteBtn);
        deleteBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		deleteRecord();
        		println("records deleted.");
        	}
        });
        
        status = (TextView) findViewById(R.id.status);

    }

    private int insertRecord() {
    	println("inserting records.");
    	
    	int count = 3;
		database.execSQL( "insert into CUSTOMER_INFO(name, age, mobile) values ('John', 20, '010-7788-1234');" );
		database.execSQL( "insert into CUSTOMER_INFO(name, age, mobile) values ('Mike', 35, '010-8888-1111');" );
		database.execSQL( "insert into CUSTOMER_INFO(name, age, mobile) values ('Sean', 26, '010-6677-4321');" );
    	
		return count;
    }
    
    private void deleteRecord() {
    	println("deleting records.");
    	
    	int count = 3;
		database.execSQL( "delete from CUSTOMER_INFO where age > 10" );
		
    }
     
    private void println(String msg) {
    	Log.d("SampleDatabaseHelper", msg);
    	status.append("\n" + msg);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
