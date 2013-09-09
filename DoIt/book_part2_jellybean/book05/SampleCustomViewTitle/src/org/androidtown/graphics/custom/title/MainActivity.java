package org.androidtown.graphics.custom.title;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

/**
 * 제목을 표시하는 뷰를 직접 만드는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 타이틀 부분에 텍스트 설정
        CustomViewTitle titleView = (CustomViewTitle) findViewById(R.id.titleView);
        titleView.setTitleText("마켓에 올리는 앱");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
