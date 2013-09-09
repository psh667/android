package andexam.ver4_1.c16_dialog;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class SelectDialog2 extends Activity {
	int mSelect = 0;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogtest);
	}
	
	public void mOnClick(View v) {
		new AlertDialog.Builder(this)
		.setTitle("음식을 선택하시오.")
		.setIcon(R.drawable.androboy)
		.setSingleChoiceItems(R.array.foods, mSelect, 
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				mSelect = which;
			}
		})
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String[] foods = getResources().getStringArray(R.array.foods);
				TextView text = (TextView)findViewById(R.id.text);
				text.setText("선택한 음식 = " + foods[mSelect]);
			}
		})
		.setNegativeButton("취소", null)
		.show();
	}
}
