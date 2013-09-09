package andexam.ver4_1.c11_widget;

import andexam.ver4_1.*;
import android.app.*;
import android.graphics.*;
import android.os.*;
import android.widget.*;

public class RadioCheck extends Activity {
	TextView mSample;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.radiocheck);

		mSample = (TextView)findViewById(R.id.txtsample);

		RadioGroup ColGroup = (RadioGroup)findViewById(R.id.colorgroup);
		ColGroup.setOnCheckedChangeListener(mRadioCheck);

		CheckBox chkWhite = (CheckBox)findViewById(R.id.chkwhiteback);
		chkWhite.setOnCheckedChangeListener(mCheckChange);
		
		ToggleButton tgLang = (ToggleButton)findViewById(R.id.tglanguage);
		tgLang.setOnCheckedChangeListener(mCheckChange);
	}

	RadioGroup.OnCheckedChangeListener mRadioCheck = 
		new RadioGroup.OnCheckedChangeListener() {
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (group.getId() == R.id.colorgroup) {
				switch (checkedId) {
				case R.id.rared:
					mSample.setTextColor(Color.RED);
					break;
				case R.id.ragreen:
					mSample.setTextColor(Color.GREEN);
					break;
				case R.id.rablue:
					mSample.setTextColor(Color.BLUE);
					break;
				}
			}
		}
	};

	CompoundButton.OnCheckedChangeListener mCheckChange = 
		new CompoundButton.OnCheckedChangeListener() {
		public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {
			if (buttonView.getId() == R.id.chkwhiteback) {
				if (isChecked) {
					mSample.setBackgroundColor(Color.WHITE);
				} else {
					mSample.setBackgroundColor(Color.TRANSPARENT);
				}
			}
			if (buttonView.getId() == R.id.tglanguage) {
				if (isChecked) {
					mSample.setText("샘플");
				} else {
					mSample.setText("Sample");
				}
			}
		}
	};
}
