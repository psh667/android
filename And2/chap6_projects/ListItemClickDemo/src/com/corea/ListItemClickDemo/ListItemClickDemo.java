package com.corea.ListItemClickDemo;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListItemClickDemo extends ListActivity { 
    private static final String[] sports = new String[] {
    	"绵备", "具备", "腊惑", "丑备", "葫惑"
    };
    private ArrayAdapter<String> adapter;

    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        adapter = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_list_item_1, sports);
        setListAdapter(adapter);	
	}
	@Override
	protected void onListItemClick(ListView lv, View v, int position, long id) {
		// TODO Auto-generated method stub
		Toast.makeText(this, id+": "+sports[position], Toast.LENGTH_SHORT).show(); 
	}
}