package andexam.ver4_1.c16_dialog;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class SelectDialog3 extends Activity {
	boolean[] mSelect = { false, false, false, false };
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogtest);
	}
	
	public void mOnClick(View v) {
		new AlertDialog.Builder(this)
		.setTitle("음식을 선택하시오.")
		.setIcon(R.drawable.androboy)
		.setMultiChoiceItems(R.array.foods, mSelect, 
				new DialogInterface.OnMultiChoiceClickListener() {
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				mSelect[which] = isChecked;
			}
		})
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String[] foods = getResources().getStringArray(R.array.foods);
				TextView text = (TextView)findViewById(R.id.text);
				String result = "선택한 음식 = ";
				for (int i = 0; i < mSelect.length; i++) {
					if (mSelect[i]) {
						result += foods[i] + " ";
					}
				}
				text.setText(result);
			}
		})
		.setNegativeButton("취소", null)
		.show();
	}
}