package org.androidtown.tutorial.anim;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 뷰에 애니메이션을 적용하는 방법에 대해 알 수 있습니다.
 * 
 * @author Mike
 *
 */
public class MainActivity extends Activity {

	View rootView;
	ImageView swingImage;
	ImageView waterImage;
	ImageView skyImage;

	Animation shakeAnimation;
	Animation dropAnimation;
	Animation flowAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // swing 이미지에 애니메이션 객체 설정
        swingImage = (ImageView) findViewById(R.id.swingImage);
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
        swingImage.setAnimation(shakeAnimation);

        // water 이미지에 애니메이션 객체 설정
        waterImage = (ImageView) findViewById(R.id.waterImage);
        dropAnimation = AnimationUtils.loadAnimation(this, R.anim.drop);
        waterImage.setAnimation(dropAnimation);

        // sky 이미지에 애니메이션 객체 설정
        skyImage = (ImageView) findViewById(R.id.skyImage);
        flowAnimation = AnimationUtils.loadAnimation(this, R.anim.flow);
        skyImage.setAnimation(flowAnimation);

        Resources res = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.sky_background);

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        ViewGroup.LayoutParams params = skyImage.getLayoutParams();
        params.width = bitmapWidth;
        params.height = bitmapHeight;

        skyImage.setImageBitmap(bitmap);

        flowAnimation.setAnimationListener(new AnimationAdapter());

    }

    /**
     * 화면에 보여지기 전에 호출되는 메소드
     */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		Toast.makeText(getApplicationContext(), "onWindowFocusChanged : " + hasFocus, 2000).show();

		if (hasFocus) {
			shakeAnimation.start();
			dropAnimation.start();
			flowAnimation.start();
		} else {
			shakeAnimation.reset();
			dropAnimation.reset();
			flowAnimation.reset();
		}

	}



    @Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();

		Toast.makeText(getApplicationContext(), "attached.", 2000).show();
	}



	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		Toast.makeText(getApplicationContext(), "detached.", 2000).show();
	}


	/**
	 * 애니메이션의 시작과 종료 시점을 알기 위한 리스너
	 */
	private final class AnimationAdapter implements Animation.AnimationListener {

		public void onAnimationStart(Animation animation) {
			Toast.makeText(getApplicationContext(), "Animation started.", 2000).show();
		}

		public void onAnimationEnd(Animation animation) {
			Toast.makeText(getApplicationContext(), "Animation ended.", 2000).show();
		}

		public void onAnimationRepeat(Animation animation) {

		}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
