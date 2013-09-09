package andexam.ver4_1.c06_layoutman;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class MultiPage extends Activity {
	View mPage1, mPage2, mPage3;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multipage);

		mPage1 = findViewById(R.id.page1);
		mPage2 = findViewById(R.id.page2);
		mPage3 = findViewById(R.id.page3);

		findViewById(R.id.btnpage1).setOnClickListener(mClickListener);
		findViewById(R.id.btnpage2).setOnClickListener(mClickListener);
		findViewById(R.id.btnpage3).setOnClickListener(mClickListener);
	}

	Button.OnClickListener mClickListener = new Button.OnClickListener() {
		public void onClick(View v) {
			mPage1.setVisibility(View.INVISIBLE);
			mPage2.setVisibility(View.INVISIBLE);
			mPage3.setVisibility(View.INVISIBLE);
			
			switch (v.getId()) {
			case R.id.btnpage1:
				mPage1.setVisibility(View.VISIBLE);
				break;
			case R.id.btnpage2:
				mPage2.setVisibility(View.VISIBLE);
				break;
			case R.id.btnpage3:
				mPage3.setVisibility(View.VISIBLE);
				break;
			}
		}
	};
}