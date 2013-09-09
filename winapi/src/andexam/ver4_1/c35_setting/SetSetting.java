package andexam.ver4_1.c35_setting;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.provider.*;
import android.view.*;
import android.widget.*;

public class SetSetting extends Activity {
	ContentResolver mCr;
	TextView mTxtTimeout;
	SeekBar mSeekBar;
	ToggleButton mTogBtn;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setsetting);

		mCr = getContentResolver();

		mTxtTimeout = (TextView)findViewById(R.id.txttimeout);
		mSeekBar = (SeekBar)findViewById(R.id.screentimeout);
		int timeout = Settings.System.getInt(mCr, 
				Settings.System.SCREEN_OFF_TIMEOUT, -1);
		mSeekBar.setProgress(timeout == -1 ? 0:timeout / 10000);
		PrintTimeout(timeout);
		mSeekBar.setOnSeekBarChangeListener(mSeekListener);

		mTogBtn = (ToggleButton)findViewById(R.id.autorotate);
		int autorotate = Settings.System.getInt(mCr, 
				Settings.System.ACCELEROMETER_ROTATION, -1);
		mTogBtn.setChecked(autorotate == 1);
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.autorotate:
			if (mTogBtn.isChecked()) {
				Settings.System.putInt(mCr, 
						Settings.System.ACCELEROMETER_ROTATION, 1);
			} else {
				Settings.System.putInt(mCr, 
						Settings.System.ACCELEROMETER_ROTATION, 0);
			}
			break;
		}
	}
	
	SeekBar.OnSeekBarChangeListener mSeekListener = 
		new SeekBar.OnSeekBarChangeListener() {
		public void onProgressChanged(SeekBar seekBar, int progress, 
				boolean fromUser) {
			int timeout = progress == 0 ? -1:progress * 10000;
			Settings.System.putInt(mCr, 
					Settings.System.SCREEN_OFF_TIMEOUT, timeout);
			PrintTimeout(timeout);
		}

		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		public void onStopTrackingTouch(SeekBar seekBar) {
		}
	};
	
	void PrintTimeout(int timeout) {
		if (timeout == -1) {
			mTxtTimeout.setText("조명 시간 : (끄지 않음)");
		} else {
			mTxtTimeout.setText("조명 시간 : " + (timeout / 1000) + "초");
		}
	}
}
