package andexam.ver4_1.c16_dialog;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;

public class Cancelable extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogtest);
	}
	
	public void mOnClick(View v) {
		new AlertDialog.Builder(this)
		.setTitle("공지 사항")
		.setMessage("이 메시지는 반드시 읽어야 합니다.")
		.setIcon(R.drawable.androboy)
		.setCancelable(false)
		.setNegativeButton("닫기", null)
		.show();
	}
}