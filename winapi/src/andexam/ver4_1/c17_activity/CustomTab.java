package andexam.ver4_1.c17_activity;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class CustomTab extends Activity {
	View mPage1, mPage2, mPage3;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customtab);

		mPage1 = findViewById(R.id.opt_general);
		mPage2 = findViewById(R.id.opt_compiler);
		mPage3 = findViewById(R.id.opt_linker);
		
		((Button)findViewById(R.id.btn1)).setOnClickListener(mClickListener);
		((Button)findViewById(R.id.btn2)).setOnClickListener(mClickListener);
		((Button)findViewById(R.id.btn3)).setOnClickListener(mClickListener);
	}

	Button.OnClickListener mClickListener = new Button.OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn1:
				ChangePage(1);
				break;
			case R.id.btn2:
				ChangePage(2);
				break;
			case R.id.btn3:
				ChangePage(3);
				break;
			}
		}
	};

	void ChangePage(int page) {
		mPage1.setVisibility(View.INVISIBLE);
		mPage2.setVisibility(View.INVISIBLE);
		mPage3.setVisibility(View.INVISIBLE);

		switch (page) {
		case 1:
			mPage1.setVisibility(View.VISIBLE);
			break;
		case 2:
			mPage2.setVisibility(View.VISIBLE);
			break;
		case 3:
			mPage3.setVisibility(View.VISIBLE);
			break;
		}
	}
}