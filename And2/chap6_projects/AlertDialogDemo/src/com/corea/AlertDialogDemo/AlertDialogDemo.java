package com.corea.AlertDialogDemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AlertDialogDemo extends Activity implements View.OnClickListener {
	Button alert;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		alert=(Button)findViewById(R.id.alert);
		alert.setOnClickListener(this);
    }
    
	public void onClick(View view) {
			new AlertDialog.Builder(this)
				.setTitle("경고")
				.setMessage("시간 초과!")
				.setPositiveButton("계속", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    	dialog.dismiss();
                    }
                })
               .setNegativeButton("종료", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {                
                	   dialog.cancel();           
                   }       
                })
				.show();
	}
}



