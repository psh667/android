package kr.co.company.alertdialog2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private static final int DIALOG_YES_NO_MESSAGE = 1;

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_YES_NO_MESSAGE:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("종료 확인 대화 상자")
					// 제목
					.setMessage("애플리케이션을 종료하시겠습니까?")
					// 메시지
					.setCancelable(false)
					.setPositiveButton("Yes", // “Yes“ 버튼
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									MainActivity.this.finish();
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									dialog.cancel();
								}
							});
			AlertDialog alert = builder.create();
			return alert;
		}
		return null;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button b = (Button) findViewById(R.id.Button01);
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(DIALOG_YES_NO_MESSAGE);
			}
		});
	}
}