package com.corea.ListViewClickDemo;

import android.app.Activity;
import android.os.Bundle;
//import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.Toast;
//import android.widget.AdapterView.OnItemClickListener;

public class ListViewClickDemo extends Activity {
    private static final String[] sports = new String[] {
    	"绵备", "具备", "腊惑", "丑备", "葫惑"
    };
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ListView list = (ListView)findViewById(R.id.list1);
        list.setAdapter(new ArrayAdapter<String>(this,
        		android.R.layout.simple_list_item_1, sports));
        list.setStackFromBottom(true);
 
//        OnItemClickListener mMessageClickedHandler = new OnItemClickListener() {    
//        	public void onItemClick(AdapterView parent, View v, int position, long id)    {
        	// Display a messagebox.        
//        		Toast.makeText(this,"You've got an event",Toast.LENGTH_SHORT).show();
//        	}};
        // Now hook into our object and set its onItemClickListener member
        // to our class handler object.
//        list.setOnItemClickListener(mMessageClickedHandler);
    }    
}