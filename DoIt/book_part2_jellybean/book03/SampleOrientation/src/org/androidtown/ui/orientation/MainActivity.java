package org.androidtown.ui.orientation;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * 단말의 방향을 바꿀 때 발생하는 이벤트를 처리하는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 전체 화면 보기
        final Window win = getWindow();
        win.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    /**
     * 방향이 바뀔 때 호출됨
     */
    public void onConfigurationChanged(Configuration newConfig) {
    	super.onConfigurationChanged(newConfig);
    	
    	Toast.makeText(this, "onConfigurationChanged() 호출됨", Toast.LENGTH_SHORT).show();
       
    	if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
    		Toast.makeText(this, "Orientation : ORIENTATION_LANDSCAPE", Toast.LENGTH_SHORT).show();
    	} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
    	  Toast.makeText(this, "Orientation : ORIENTATION_PORTRAIT", Toast.LENGTH_SHORT).show();
    	}

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
