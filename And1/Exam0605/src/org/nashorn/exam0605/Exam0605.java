package org.nashorn.exam0605;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

public class Exam0605 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(this));
	    
	    gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	    	@Override
	    	public void onItemClick(AdapterView<?> parent, View v, int pos, long id) 
	    	{ 
	    		Toast.makeText(getBaseContext(), String.valueOf(pos), Toast.LENGTH_SHORT).show();
	    	} 
    	});
    }
    
    public class ImageAdapter extends BaseAdapter {
	    private Context mContext;
	
	    public ImageAdapter(Context c) {
	        mContext = c;
	    }
	
	    public int getCount() {
	    	return 27;
	    }
	
	    public Object getItem(int position) {
	        return null;
	    }
	
	    public long getItemId(int position) {
	        return 0;
	    }
	
	    // create a new ImageView for each item referenced by the Adapter
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	
	    	View v;
	    	if (convertView == null)
	    	{
	    		LayoutInflater li = getLayoutInflater();
	    		v = li.inflate(R.layout.icon, null);
	    	}
	    	else
	    	{
	    		v = convertView;
	    	}
	    	return v;

	    }
	
	}
}