package kr.co.company.alertdialog5;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AlertDialog5Activity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Button b = (Button) findViewById(R.id.Button01);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ProgressDialog progressDialog;
				progressDialog = new ProgressDialog(AlertDialog5Activity.this);
				progressDialog
						.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				progressDialog.setMessage("Loading...");
				progressDialog.show();
				progressDialog.setProgress(30);
			}
		});
	}

}