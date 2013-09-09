package org.androidtown.ui.ninepatch;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 일반 이미지의 크기가 자동 변경되었을 때와 나인패치 이미지의 크기가 자동 변경되었을 때를 비교해볼 수 있습니다.
 *
 * @author Mike
 *
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
