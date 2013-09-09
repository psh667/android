package org.androidtown.actionbar;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 액션바에 메뉴 버튼들을 보여주는 가장 기본적인 방법을 알 수 있습니다.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {

	TextView text01;
	ActionBar abar;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 액션바 객체를 참조할 때는 getActionBar() 메소드를 사용합니다.
        abar = getActionBar();
        
        // 보여주고 싶다면 show() 메소드를 호출합니다.
        //abar.show();
        // 감추고 싶다면 hide() 메소드를 호출합니다.
        //abar.hide();
        
        // 타이틀의 부제목을 설정합니다.
        abar.setSubtitle("옵션바 살펴보기");
        
        
        // 선택된 메뉴를 표시할 텍스트뷰
        text01 = (TextView) findViewById(R.id.text01);
     
        // 액션바의 아이콘을 바꿀 버튼
        Button button01 = (Button) findViewById(R.id.button01);
        button01.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		abar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME|ActionBar.DISPLAY_USE_LOGO|ActionBar.DISPLAY_HOME_AS_UP);
        	}
        });

        
    }

    /**
     * 메뉴가 만들어질 수 있도록 자동으로 호출됩니다.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// XML에 정의한 메뉴들을 인플레이션하여 로딩합니다.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        
        return true;
    }
    
    /**
     * 메뉴가 선택되었을 때 자동으로 호출됩니다.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
	        case R.id.menu_refresh:  // 새로고침 메뉴 선택
	            text01.setText("새로고침 메뉴를 선택했습니다.");
	            return true;
	 
	        case R.id.menu_search:  // 검색 메뉴 선택
	        	text01.setText("검색 메뉴를 선택했습니다.");
	            return true;
	 
	        case R.id.menu_settings:  // 설정 메뉴 선택
	        	text01.setText("설정 메뉴를 선택했습니다.");
	            return true;
        }
 
        return super.onOptionsItemSelected(item);
    }
    
}
