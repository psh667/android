package andexam.ver4_1.c21_actionbar;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class NoActionBar extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView text = new TextView(this);
		text.setText("액션바를 숨깁니다.");
		setContentView(text);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbarmenu, menu);

		return true;
	}
}
