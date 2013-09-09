package andexam.ver4_1.c17_activity;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.drawable.*;
import android.os.*;
import android.view.*;
import android.widget.*;

//* 팩토리를 쓰는 방법.
@SuppressWarnings("deprecation")
public class TabTest2 extends TabActivity {
	TabFactory factory;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		factory = new TabFactory(this);
		TabHost tabHost = getTabHost();
		
		Drawable icon = getResources().getDrawable(R.drawable.androboy);
		tabHost.addTab(tabHost.newTabSpec("General")
				.setIndicator("일반", icon)
				.setContent(factory));
		tabHost.addTab(tabHost.newTabSpec("Compiler")
				.setIndicator("컴파일러", icon)
				.setContent(factory));
		tabHost.addTab(tabHost.newTabSpec("Linker")
				.setIndicator("링커", icon)
				.setContent(factory));
	}
}

class TabFactory implements TabHost.TabContentFactory {
	Context mCon;
	TabFactory(Context context) {
		mCon = context;
	}
	// 탭의 고유한 태그가 전달된다.
	public View createTabContent(String tag) {
		TextView text = new TextView(mCon);
		text.setText("Tab View of " + tag);
		return text;
	}
}
//*/

/* 액티비티가 팩토리를 직접 구현
public class TabTest2 extends TabActivity implements TabHost.TabContentFactory {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TabHost tabHost = getTabHost();
		
		Drawable icon = getResources().getDrawable(R.drawable.androboy);
		tabHost.addTab(tabHost.newTabSpec("General")
				.setIndicator("일반", icon)
				.setContent(this));
		tabHost.addTab(tabHost.newTabSpec("Compiler")
				.setIndicator("컴파일러", icon)
				.setContent(this));
		tabHost.addTab(tabHost.newTabSpec("Linker")
				.setIndicator("링커", icon)
				.setContent(this));
	}

	public View createTabContent(String tag) {
		TextView text = new TextView(this);
		text.setText("Tab View of " + tag);
		return text;
	}
}
//*/
