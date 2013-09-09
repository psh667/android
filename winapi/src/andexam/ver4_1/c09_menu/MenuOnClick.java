package andexam.ver4_1.c09_menu;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class MenuOnClick extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView MyText = new TextView(this);
		MyText.setText("메뉴 키를 누르세요.");
		setContentView(MyText);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.onclickmenu, menu);

		return true;
	}

	public void mOnClick(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.about:
			Toast.makeText(this,"onClick 속성으로 메뉴 이벤트를 처리합니다.",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.help:
			Toast.makeText(this,"도움말입니다.",Toast.LENGTH_SHORT).show();
			break;
		}
	}
}
