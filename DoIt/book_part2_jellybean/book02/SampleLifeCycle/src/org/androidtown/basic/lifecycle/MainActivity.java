package org.androidtown.basic.lifecycle;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 액티비티의 수명주기에 대해 알 수 있습니다.
 *
 * @author Mike
 */
public class MainActivity extends Activity {

	/**
	 * 요청 코드 정의
	 */
	public static final int REQUEST_CODE_ANOTHER = 1001;
	
	public static final String PREF_ID = "Pref01";  
	public static final int actMode = Activity.MODE_PRIVATE;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button showBtn = (Button) findViewById(R.id.showBtn);
		
		// 버튼을 눌렀을 때 새로운 액티비티를 띄워줍니다.
		showBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {			
				// 인텐트 객체를 만듭니다.
    			Intent intent = new Intent(getBaseContext(), AnotherActivity.class);
    			
    			// 액티비티를 띄워주도록 startActivityForResult() 메소드를 호출합니다.
   				startActivityForResult(intent, REQUEST_CODE_ANOTHER);	
			}
		});

		Toast.makeText(getBaseContext(), "onCreate() 호출됨.", Toast.LENGTH_LONG).show();
    	
    }

    
    @Override
	protected void onDestroy() {
    	Toast.makeText(getBaseContext(), "onDestroy() 호출됨.", Toast.LENGTH_LONG).show();
    	
		super.onDestroy();
	}


	@Override
	protected void onPause() {
		Toast.makeText(getBaseContext(), "onPause() 호출됨.", Toast.LENGTH_LONG).show();
    	
		saveCurrentState();
		
		super.onPause();
	}


	@Override
	protected void onRestart() {
		Toast.makeText(getBaseContext(), "onRestart() 호출됨.", Toast.LENGTH_LONG).show();
    	
		super.onRestart();
	}


	@Override
	protected void onResume() {
		Toast.makeText(getBaseContext(), "onResume() 호출됨.", Toast.LENGTH_LONG).show();
    	
		restoreFromSavedState();
		
		super.onResume();
	}


	@Override
	protected void onStart() {
		Toast.makeText(getBaseContext(), "onStart() 호출됨.", Toast.LENGTH_LONG).show();
    	
		super.onStart();
	}


	@Override
	protected void onStop() {
		Toast.makeText(getBaseContext(), "onStop() 호출됨.", Toast.LENGTH_LONG).show();
    	
		super.onStop();
	}



	/**
     * 새로운 액티비티에서 돌아올 때 자동 호출되는 메소드
     */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_CODE_ANOTHER) {
			Toast toast = Toast.makeText(getBaseContext(), "onActivityResult() 메소드가 호출됨. 요청코드 : " + requestCode + ", 결과코드 : " + resultCode, Toast.LENGTH_LONG);
			toast.show();
		}

	}

	protected void restoreFromSavedState() {
		SharedPreferences myPrefs = getSharedPreferences(PREF_ID, actMode);
		if ((myPrefs != null) && (myPrefs.contains("txtMsg")) ) {
			String myData = myPrefs.getString("txtMsg", "");
			Toast.makeText(this, "Restored : " + myData, Toast.LENGTH_SHORT).show();
		}
	}
 
	protected void saveCurrentState() {
		SharedPreferences myPrefs = getSharedPreferences(PREF_ID, actMode);
		SharedPreferences.Editor myEditor = myPrefs.edit();
		myEditor.putString( "txtMsg", "My name is mike." );
		myEditor.commit();
	}
 
	protected void clearMyPrefs() {
		SharedPreferences myPrefs = getSharedPreferences(PREF_ID, actMode);
		SharedPreferences.Editor myEditor = myPrefs.edit();
		myEditor.clear();
		myEditor.commit();
	}

	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
