package com.andro;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class TabViewActivity extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);    
    	setContentView(R.layout.main);    
    	Resources res = getResources(); // Resource object to get Drawables    
    	TabHost tabHost = getTabHost();  // The activity TabHost    
    	TabHost.TabSpec spec;  // Resusable TabSpec for each tab    
    	Intent intent;  // Reusable Intent for each tab    
    	
    	// Create an Intent to launch an Activity for the tab (to be reused)    
    	intent = new Intent().setClass(this, CustomerListActivity.class);    
    	
    	// Initialize a TabSpec for each tab and add it to the TabHost    
    	spec = tabHost.newTabSpec("query").setIndicator("고객").setContent(intent);                      
    	tabHost.addTab(spec);    
    	
    	// Do the same for the other tabs    
    	intent = new Intent().setClass(this, CustomerRegActivity.class);    
    	spec = tabHost.newTabSpec("registration").setIndicator("등록").setContent(intent);                      
    	tabHost.addTab(spec);    
    	
    	// intent = new Intent().setClass(this, SongsActivity.class);    
    	intent = new Intent().setClass(this, HelpActivity.class);   
    	spec = tabHost.newTabSpec("help").setIndicator("도움말").setContent(intent);                      
    	
    	tabHost.addTab(spec); 
    	
    	tabHost.setCurrentTab(0);    
    }
}