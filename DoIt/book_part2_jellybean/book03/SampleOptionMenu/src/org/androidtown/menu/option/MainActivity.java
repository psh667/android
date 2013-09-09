package org.androidtown.menu.option;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * 메뉴를 사용하는 가장 기초적인 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    }

    /**
     * XML에 정의한 메뉴 정보를 메모리에 로딩하여 현재 화면의 옵션 메뉴로 설정합니다.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * 메뉴를 선택할 때 자동 호출됩니다.
     */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int curId = item.getItemId();
		switch(curId) {
			case R.id.menu_refresh:
				Toast.makeText(this, "새로고침 메뉴가 선택되었습니다.", Toast.LENGTH_SHORT).show();
				break;
			case R.id.menu_search:
				Toast.makeText(this, "검색 메뉴가 선택되었습니다.", Toast.LENGTH_SHORT).show();
				break;
			case R.id.menu_settings:
				Toast.makeText(this, "설정 메뉴가 선택되었습니다.", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
		}
		
		return super.onOptionsItemSelected(item);
	}
    
}
