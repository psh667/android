package org.androidtown.animation.frame;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 프레임 단위 애니메이션 적용 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

	AnimationDrawable animDrawable = null;
	ImageView imageView01;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		imageView01 = (ImageView)findViewById(R.id.imageView01);

		// 시작 버튼 이벤트 처리
		Button startBtn = (Button) findViewById(R.id.startBtn);
		startBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startAnimation();
			}
		});

		// 중지 버튼 이벤트 처리
		Button stopBtn = (Button) findViewById(R.id.stopBtn);
		stopBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				stopAnimation();
			}
		});


		// 이미지 가져오기
		Resources res = getResources();
	 	BitmapDrawable frame01 = (BitmapDrawable)res.getDrawable(R.drawable.emo_im_crying);
	 	BitmapDrawable frame02 = (BitmapDrawable)res.getDrawable(R.drawable.emo_im_happy);
	 	BitmapDrawable frame03 = (BitmapDrawable)res.getDrawable(R.drawable.emo_im_laughing);
	 	BitmapDrawable frame04 = (BitmapDrawable)res.getDrawable(R.drawable.emo_im_surprised);

	 	// 프레임으로 추가하기
	 	int duration = 250;
	 	animDrawable = new AnimationDrawable();
	 	animDrawable.setOneShot(false);
	 	animDrawable.addFrame(frame01, duration);
	 	animDrawable.addFrame(frame02, duration);
	 	animDrawable.addFrame(frame03, duration);
	 	animDrawable.addFrame(frame04, duration);

    }

    /**
     * 애니메이션 시작
     */
	private void startAnimation() {
     	imageView01.setBackgroundDrawable(animDrawable);

     	animDrawable.setVisible(true, true);
     	animDrawable.start();
	}

	/**
	 * 애니메이션 중지
	 */
	private void stopAnimation() {
		animDrawable.stop();
		animDrawable.setVisible(false, false);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
