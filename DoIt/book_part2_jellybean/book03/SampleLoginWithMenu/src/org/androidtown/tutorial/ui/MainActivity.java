package org.androidtown.tutorial.ui;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 로그인과 메뉴 기능을 가지고 있는 화면 
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

	/**
	 * 다른 액티비티를 띄우기 위한 요청코드
	 */
	public static final int REQUEST_CODE_ANOTHER = 1001;
	
	/**
	 * About 액티비티를 띄우기 위한 요청코드
	 */
	public static final int REQUEST_CODE_ABOUT = 1002;
	
	/**
	 * 설정 액티비티를 띄우기 위한 요청코드
	 */
	public static final int REQUEST_CODE_SETTINGS = 1003;
	
	/**
	 * 타이틀
	 */
	TextView titleLabel;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 뷰 객체 참조
        final EditText usernameEntry = (EditText) findViewById(R.id.usernameEntry);
        final EditText passwordEntry = (EditText) findViewById(R.id.passwordEntry);
        titleLabel = (TextView) findViewById(R.id.titleLabel);
        
        // 로그인 버튼 이벤트 처리
        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		
        		String username = usernameEntry.getText().toString();
        		String password = passwordEntry.getText().toString();
        		
        		// 로그인 메소드 호출
        		boolean isLogged = checkLogin(username, password);
        		if (isLogged) {
        			// 로그인 정상이면 다른 액티비티 띄우기
	    			Intent intent = new Intent(getBaseContext(), AnotherActivity.class);
	   				startActivityForResult(intent, REQUEST_CODE_ANOTHER);
        		}
        		
        	}
        });
        
    }


    /**
     * 가상의 로그인 처리를 하는 메소드
     * 
     * @param username
     * @param password
     */
    private boolean checkLogin(String username, String password) {
    	// do something for login
    	Toast toast = Toast.makeText(getBaseContext(), "checkLogin() 메소드 호출됨. \nusername : " + username + ", password : " + password, Toast.LENGTH_LONG);
		toast.show();
    	
    	return true;
    }
    
    
    /**
     * 다른 액티비티에서 돌아올 때
     */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == REQUEST_CODE_ANOTHER) {
			Toast toast = Toast.makeText(getBaseContext(), "onActivityResult() 호출됨. 응답 코드 : " + resultCode, Toast.LENGTH_LONG);
			toast.show();
			
			if (resultCode == Activity.RESULT_OK) {
				int color = data.getExtras().getInt("color");
				toast = Toast.makeText(getBaseContext(), "result color : " + color, Toast.LENGTH_LONG);
				toast.show();
				
				// 텍스트뷰의 글자색 바꾸기
				titleLabel.setTextColor(color);
			}
			
		}
		
	}
	    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// 코드에서 직접 옵션 메뉴 추가하기
    	super.onCreateOptionsMenu(menu);
    	addOptionMenuItems(menu);
    	
        return true;
    }
    

	/**
	 * 옵션 메뉴의 아이템들 추가
	 * 
	 * @param menu
	 */
    private void addOptionMenuItems(Menu menu) {
    	int base = Menu.FIRST;

    	MenuItem item01 = menu.add(base, base, Menu.NONE,"Settings");
    	MenuItem item02 = menu.add(base, base+1, Menu.NONE,"About");
    	
    	item01.setIcon(R.drawable.settings_icon);
    	item02.setIcon(R.drawable.about_icon);
    }
    	
    /**
     * 옵션 메뉴가 선택되었을 때 호출됨
     */
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (item.getItemId() == 1) {
    		// 설정 화면 띄워주기
			Intent intent = new Intent(getBaseContext(), SettingsActivity.class);
			startActivityForResult(intent, REQUEST_CODE_SETTINGS);
    	} else if (item.getItemId() == 2) {
    		// About 화면 띄워주기
			Intent intent = new Intent(getBaseContext(), AboutDialog.class);
			startActivityForResult(intent, REQUEST_CODE_ABOUT);
    	}
    	
    	return true;
    }	
	
    
}
