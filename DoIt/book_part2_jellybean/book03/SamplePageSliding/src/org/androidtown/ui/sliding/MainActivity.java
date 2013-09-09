package org.androidtown.ui.sliding;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * 두 개의 레이아웃을 중첩시켜 둔 후 버튼을 누르면 보이지 않던 화면이 애니메이션되면서 보이는 과정을 알 수 있습니다.
 * 
 * @author Mike
 */
public class MainActivity extends Activity {

	/**
	 * 페이지가 열려 있는지 알기 위한 플래그
	 */
	boolean isPageOpen = false;

	/**
	 * 애니메이션 객체
	 */
	Animation translateLeftAnim;
	Animation translateRightAnim;

	/**
	 * 슬라이딩으로 보여지는 페이지 레이아웃
	 */
	LinearLayout slidingPage01;
	
	/**
	 * 버튼
	 */
	Button openBtn01;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 슬라이딩으로 보여질 레이아웃 객체 참조
        slidingPage01 = (LinearLayout) findViewById(R.id.slidingPage01);

        // 애니메이션 객체 로딩
        translateLeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        translateRightAnim = AnimationUtils.loadAnimation(this, R.anim.translate_right);

        // 애니메이션 객체에 리스너 설정
        SlidingPageAnimationListener animListener = new SlidingPageAnimationListener();
        translateLeftAnim.setAnimationListener(animListener);
        translateRightAnim.setAnimationListener(animListener);


        // 버튼 이벤트 처리
        openBtn01 = (Button) findViewById(R.id.openBtn01);
        openBtn01.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		// 애니메이션 적용
        		if (isPageOpen) {
        			slidingPage01.startAnimation(translateRightAnim);
        		} else {
        			slidingPage01.setVisibility(View.VISIBLE);
        			slidingPage01.startAnimation(translateLeftAnim);
        		}
        	}
        });

    }
    
    /**
     * 애니메이션 리스너 정의
     */
    private class SlidingPageAnimationListener implements AnimationListener {
    	/**
    	 * 애니메이션이 끝날 때 호출되는 메소드
    	 */
		public void onAnimationEnd(Animation animation) {
			if (isPageOpen) {
				slidingPage01.setVisibility(View.INVISIBLE);

				openBtn01.setText("Open");
				isPageOpen = false;
			} else {
				openBtn01.setText("Close");
				isPageOpen = true;
			}
		}

		public void onAnimationRepeat(Animation animation) {

		}

		public void onAnimationStart(Animation animation) {

		}

    }

    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
