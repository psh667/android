package com.appstudio.android.sample.ch_5;
	
import com.appstudio.android.sample.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
	
	public class LayoutInflationActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    RelativeLayout relativeLayout = (RelativeLayout)inflater.inflate(R.layout.relativelayoutmain, null);
	    
	    LinearLayout newLayout = new LinearLayout(this);
	    
	    newLayout.setOrientation(LinearLayout.VERTICAL);
	    newLayout.setBackgroundColor(Color.DKGRAY);
	    
	    Button button = new Button(this);
	    button.setHeight(20);
	    button.setWidth(200);
	    button.setText("This is Button");

	    button.setOnClickListener(new OnClickListener() {
	    	public void onClick(View v) {
	            Toast.makeText(LayoutInflationActivity.this, ((Button) v).getText() +  " is Clicked", Toast.LENGTH_SHORT).show();
		    }
	    });
	    

	    newLayout.addView(relativeLayout);
	    newLayout.addView(button);

	    
	    this.setContentView(newLayout);
	    
	}
}
