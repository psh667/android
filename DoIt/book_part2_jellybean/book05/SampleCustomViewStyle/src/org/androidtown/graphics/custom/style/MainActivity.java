package org.androidtown.graphics.custom.style;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 직접 만든 뷰 위에 다양한 스타일의 그래픽을 그리는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 직접 만든 뷰를 화면에 설정
        CustomViewStyles myView = new CustomViewStyles(this);
        setContentView(myView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
