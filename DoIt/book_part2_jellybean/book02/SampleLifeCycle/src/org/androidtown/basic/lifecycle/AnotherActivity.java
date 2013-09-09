package org.androidtown.basic.lifecycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 새로운 액티비티
 * 
 * @author Mike
 */
public class AnotherActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another);

        Button backBtn = (Button) findViewById(R.id.backBtn);
		
		// 버튼을 눌렀을 때 메인 액티비티로 돌아갑니다.
		backBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
        		// 객체를 만듭니다.
        		Intent resultIntent = new Intent();
        		resultIntent.putExtra("name", "mike");

                // 응답을 전달하고 이 액티비티를 종료합니다.
        		setResult(RESULT_OK, resultIntent);
                finish();
			}
		});
		
		Toast.makeText(this, "새로운 액티비티의 onDestroy() 호출됨.", Toast.LENGTH_LONG).show();
	    
    }


    
    @Override
	protected void onDestroy() {
    	Toast.makeText(this, "새로운 액티비티의 onDestroy() 호출됨.", Toast.LENGTH_LONG).show();
    	
		super.onDestroy();
	}


	@Override
	protected void onPause() {
		Toast.makeText(this, "새로운 액티비티의 onPause() 호출됨.", Toast.LENGTH_LONG).show();
    	
		super.onPause();
	}


	@Override
	protected void onRestart() {
		Toast.makeText(this, "새로운 액티비티의 onRestart() 호출됨.", Toast.LENGTH_LONG).show();
    	
		super.onRestart();
	}


	@Override
	protected void onResume() {
		Toast.makeText(this, "새로운 액티비티의 onResume() 호출됨.", Toast.LENGTH_LONG).show();
    	
		super.onResume();
	}


	@Override
	protected void onStart() {
		Toast.makeText(this, "새로운 액티비티의 onStart() 호출됨.", Toast.LENGTH_LONG).show();
    	
		super.onStart();
	}


	@Override
	protected void onStop() {
		Toast.makeText(this, "새로운 액티비티의 onStop() 호출됨.", Toast.LENGTH_LONG).show();
    	
		super.onStop();
	}

	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
