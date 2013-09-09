package andexam.ver4_1.c35_setting;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.provider.*;
import android.view.*;
import android.widget.*;

public class GetSetting extends Activity {
	TextView mResult;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getsetting);
		
		mResult = (TextView)findViewById(R.id.result);
		RefreshSetting();
	}
	
	void RefreshSetting() {
		ContentResolver cr = getContentResolver();
		String result = "";
		result = String.format("화면 타임아웃 = %d\n화면 밝기 = %d\n자동 회전 = %d\n" + 
				"비행 모드 = %d\n햅틱 = %d\n사운드 효과 = %d\n", 
			Settings.System.getInt(cr, Settings.System.SCREEN_OFF_TIMEOUT, -1),
			Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS, -1),
			Settings.System.getInt(cr, Settings.System.ACCELEROMETER_ROTATION, -1),
			Settings.System.getInt(cr, Settings.System.AIRPLANE_MODE_ON, -1),
			Settings.System.getInt(cr, Settings.System.HAPTIC_FEEDBACK_ENABLED, -1),
			Settings.System.getInt(cr, Settings.System.SOUND_EFFECTS_ENABLED, -1)
		);
		mResult.setText(result);
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btnrefresh:
			RefreshSetting();
			break;
		}
	}
}
