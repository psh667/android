package andexam.ver4_1.c21_actionbar;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class DisplayOption extends Activity {
	CheckBox mChkUseLogo;
	CheckBox mChkShowHome;
	CheckBox mChkHomeAsUp;
	CheckBox mChkShowTitle;
	CheckBox mChkShowCustom;
	ActionBar mActionBar;
	Button mCustom;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.displayoption);

		mChkUseLogo = (CheckBox)findViewById(R.id.chkuselogo);
		mChkUseLogo.setOnCheckedChangeListener(mListener);
		mChkShowHome = (CheckBox)findViewById(R.id.chkshowhome);
		mChkShowHome.setOnCheckedChangeListener(mListener);
		mChkHomeAsUp = (CheckBox)findViewById(R.id.chkhomeasup);
		mChkHomeAsUp.setOnCheckedChangeListener(mListener);
		mChkShowTitle = (CheckBox)findViewById(R.id.chkshowtitle);
		mChkShowTitle.setOnCheckedChangeListener(mListener);
		mChkShowCustom = (CheckBox)findViewById(R.id.chkshowcustom);
		mChkShowCustom.setOnCheckedChangeListener(mListener);

		mActionBar = getActionBar();
		mCustom = new Button(this);
		mCustom.setText("Custom");
		mActionBar.setCustomView(mCustom);

		mActionBar.setSubtitle("subtitle");

		int option = mActionBar.getDisplayOptions();
		mChkUseLogo.setChecked((option & ActionBar.DISPLAY_USE_LOGO) != 0);
		mChkShowHome.setChecked((option & ActionBar.DISPLAY_SHOW_HOME) != 0);
		mChkHomeAsUp.setChecked((option & ActionBar.DISPLAY_HOME_AS_UP) != 0);
		mChkShowTitle.setChecked((option & ActionBar.DISPLAY_SHOW_TITLE) != 0);
		mChkShowCustom.setChecked((option & ActionBar.DISPLAY_SHOW_CUSTOM) != 0);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbarmenu, menu);

		return true;
	}

	CheckBox.OnCheckedChangeListener mListener = 
			new CheckBox.OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			switch (buttonView.getId()) {
			case R.id.chkuselogo:
				//mActionBar.setDisplayOptions(isChecked ? 
				//	ActionBar.DISPLAY_USE_LOGO:0, 
				//	ActionBar.DISPLAY_USE_LOGO);
				mActionBar.setDisplayUseLogoEnabled(isChecked);
				break;
			case R.id.chkshowhome:
				mActionBar.setDisplayShowHomeEnabled(isChecked);
				break;
			case R.id.chkhomeasup:
				mActionBar.setDisplayHomeAsUpEnabled(isChecked);
				break;
			case R.id.chkshowtitle:
				mActionBar.setDisplayShowTitleEnabled(isChecked);
				break;
			case R.id.chkshowcustom:
				mActionBar.setDisplayShowCustomEnabled(isChecked);
				break;
			}

		}
	};
}
