package andexam.ver4_1.c09_menu;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;

public class ChangeMenu extends Activity {
	boolean mBeginner = true;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changemenu);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		if (mBeginner) {
			inflater.inflate(R.menu.beginner, menu);
		} else {
			inflater.inflate(R.menu.professional, menu);
		}

		return true;
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btnbegineer:
			mBeginner = true;
			invalidateOptionsMenu();
			break;
		case R.id.btnprofessional:
			mBeginner = false;
			invalidateOptionsMenu();
			break;
		}
	}	
}