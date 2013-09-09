package andexam.ver4_1.c16_dialog;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class Question1 extends Activity {
	int a = 3;
	int b = 4;
	int result;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogtest);
	}
	
	public void mOnClick(View v) {
		new AlertDialog.Builder(this)
		.setTitle("질문")
		.setMessage("어떤 연산을 하시겠습니까?")
		.setPositiveButton("덧셈", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				result = a + b;
			}
		})
		.setNegativeButton("곱셈", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				result = a * b;
			}
		})
		.show();

		TextView text = (TextView)findViewById(R.id.text);
		text.setText("연산 결과 = " + result);
		Toast.makeText(this, "연산을 완료하였습니다.", 
				Toast.LENGTH_LONG).show();
	}
}

