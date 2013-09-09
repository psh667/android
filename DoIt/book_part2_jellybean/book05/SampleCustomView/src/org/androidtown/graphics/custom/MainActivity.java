package org.androidtown.graphics.custom;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 뷰를 상속하여 새로운 뷰를 만드는 방법에 대해 알 수 있습니다.
 * 메인 액티비티의 화면에는 XML레이아웃으로 만든 것이 아닌 직접 만든 뷰를 설정합니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 직접 만든 뷰 화면에 설정하기
        CustomView myView = new CustomView(this);
        setContentView(myView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
