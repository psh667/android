package andexam.ver4_1.c21_actionbar;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;
import andexam.ver4_1.*;

public class ActionSwitch extends Activity {
	TextView mText;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mText = new TextView(this);
		mText.setText("액션바에서 네트워크 옵션을 선택하세요.");
		setContentView(mText);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionswitchmenu, menu);
		
		MenuItem network = menu.findItem(R.id.network);
		Switch sw = (Switch)network.getActionView();
		sw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mText.setText("선택된 네트워크 = " + (isChecked ? "WiFi":"LTE"));
			}
		});

		return true;
	}
}