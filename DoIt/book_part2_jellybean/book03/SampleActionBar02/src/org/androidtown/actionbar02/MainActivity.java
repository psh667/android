package org.androidtown.actionbar02;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 액션바의 메뉴에 뷰를 설정하는 방법을 알 수 있습니다.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {

	TextView text01;
	EditText edit01;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        // 선택된 메뉴를 표시할 텍스트뷰
        text01 = (TextView) findViewById(R.id.text01);
        
    }

    /**
     * 메뉴가 만들어질 수 있도록 자동으로 호출됩니다.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// XML에 정의한 메뉴들을 인플레이션하여 로딩합니다.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        
        View v = menu.findItem(R.id.menu_search).getActionView();
        edit01 = (EditText) v.findViewById(R.id.edit01);
 
        if (edit01 != null) {
        	edit01.setOnEditorActionListener(onSearchListener);
        }
        
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
    
    /**
     * 키 입력이 끝났을 때 검색합니다.
     */
    private TextView.OnEditorActionListener onSearchListener = new TextView.OnEditorActionListener() {
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (event == null || event.getAction() == KeyEvent.ACTION_UP) {
                // 검색 메소드 호출
            	search();
 
            	// 키패드 닫기
                InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
 
            return (true);
        }
    };
    
    /**
     * 검색 메소드 : 여기에서는 단순히 메시지로 검색어만 보여줍니다.
     */
    private void search() {
    	String searchString = edit01.getEditableText().toString();
    	Toast.makeText(this, "검색어 : " + searchString, Toast.LENGTH_SHORT).show();
    }
    
}
