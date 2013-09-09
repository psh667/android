package andexam.ver4_1.c30_service;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class ChildrenWallSetting extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.childrenwallsetting);
		
		CheckBox GiveFruit = (CheckBox)findViewById(R.id.givefruit); 
		GiveFruit.setOnCheckedChangeListener(mCheckChange);
		SharedPreferences pref = getSharedPreferences("ChildrenWall", 0);
		if (pref.getBoolean("givefruit", true)) {
			GiveFruit.setChecked(true);
		}
		
		RadioGroup ColGroup = (RadioGroup)findViewById(R.id.ColorGroup);
		ColGroup.setOnCheckedChangeListener(mRadioCheck);

		int backcolor = pref.getInt("backcolor", 0);
		switch(backcolor) {
		case 0:
			((RadioButton)findViewById(R.id.Green)).setChecked(true);
			break;
		case 1:
			((RadioButton)findViewById(R.id.Blue)).setChecked(true);
			break;
		case 2:
			((RadioButton)findViewById(R.id.Yellow)).setChecked(true);
			break;
		case 3:
			((RadioButton)findViewById(R.id.Gray)).setChecked(true);
			break;
		}
		
		findViewById(R.id.btnclose).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});		
	}
	
	RadioGroup.OnCheckedChangeListener mRadioCheck = 
		new RadioGroup.OnCheckedChangeListener() {
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			SharedPreferences pref = getSharedPreferences("ChildrenWall", 0);
			SharedPreferences.Editor edit = pref.edit();
			if (group.getId() == R.id.ColorGroup) {
				switch (checkedId) {
				case R.id.Green:
					edit.putInt("backcolor", 0);
					break;
				case R.id.Blue:
					edit.putInt("backcolor", 1);
					break;
				case R.id.Yellow:
					edit.putInt("backcolor", 2);
					break;
				case R.id.Gray:
					edit.putInt("backcolor", 3);
					break;
				}
			}
			edit.commit();
		}
	};

	CompoundButton.OnCheckedChangeListener mCheckChange = 
		new CompoundButton.OnCheckedChangeListener() {
		public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {
			SharedPreferences pref = getSharedPreferences("ChildrenWall", 0);
			SharedPreferences.Editor edit = pref.edit();
			if (buttonView.getId() == R.id.givefruit) {
				if (isChecked) {
					edit.putBoolean("givefruit", true);
				} else {
					edit.putBoolean("givefruit", false);
				}
			}
			edit.commit();
		}
	};	
}
