package org.androidtown.graphics.custom.drawables;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 직접 만든 뷰 위에 다양한 그래픽 요소들을 그리는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 직접 만든 뷰를 화면에 설정
        CustomViewDrawables myView = new CustomViewDrawables(this);
        setContentView(myView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
