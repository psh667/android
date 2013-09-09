package andexam.ver4_1.c16_dialog;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;

public class OkCancel extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogtest);
	}
	
	public void mOnClick(View v) {
		new AlertDialog.Builder(this)
		.setTitle("질문")
		.setMessage("김상형군에게 100만원을 기부하시겠습니까?")
		.setIcon(R.drawable.androboy)
		.setPositiveButton("확인", null)
		.setNegativeButton("취소", null)
		.show();
	}
}