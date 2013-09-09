package org.androidtown.basic.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 기본 위젯 사용하기
 * 
 * - 앱을 실행한 후 원하는 버튼을 하나 선택하면 그 위젯을 사용한 화면이 보입니다.
 * - 화면이 어떻게 만들어졌는지는 res/layout 폴더 밑의 해당 XML 레이아웃 파일을 보시면 됩니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 첫번째 버튼을 눌렀을 때 textview.xml 에 정의된 화면 레이아웃을 보여줍니다.
        Button button01 = (Button) findViewById(R.id.button01);
        button01.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		setContentView(R.layout.textview);
        	}
        });

        // 두번째 버튼을 눌렀을 때 button.xml 에 정의된 화면 레이아웃을 보여줍니다.
        Button button02 = (Button) findViewById(R.id.button02);
        button02.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		setContentView(R.layout.button);
        	}
        });

        // 세번째 버튼을 눌렀을 때 edittext.xml 에 정의된 화면 레이아웃을 보여줍니다.
        Button button03 = (Button) findViewById(R.id.button03);
        button03.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		setContentView(R.layout.edittext);
        	}
        });

        // 네번째 버튼을 눌렀을 때 image.xml 에 정의된 화면 레이아웃을 보여줍니다.
        Button button04 = (Button) findViewById(R.id.button04);
        button04.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		setContentView(R.layout.image);
        	}
        });
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}
