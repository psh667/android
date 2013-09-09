package andexam.ver4_1.c13_advwidget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SwitchTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.switchtest);

		Switch sw = (Switch)findViewById(R.id.switch1);
		sw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Toast.makeText(SwitchTest.this, "체크 상태 = " + isChecked, 0).show();
			}
		});
	}
}