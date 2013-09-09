package com.appstudio.android.sample.ch_8;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ToastNotificationActivity extends Activity {
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toastnotificationmain);
		
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new ButtonOnClickHandler());

		Button custom_button = (Button) findViewById(R.id.custombutton);
		custom_button.setOnClickListener(new CustomButtonOnClickHandler());

	}
	
	class ButtonOnClickHandler implements OnClickListener{
		@Override
		public void onClick(View v) {
			Context context = getApplicationContext();
			CharSequence text = "Hello toast!";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
			toast.show();
		}
		
	}

	class CustomButtonOnClickHandler implements OnClickListener{
		@Override
		public void onClick(View v) {
			LayoutInflater inflater = getLayoutInflater();
			View layout = inflater.inflate(R.layout.toastlayout,
			                               (ViewGroup) findViewById(R.id.toast_layout_root));
			
			ImageView image = (ImageView) layout.findViewById(R.id.image);
			image.setImageResource(R.drawable.android_button);
			TextView text = (TextView) layout.findViewById(R.id.text);
			text.setText("안녕하세요. 커스텀 사용자 토스트 메시지 입니다.!");

			Toast toast = new Toast(getApplicationContext());
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(layout);
			toast.show();
		}
		
	}

}
