package andexam.ver4_1.c11_widget;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.*;

public class ShowHideKey extends Activity {
	InputMethodManager mImm;
	EditText mEdit;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showhidekey);

		mImm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
		mEdit = (EditText)findViewById(R.id.edit);
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.show:
			mImm.showSoftInput(mEdit, 0);
			break;
		case R.id.hide:
			mImm.hideSoftInputFromWindow(mEdit.getWindowToken(), 0);
			break;
		}
	}
}