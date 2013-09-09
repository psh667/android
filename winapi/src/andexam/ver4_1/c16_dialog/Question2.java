package andexam.ver4_1.c16_dialog;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

/* 리스너 안에 직접 코드 작성 - 중복이 심함
public class Question2 extends Activity {
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
				TextView text = (TextView)findViewById(R.id.text);
				text.setText("연산 결과 = " + result);
				Toast.makeText(Question2.this, "연산을 완료하였습니다.", 
						Toast.LENGTH_LONG).show();
			}
		})
		.setNegativeButton("곱셈", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				result = a * b;
				TextView text = (TextView)findViewById(R.id.text);
				text.setText("연산 결과 = " + result);
				Toast.makeText(Question2.this, "연산을 완료하였습니다.", 
						Toast.LENGTH_LONG).show();
			}
		})
		.show();
	}
}
//*/

//* 리스너를 통합 - 중복은 없으나 메서드를 따로 분리해야 함
public class Question2 extends Activity {
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
		.setPositiveButton("덧셈", mClick)
		.setNegativeButton("곱셈", mClick)
		.show();
	}
	
	DialogInterface.OnClickListener mClick = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			if (whichButton == DialogInterface.BUTTON_POSITIVE) {
				result = a + b;
			} else {
				result = a * b;
			}
			TextView text = (TextView)findViewById(R.id.text);
			text.setText("연산 결과 = " + result);
			Toast.makeText(Question2.this, "연산을 완료하였습니다.", 
					Toast.LENGTH_LONG).show();
		}
	};
}
//*/

