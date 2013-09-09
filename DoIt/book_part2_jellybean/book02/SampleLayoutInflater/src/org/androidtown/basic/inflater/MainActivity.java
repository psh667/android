package org.androidtown.basic.inflater;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

/**
 * 레이아웃 인플레이터를 이용해 레이아웃의 일부를 동적으로 로딩하는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // 버튼을 눌렀을 때 레이아웃을 동적으로 로딩합니다.
        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 인플레이션으로 contentsLayout 안에 레이아웃을 설정합니다.
				inflateLayout();
			}
		});
        
    }

    /**
     * button.xml 에 정의된 레이아웃을 메인 액티비티의 레이아웃 일부로 추가하는 메소드 정의
     */
    private void inflateLayout() {
        // XML 레이아웃에 정의된 contentsLayout 객체 참조
        LinearLayout contentsLayout = (LinearLayout) findViewById(R.id.contentsLayout);

        // 인플레이션 수행
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.button, contentsLayout, true);

        // 새로 추가한 레이아웃 안에 들어있는 버튼 객체 참조
		Button btnSelect = (Button) findViewById(R.id.btnSelect);
		final CheckBox allDay = (CheckBox) findViewById(R.id.allDay);

		btnSelect.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (allDay.isChecked()) {
					allDay.setChecked(false);
				} else {
					allDay.setChecked(true);
				}
			}
		});

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
