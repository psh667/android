package andexam.ver4_1.c09_menu;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;

public class MenuHistory extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menuhistory);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menuhistorymenu, menu);

		return true;
	}
}
