package andexam.ver4_1.c09_menu;

import andexam.ver4_1.*;
import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class PopupMenuTest extends Activity {
	Button mBtn;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popupmenutest);

		mBtn = (Button)findViewById(R.id.btn);
	}

	public void mOnClick(View v) {
		PopupMenu popup = new PopupMenu(this, v);
		MenuInflater inflater = popup.getMenuInflater();
		Menu menu = popup.getMenu();
		inflater.inflate(R.menu.popupmenutestmenu, menu);
		//popup.inflate(R.menu.popupmenutestmenu);
		popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.popup_red:
					mBtn.setBackgroundColor(Color.RED);
					break;
				case R.id.popup_green:
					mBtn.setBackgroundColor(Color.GREEN);
					break;
				case R.id.popup_blue:
					mBtn.setBackgroundColor(Color.BLUE);
					break;
				case R.id.popup_black:
					mBtn.setTextColor(Color.BLACK);
					break;
				case R.id.popup_white:
					mBtn.setTextColor(Color.WHITE);
					break;
				case R.id.popup_gray:
					mBtn.setTextColor(Color.GRAY);
					break;
				}
				return false;
			}
		});
		popup.show();
	}	
}
