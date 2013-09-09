package kr.co.company.customdialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomDialogActivity extends Activity {
	static final int DIALOG_CUSTOM_ID = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button b = (Button) findViewById(R.id.button1);
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(DIALOG_CUSTOM_ID);
			}
		});
	}

	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case DIALOG_CUSTOM_ID:
			dialog = new Dialog(this);
			dialog.setContentView(R.layout.custom_dialog);
			dialog.setTitle("Custom Dialog");
			TextView text = (TextView) dialog.findViewById(R.id.text);
			text.setText("Hello, this is a custom dialog!");
			ImageView image = (ImageView) dialog.findViewById(R.id.image);
			image.setImageResource(R.drawable.android);
			break;
		}
		return dialog;
	}
}
