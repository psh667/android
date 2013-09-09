package andexam.ver4_1.c16_dialog;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;

public class TouchOutside extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogtest);
	}
	
	public void mOnClick(View v) {
		AlertDialog dialog = new AlertDialog.Builder(this)
		.setTitle("알림")
		.setMessage("대화상자 바깥을 누르면 닫힙니다.")
		.setIcon(R.drawable.androboy)
		.setNegativeButton("닫기", null)
		.create();
		
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}
}