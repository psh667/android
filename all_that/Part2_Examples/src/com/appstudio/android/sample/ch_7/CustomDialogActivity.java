package com.appstudio.android.sample.ch_7;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomDialogActivity extends Activity {
	AlertDialog.Builder builder;
	AlertDialog alertDialog;

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customdialogmain);
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new ButtonClickHandler());
	}
	
	public class ButtonClickHandler implements OnClickListener
	{
		@Override
		public void onClick(View v) {
			Context mContext = CustomDialogActivity.this;
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.customdialog,
			                               (ViewGroup) findViewById(R.id.layout_root));

			TextView text = (TextView) layout.findViewById(R.id.text);
			text.setText("Hello, this is a custom dialog!");
			ImageView image = (ImageView) layout.findViewById(R.id.image);
			image.setImageResource(R.drawable.android_button);

			builder = new AlertDialog.Builder(mContext);
			builder.setView(layout);
			alertDialog = builder.create();
			alertDialog.show();
		}
		
	}
}
