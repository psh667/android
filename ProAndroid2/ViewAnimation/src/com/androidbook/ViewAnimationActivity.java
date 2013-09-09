package com.androidbook;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ViewAnimationActivity extends Activity {

	  @Override 
	  public void onCreate(Bundle savedInstanceState) 
	  {
	    super.onCreate(savedInstanceState); 
	    setContentView(R.layout.list_layout); 
	    setupListView(); 
	    this.setupButton(); 
	  }
	  private void setupListView()
	  {
	    String[] listItems = new String[] {
	      "亲格 1", "亲格 2", "亲格 3", 
	      "亲格 4", "亲格 5", "亲格 6", 
	    };

	    ArrayAdapter<Object> listItemAdapter = 
	      new ArrayAdapter<Object>(this 
	          ,android.R.layout.simple_list_item_1 
	          ,listItems);
	    ListView lv = (ListView)this.findViewById(R.id.list_view_id); 
	    lv.setAdapter(listItemAdapter); 
	  }
	  private void setupButton()
	  {
	    Button b = (Button)this.findViewById(R.id.btn_animate); 
	    b.setOnClickListener( 
	      new Button.OnClickListener() {
	        public void onClick(View v) 
	        {
	          animateListView();
	        }
	      }); 
	  }
	  private void animateListView() 
	  {
	    ListView lv = (ListView)this.findViewById(R.id.list_view_id); 
	    lv.startAnimation(new ViewAnimation()); 
	  }
	}
