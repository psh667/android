package org.androidtown.ui.bitmap.widget;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

/**
 * 비트맵이 올라간 위젯을 직접 정의하는 방법을 알 수 있습니다.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 타이틀을 안보이도록 함
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        // 왼쪽 화살표를 보여주는 위젯 객체 참조
        TitleBitmapButton arrowLeftBtn = (TitleBitmapButton)findViewById(R.id.arrowLeftBtn);

        // 리소스의 이미지를 직접 가져와서 설정하는 방식
		Resources res = getResources();
		BitmapDrawable curDrawable = (BitmapDrawable) res.getDrawable(R.drawable.arrow_left);
		Bitmap iconBitmap = curDrawable.getBitmap();
		BitmapDrawable curClickedDrawable = (BitmapDrawable) res.getDrawable(R.drawable.arrow_left_clicked);
		Bitmap iconClickedBitmap = curClickedDrawable.getBitmap();
		arrowLeftBtn.setIconBitmap(iconBitmap, iconClickedBitmap);

		// 이벤트 처리
		arrowLeftBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 화면을 닫음
				finish();
			}
		});

		String titleText = "비트맵 Title";

		// 타이틀을 위한 버튼에 텍스트 설정
        TitleButton titleBtn = (TitleButton)findViewById(R.id.titleBtn);
        titleBtn.setTitleText(titleText);
        titleBtn.setDefaultSize(32F);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
