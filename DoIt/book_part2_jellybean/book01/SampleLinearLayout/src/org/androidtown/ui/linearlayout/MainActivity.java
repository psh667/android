package org.androidtown.ui.linearlayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 리니어 레이아웃 사용하기
 * 
 * - 앱을 실행한 후 원하는 버튼을 하나 선택하면 리니어 레이아웃의 속성을 이용해 만든 화면이 보입니다.
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

        // 첫번째 버튼을 눌렀을 때 normal.xml 에 정의된 화면 레이아웃을 보여줍니다.
        Button button01 = (Button) findViewById(R.id.button01);
        button01.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		setContentView(R.layout.normal);
        	}
        });

        // 두번째 버튼을 눌렀을 때 padding.xml 에 정의된 화면 레이아웃을 보여줍니다.
        Button button02 = (Button) findViewById(R.id.button02);
        button02.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		setContentView(R.layout.padding);
        	}
        });

        // 세번째 버튼을 눌렀을 때 gravity.xml 에 정의된 화면 레이아웃을 보여줍니다.
        Button button03 = (Button) findViewById(R.id.button03);
        button03.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		setContentView(R.layout.gravity);
        	}
        });

        // 네번째 버튼을 눌렀을 때 weight.xml 에 정의된 화면 레이아웃을 보여줍니다.
        Button button04 = (Button) findViewById(R.id.button04);
        button04.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		setContentView(R.layout.weight);
        	}
        });

        // 다섯번째 버튼을 눌렀을 때 baseline.xml 에 정의된 화면 레이아웃을 보여줍니다.
        Button button05 = (Button) findViewById(R.id.button05);
        button05.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		setContentView(R.layout.baseline);
        	}
        });

        // 여섯번째 버튼을 눌렀을 때 gravitytext01.xml 에 정의된 화면 레이아웃을 보여줍니다.
        Button button06 = (Button) findViewById(R.id.button06);
        button06.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		setContentView(R.layout.gravitytext01);
        	}
        });

        // 일곱번째 버튼을 눌렀을 때 gravitytext02.xml 에 정의된 화면 레이아웃을 보여줍니다.
        Button button07 = (Button) findViewById(R.id.button07);
        button07.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		setContentView(R.layout.gravitytext02);
        	}
        });

        // 여덟번째 버튼을 눌렀을 때 gravitytext03.xml 에 정의된 화면 레이아웃을 보여줍니다.
        Button button08 = (Button) findViewById(R.id.button08);
        button08.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		setContentView(R.layout.gravitytext03);
        	}
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
