package org.androidtown.ui.flipper;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 뷰플리퍼를 사용하는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
