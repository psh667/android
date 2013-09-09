package org.androidtown.ui.listview2;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	String[] items = { "mike", "angel", "crow", "john",
			"ginnie", "sally", "cohen", "rice" };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setListAdapter(new ArrayAdapter<String>(
	        	this,
	        	android.R.layout.simple_list_item_1,
	        	items));
		
	}

	protected void onListItemClick(ListView list, View v, int position, long id) {
	    super.onListItemClick(list, v, position, id);
		
	    String text = " position:" + position + " " + items[position];
	    Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
