package org.androidtown.ui.tab;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;

/**
 * 예전 방식의 탭을 만드는 방법에 대해 알 수 있습니다.
 * 액티비티는 TabActivity를 상속해야 합니다.
 * 
 * @author Mike
 */
public class MainActivity extends TabActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 탭 위젯을 위해 만든 메소드 호출
        setupTabs();
    }

    /**
     * 탭 위젯을 처리하는 메소드
     */
    private void setupTabs() {
    	TabHost tabs = getTabHost();
 	    
 	    // TAB 01 
 	    TabHost.TabSpec spec = null;
 	    Intent intent = null;
        
 	    spec = tabs.newTabSpec("tab01");
 	    intent = new Intent(this, SubPage01Activity.class);
 	    intent.putExtra("mode", "new");
	   	intent.putExtra("initialize", true);
	   	intent.putExtra("request", true);
	   	intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
   	
 	    spec.setContent(intent);

 	    spec.setIndicator("SubPage01");
 	    tabs.addTab(spec);
 	    
 	    // TAB 02 
 	    spec = tabs.newTabSpec("tab02");
 	    intent = new Intent(this, SubPage02Activity.class);
 	    intent.putExtra("mode", "new");
	   	intent.putExtra("initialize", true);
	   	intent.putExtra("request", true);
	   	intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
 	    spec.setContent(intent);

 	    spec.setIndicator("SubPage02");
 	    tabs.addTab(spec);
 	    
 	    // TAB 03 
 	    spec = tabs.newTabSpec("tab03");
 	    intent = new Intent(this, SubPage03Activity.class);
 	    intent.putExtra("mode", "new");
	   	intent.putExtra("initialize", true);
	   	intent.putExtra("request", true);
	   	intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
 	    spec.setContent(intent);

 	    spec.setIndicator("SubPage03");
 	    tabs.addTab(spec);
 	    
 	    // set current tab
 	    tabs.setCurrentTab(0);
 	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
