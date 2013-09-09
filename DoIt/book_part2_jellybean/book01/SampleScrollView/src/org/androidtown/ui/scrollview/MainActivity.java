package org.androidtown.ui.scrollview;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

/**
 * 스크롤뷰 사용하기
 * 
 * - 앱을 실행하면 화면보다 큰 이미지가 손가락 터치에 따라 좌우/상하 스크롤됩니다.
 * - 가로방향과 세로방향 스크롤을 만들때 스크롤뷰를 어떻게 사용하는지 XML 레이아웃을 살펴봅니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

	ScrollView scrollView01;
	ImageView imageView01;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollView01 = (ScrollView) findViewById(R.id.scrollView01);
        imageView01 = (ImageView) findViewById(R.id.imageView01);
        Button button01 = (Button) findViewById(R.id.button01);

        // 가로 스크롤뷰의 메소드 호출
        scrollView01.setHorizontalScrollBarEnabled(true);

        // drawable 리소스에 있는 이미지를 가져와서 이미지뷰에 설정하기
        Resources res = getResources();
        BitmapDrawable bitmap = (BitmapDrawable) res.getDrawable(R.drawable.system_architecture);
        int bitmapWidth = bitmap.getIntrinsicWidth();
        int bitmapHeight = bitmap.getIntrinsicHeight();

        imageView01.setImageDrawable(bitmap);
        imageView01.getLayoutParams().width = bitmapWidth;
        imageView01.getLayoutParams().height = bitmapHeight;

        // 버튼을 눌렀을 때 이미지 바꾸어 보여주기
        button01.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		changeImage();
        	}
        });

    }

    /**
     * 다른 이미지로 바꾸어 보여주기
     */
    private void changeImage() {
    	Resources res = getResources();
        BitmapDrawable bitmap = (BitmapDrawable) res.getDrawable(R.drawable.activity_lifecycle);
        int bitmapWidth = bitmap.getIntrinsicWidth();
        int bitmapHeight = bitmap.getIntrinsicHeight();

        imageView01.setImageDrawable(bitmap);
        imageView01.getLayoutParams().width = bitmapWidth;
        imageView01.getLayoutParams().height = bitmapHeight;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
