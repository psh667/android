package com.appstudio.android.sample;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Part02ListActivity extends ListActivity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] countries = getResources().getStringArray(R.array.activities_array);
        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, countries));
    }
    
    public void onListItemClick(ListView list, View view, int position, long id){
    	Toast.makeText(this, 
    			((String)((TextView)view).getText()).split("-")[0] + "보기", 
    			Toast.LENGTH_SHORT).show();
		try {
			this.startActivity(new Intent(this, Class.forName(this.getPackageName() +"."+((String)((TextView)view).getText()).split("-")[1])));
		} catch (Exception e) {
			Log.d("onListItemClick",e.getMessage());
		} 
    }
}