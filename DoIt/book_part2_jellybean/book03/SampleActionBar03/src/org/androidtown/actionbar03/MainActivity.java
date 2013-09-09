package org.androidtown.actionbar03;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
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
 * 액션바에 탭을 설정하는 방법을 알 수 있습니다.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {

	EditText edit01;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 메인 레이아웃을 설정하는 setContentView() 메소드를 사용하지 않습니다.
        

        // 액션바를 직접 참조하여 모드를 변경합니다.
        ActionBar abar = getActionBar();
        abar.setNavigationMode( ActionBar.NAVIGATION_MODE_TABS );

		Tab tab01 = abar.newTab();
		tab01.setText("상품 #1");
		tab01.setTabListener(new ProductTabListener(this, Fragment01.class.getName()));
		abar.addTab(tab01);

		Tab tab02 = abar.newTab();
		tab02.setText("상품 #2");
		tab02.setTabListener(new ProductTabListener(this, Fragment02.class.getName()));
		abar.addTab(tab02);

		Tab tab03 = abar.newTab();
		tab03.setText("상품 #3");
		tab03.setTabListener(new ProductTabListener(this, Fragment03.class.getName()));
		abar.addTab(tab03);
		
    }

    /**
     * 탭을 선택했을 때 처리할 리스너 정의
     */
	private class ProductTabListener implements ActionBar.TabListener {
		private Fragment mFragment;
		private final Activity mActivity;
		private final String mFragName;

		public ProductTabListener(Activity activity, String fragName) {
			mActivity = activity;
			mFragName = fragName;
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction arg1) {
			
		}

		/**
		 * 탭이 선택되었을 때
		 */
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			mFragment = Fragment.instantiate(mActivity, mFragName);
			ft.add(android.R.id.content, mFragment);
		}

		/**
		 * 탭 선택이 해제되었을 때
		 */
		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			ft.remove(mFragment);
			mFragment = null;
		}

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
	        	Toast.makeText(this, "새로고침 메뉴를 선택했습니다.", Toast.LENGTH_SHORT).show();
	            return true;
	 
	        case R.id.menu_search:  // 검색 메뉴 선택
	        	Toast.makeText(this, "검색 메뉴를 선택했습니다.", Toast.LENGTH_SHORT).show();
	            return true;
	 
	        case R.id.menu_settings:  // 설정 메뉴 선택
	        	Toast.makeText(this, "설정 메뉴를 선택했습니다.", Toast.LENGTH_SHORT).show();
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
