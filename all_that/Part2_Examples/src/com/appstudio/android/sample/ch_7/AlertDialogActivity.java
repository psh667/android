package com.appstudio.android.sample.ch_7;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AlertDialogActivity extends Activity {
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alertdialogmain);
		
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new ButtonOnClickHandler());
	}
	
	class ButtonOnClickHandler implements OnClickListener{
		AlertDialog.Builder builder = new AlertDialog.Builder(AlertDialogActivity.this);
		@Override
		public void onClick(View v) {
			builder.setMessage("정말로 다이얼로그를 종료하시겠습니까?")
			       .setCancelable(false)
			       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   AlertDialogActivity.this.finish();
			           }
			       })
			       .setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		}
		
	}
}
