package andexam.ver4_1.c21_actionbar;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class AppIcon extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView text = new TextView(this);
		text.setText("타이틀 바의 로고 아이콘을 누르세요.");
		setContentView(text);
		getActionBar().setHomeButtonEnabled(true);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.appiconmenu, menu);

		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.one:
			Toast.makeText(this,"첫번째 액션 항목 선택",Toast.LENGTH_SHORT).show();
			return true;
		case android.R.id.home:
			Toast.makeText(this,"로고 아이콘 선택",Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this, AndExam.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		}
		return false;
	}
}