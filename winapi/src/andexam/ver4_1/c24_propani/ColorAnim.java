package andexam.ver4_1.c24_propani;

import android.animation.*;
import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class ColorAnim extends Activity {
	Button mBtn;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coloranim);
		
		mBtn=(Button)findViewById(R.id.btn);
	}
	
	public void mOnClick(View v) {
		ObjectAnimator anim = ObjectAnimator.ofInt(mBtn, "backgroundColor", 
				Color.YELLOW, Color.RED);
		anim.setDuration(2000);
		if (v.getId() == R.id.btn2) {
			anim.setEvaluator(new ArgbEvaluator());
		}
		anim.start();
	}	
}