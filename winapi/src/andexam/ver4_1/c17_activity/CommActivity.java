package andexam.ver4_1.c17_activity;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class CommActivity extends Activity {
	TextView mText;
	final static int ACT_EDIT = 0;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commactivity);

		mText = (TextView)findViewById(R.id.text);
	}
	
	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btnedit:
			Intent intent = new Intent(this, ActEdit.class);
			intent.putExtra("TextIn", mText.getText().toString());
			startActivityForResult(intent,ACT_EDIT);
			break;
		}
	}
	
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ACT_EDIT:
			if (resultCode == RESULT_OK) {
				mText.setText(data.getStringExtra("TextOut"));
			}
			break;
		}
	}
}
