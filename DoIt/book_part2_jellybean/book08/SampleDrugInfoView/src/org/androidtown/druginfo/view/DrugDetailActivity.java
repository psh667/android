package org.androidtown.druginfo.view;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DrugDetailActivity extends Activity {
	public static final String TAG = "DrugDetailActivity";
	TextView txtMsg;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        txtMsg = (TextView)findViewById(R.id.txtMsg);
        
        
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        
        String prodKName = bundle.getString("data0");
        String drugCode = bundle.getString("data1");
        String drugName = bundle.getString("data2");
        String distrName = bundle.getString("data3");

        Cursor cursor = DatabaseHelper.queryDetailsTable(drugCode);
        
        HandleCursorData (cursor );
        

    }
    
    
    public void HandleCursorData ( Cursor outCursor ) {
		
		int recordCount = outCursor.getCount();
		println("cursor count : " + recordCount + "\n");
		
		// get column index
		int drugCodeCol = outCursor.getColumnIndex("DRUGCODE");
		int classCodeCol = outCursor.getColumnIndex("CLASSCODE");
		int classNameCol = outCursor.getColumnIndex("CLASSNAME");
		int detailsCol = outCursor.getColumnIndex("DETAILS");
		
		for (int i = 0; i < recordCount; i++) {
			outCursor.moveToNext();
			String drugCode = outCursor.getString(drugCodeCol);
			String classCode = outCursor.getString(classCodeCol);
			String className = outCursor.getString(classNameCol);
			String details = outCursor.getString(detailsCol);

	        txtMsg.append("\n");
	        txtMsg.append("[" + className + "]\n");
	        txtMsg.append("\n");
	        txtMsg.append(details + "\n\n");
	        txtMsg.append("--------------------------------------------------------");
	        txtMsg.append("\n");
			
		}
		
		outCursor.close();
		
    }
    
	public void println(String msg) {
    	Log.d(TAG, msg);
    }    
    
}
