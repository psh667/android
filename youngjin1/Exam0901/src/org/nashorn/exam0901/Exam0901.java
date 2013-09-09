package org.nashorn.exam0901;

import static org.nashorn.exam0901.Constants.TABLE_NAME;
import static org.nashorn.exam0901.Constants.WORD;
import static org.nashorn.exam0901.Constants.DEFINITION;
import java.util.ArrayList;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Exam0901 extends Activity {
	private String[] wordList = null;
	private String[] definitionList = null; 	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button addButton = (Button)findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				writeDatabase();
    			readDatabase();
			}
		});
    }
    
    public void writeDatabase()
    {
    	Dictionary dictionary = new Dictionary(this);
		
		try {
			SQLiteDatabase db = dictionary.getWritableDatabase();
	    	ContentValues values = new ContentValues();
	    	
	    	values.put(WORD, "test");
	    	values.put(DEFINITION, "Å×½ºÆ®");
	    	
	    	db.insertOrThrow(TABLE_NAME, null, values);
		}
		catch (Throwable t) {
			Toast
				.makeText(this, "Exception: "+t.toString(), 2000)
				.show();
		}
    }
    
    public void readDatabase()
    {
    	Cursor cursor;
    	int listcount = 0;
    	
    	Dictionary dictionary = new Dictionary(this);
    	try
    	{
    		String[] FROM = {
    				WORD, DEFINITION };
        	SQLiteDatabase db = dictionary.getReadableDatabase();
        	cursor = db.query(TABLE_NAME, FROM, null, null, null, null, null);
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
    	}
		finally
		{
			dictionary.close();
		}	
		
		if (listcount > 0)
    	{
    		ArrayList<String> listString = new ArrayList<String>();
    		for (int i = 0; i < wordList.length; i++)
    		{
    			listString.add(wordList[i]+" ("+definitionList[i]+")");
    		}
    		
        	ListView listView = (ListView)findViewById(R.id.list);
    		
        	ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getBaseContext(),
    			android.R.layout.simple_list_item_1, listString);

      		listView.setAdapter(arrayAdapter);	
    	}
    }
}