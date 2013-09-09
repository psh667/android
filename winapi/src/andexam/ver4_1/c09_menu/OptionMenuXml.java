package andexam.ver4_1.c09_menu;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class OptionMenuXml extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView MyText = new TextView(this);
		MyText.setText("메뉴 키를 누르세요.");
		setContentView(MyText);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.optionmenu, menu);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.jjajang:
			Toast.makeText(this,"짜장은 달콤해",Toast.LENGTH_SHORT).show();
			return true;
		case R.id.jjambbong:
			Toast.makeText(this,"짬뽕은 매워",Toast.LENGTH_SHORT).show();
			return true;
		case R.id.udong:
			Toast.makeText(this,"우동은 시원해",Toast.LENGTH_SHORT).show();
			return true;
		case R.id.mandoo:
			Toast.makeText(this,"만두는 공짜야",Toast.LENGTH_SHORT).show();
			return true;
		}
		return false;
	}
}
