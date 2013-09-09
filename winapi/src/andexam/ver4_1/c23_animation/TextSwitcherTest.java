package andexam.ver4_1.c23_animation;

import andexam.ver4_1.*;
import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.view.animation.*;
import android.widget.*;

public class TextSwitcherTest extends Activity { 
	TextSwitcher mSwitcher;
	int mAdIdx = 0;
	String[] arAd = new String[] {
		"1.정력짱! 롯데 삼계탕", 
		"2.MS 명품 마우스", 
		"3.애플 아이 사과 쥬스", 
		"4.신용불량자 긴급대출",	
	};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.textswitchertest);
	
		mSwitcher = (TextSwitcher)findViewById(R.id.switcher);
		mSwitcher.setFactory(mFactory);
		
		mSwitcher.setText(arAd[mAdIdx]);
	}
	
	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btnnext:
			mAdIdx = mAdIdx == arAd.length - 1 ? 0:mAdIdx + 1;
			mSwitcher.setText(arAd[mAdIdx]);
			break;
		}
	}
	
	ViewSwitcher.ViewFactory mFactory = new ViewSwitcher.ViewFactory() {
		public View makeView() {
			TextView text = new TextView(TextSwitcherTest.this);
			text.setTextSize(22);
			text.setBackgroundColor(Color.YELLOW);
			text.setTextColor(Color.BLACK);
			return text;
		}
	};
}
