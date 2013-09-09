package com.androidbook;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class SampleDialogActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    Menu myMenu = null;

	@Override 
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		  MenuInflater inflater = getMenuInflater(); // 액티비티로부터
		  inflater.inflate(R.menu.my_menu, menu); 

		  // 반드시 true를 반환해야만 메뉴가 보인다.
		  return true;
	}

	@Override 
	public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_simple_alert) 
        {
        	//String reply = 
        	Alerts.showAlert("간단한 샘플 경고창", this);
        }
        // 메뉴 항목 처리가 완료되면 반드시 true를 반환해야 한다.
		return true;
	}

}
