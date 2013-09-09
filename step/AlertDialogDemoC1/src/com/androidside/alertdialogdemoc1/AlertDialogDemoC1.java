package com.androidside.alertdialogdemoc1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AlertDialogDemoC1 extends Activity {
	final static int MY_DIALOG = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button alert = (Button) findViewById(R.id.alert);
        alert.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    Log.d("tag", "showDialog " + MY_DIALOG);
			    
				showDialog(MY_DIALOG);
				
			}
		});
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
    	Log.d("tag", "onCreateDialog " + id);
    	
    	return new AlertDialog.Builder(this)
        .setTitle("∞Ê∞Ì√¢")
        .setMessage("æ»≥Á«œººø‰")
        .setNeutralButton("¥›±‚", null)
        .create();    	
    	
    }
    
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
    	// TODO Auto-generated method stub
    	super.onPrepareDialog(id, dialog);
    	
    	Log.d("tag", "onPrepareDialog " + id);
    }
}