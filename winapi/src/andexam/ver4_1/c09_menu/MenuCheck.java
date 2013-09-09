package andexam.ver4_1.c09_menu;

import andexam.ver4_1.*;
import android.app.*;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class MenuCheck extends Activity {
	Button mBtn;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menucheck);
		mBtn = (Button)findViewById(R.id.button);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menucheck,menu);
		
		return true;
	}
	
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (mBtn.getTextSize() == 40) {
			menu.findItem(R.id.bigfont).setChecked(true);
		} else {
			menu.findItem(R.id.bigfont).setChecked(false);
		}
		
		int color = mBtn.getTextColors().getDefaultColor();
		
		if (color == Color.RED) {
			menu.findItem(R.id.red).setChecked(true);
		}
		if (color == Color.GREEN) {
			menu.findItem(R.id.green).setChecked(true);
		}
		if (color == Color.BLUE) {
			menu.findItem(R.id.blue).setChecked(true);
		}
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.bigfont:
			if (item.isChecked()) {
				mBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, 20);
			} else {
				mBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
			}
			return true;
		case R.id.red:
			mBtn.setTextColor(Color.RED);
			return true;
		case R.id.green:
			mBtn.setTextColor(Color.GREEN);
			return true;
		case R.id.blue:
			mBtn.setTextColor(Color.BLUE);
			return true;
		}
		return false;
	}
}
