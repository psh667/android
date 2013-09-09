package org.androidtown.ui.linearlayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * 코드에서 직접 레이아웃을 만들어보기
 * 
 * @author Mike
 *
 */
public class SampleLayoutCodeActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 메인 레이아웃
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            	LinearLayout.LayoutParams.MATCH_PARENT,
            	LinearLayout.LayoutParams.WRAP_CONTENT);
        
        // 버튼 추가하여 생성
        Button button01 = new Button(this);
        button01.setText("Button 01");
        button01.setLayoutParams(params);
        mainLayout.addView(button01);

        // 화면 설정
        setContentView(mainLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
