package andexam.ver4_1.c06_layoutman;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class SetParameter extends Activity {
	Button mLeft;
	Button mRight;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setparameter);

		mLeft = (Button)findViewById(R.id.btnleft); 
		mRight = (Button)findViewById(R.id.btnright); 

		mLeft.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setParam(3, 1);
			}
		});

		mRight.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setParam(1, 3);
			}
		});
	}
	
	void setParam(int left, int right) {
		LinearLayout.LayoutParams lparam = 
			(LinearLayout.LayoutParams)mLeft.getLayoutParams();
		lparam.weight = left;
		mLeft.setLayoutParams(lparam);

		LinearLayout.LayoutParams rparam = 
			(LinearLayout.LayoutParams)mRight.getLayoutParams();
		rparam.weight = right;
		mRight.setLayoutParams(rparam);
	}
}