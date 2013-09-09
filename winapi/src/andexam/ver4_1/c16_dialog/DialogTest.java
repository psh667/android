package andexam.ver4_1.c16_dialog;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class DialogTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogtest);
	}

	public void mOnClick(View v) {
		Dialog dlg = new Dialog(this);
		TextView text = new TextView(this);
		text.setText("대화상자를 열었습니다.");
		dlg.setContentView(text);
		dlg.setTitle("알립니다.");
		dlg.show();
	}
}