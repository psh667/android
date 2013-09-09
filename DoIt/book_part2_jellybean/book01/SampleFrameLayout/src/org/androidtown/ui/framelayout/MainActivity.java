package org.androidtown.ui.framelayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 프레임 레이아웃을 이용해 두 개의 뷰를 중첩시키고 가시성 속성을 이용해 서로 바꾸면서 보여줍니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

	Button button01;
	ImageView imageView01;
	ImageView imageView02;
	int imageIndex = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 이미지 바꾸기 버튼
        button01 = (Button) findViewById(R.id.button01);
        
        // 첫번째 이미지 뷰
        imageView01 = (ImageView) findViewById(R.id.imageView01);
        
        // 두번째 이미지 뷰
        imageView02 = (ImageView) findViewById(R.id.imageView02);

        // 이미지 바꾸기 버튼을 눌렀을 때
        button01.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		changeImage();
        	}
        });

    }

    /**
     * 현재 보고있는 이미지뷰가 아닌 다른 이미지뷰를 보이도록 하는 메소드
     */
    private void changeImage() {
    	if (imageIndex == 0) {
    		imageView01.setVisibility(View.VISIBLE);
    		imageView02.setVisibility(View.INVISIBLE);

    		imageIndex = 1;
    	} else if (imageIndex == 1) {
    		imageView01.setVisibility(View.INVISIBLE);
    		imageView02.setVisibility(View.VISIBLE);

    		imageIndex = 0;
    	}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
