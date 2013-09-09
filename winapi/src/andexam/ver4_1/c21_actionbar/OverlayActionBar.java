package andexam.ver4_1.c21_actionbar;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class OverlayActionBar extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overlayactionbar);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbarmenu, menu);

		return true;
	}
	
	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btntoggle:
			ActionBar ab = getActionBar(); 
			if (ab.isShowing()) {
				ab.hide();
				((Button)v).setText("Show Action Bar");
			} else {
				ab.show();
				((Button)v).setText("Hide Action Bar");
			}
			break;
		}
	}
}