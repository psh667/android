package kr.co.company.ActivityForResult;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SubActivity extends Activity {
	EditText edit;
	public class MyAppWidgetProvider extends AppWidgetProvider {

	    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
				// 여기에서 위젯의 뷰들을 업데이트한다. 
	        }
	    }

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sub);

		edit = (EditText) findViewById(R.id.edit);
		Button button_ok = (Button) findViewById(R.id.button_ok);
		button_ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("INPUT_TEXT", edit.getText().toString());
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		Button button_cancel = (Button) findViewById(R.id.button_cancel);
		button_cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}
}
