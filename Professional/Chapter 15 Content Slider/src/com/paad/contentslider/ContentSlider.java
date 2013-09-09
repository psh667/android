package com.paad.contentslider;

import android.app.Activity;
import android.view.KeyEvent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class ContentSlider extends Activity {

	TextPosition textPosition = TextPosition.Center;
	enum TextPosition { UpperLeft, Top, UpperRight,
	                    Left, Center, Right,
	                    LowerLeft, Bottom, LowerRight };
	
	Animation slideInLeft;
	Animation slideOutLeft;
	Animation slideInRight;
	Animation slideOutRight;
	Animation slideInTop;
	Animation slideOutTop;
	Animation slideInBottom;
	Animation slideOutBottom;
	TextView myTextView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);

	    slideInLeft = AnimationUtils.loadAnimation(this, R.anim.slide_left_in);
	    slideOutLeft = AnimationUtils.loadAnimation(this, R.anim.slide_left_out);
	    slideInRight = AnimationUtils.loadAnimation(this, R.anim.slide_right_in);
	    slideOutRight = AnimationUtils.loadAnimation(this, R.anim.slide_right_out);
	    slideInTop = AnimationUtils.loadAnimation(this, R.anim.slide_top_in);
	    slideOutTop = AnimationUtils.loadAnimation(this, R.anim.slide_top_out);
	    slideInBottom = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_in);
	    slideOutBottom = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_out);

	    myTextView = (TextView)findViewById(R.id.myTextView);
	}
	
	private void applyAnimation(Animation _out, Animation _in, String _newText) {
		final String text = _newText;
		final Animation in = _in;

		// 밖으로 슬라이딩되는 애니메이션이 완료될 때
		// 텍스트가 화면 밖에 머물도록 보장한다.
		_out.setFillAfter(true);

		// 밖으로 슬라이딩되는 애니메이션이 완료되기를 기다리는
		// 리스너를 만든다.
		_out.setAnimationListener(new AnimationListener() {
			public void onAnimationEnd(Animation _animation) {
				// 텍스트를 바꾼다.
				myTextView.setText(text);
				// 이를 다시 뷰 안으로 슬라이딩한다.
				myTextView.startAnimation(in);
			}

			public void onAnimationRepeat(Animation _animation) {}

			public void onAnimationStart(Animation _animation) {}
		});

		// 밖으로 슬라이딩되는 애니메이션을 적용한다.
		myTextView.startAnimation(_out);
	}
	
	private void movePosition(TextPosition _current, TextPosition _directionPressed) {
		Animation in;
		Animation out;
		TextPosition newPosition;

		if (_directionPressed == TextPosition.Left) {
			in = slideInLeft;
			out = slideOutLeft;
		} else if (_directionPressed == TextPosition.Right) {
			in = slideInRight;
			out = slideOutRight;
		} else if (_directionPressed == TextPosition.Top) {
			in = slideInTop;
			out = slideOutTop;
		} else {
			in = slideInBottom;
			out = slideOutBottom;
		}

		int newPosValue = _current.ordinal();
		int currentValue = _current.ordinal();

		// 장치를 한쪽 방향으로 기울이는 효과를 시뮬레이션 하려면
		// 텍스트가 반대 방향에서 나타나게끔 해야 한다.
		// 예컨대, 장치가 오른쪽으로 기운다면 텍스트가 왼쪽에서 나타나야 한다.
		if (_directionPressed == TextPosition.Bottom)
			newPosValue = currentValue - 3;
		else if (_directionPressed == TextPosition.Top)
			newPosValue = currentValue + 3;
		else if (_directionPressed == TextPosition.Right) {
			if (currentValue % 3 != 0)
				newPosValue = currentValue - 1;
		} else if (_directionPressed == TextPosition.Left) {
			if ((currentValue + 1) % 3 != 0)
				newPosValue = currentValue + 1;
		}
		if (newPosValue != currentValue && newPosValue > -1 && newPosValue < 9) {
			newPosition = TextPosition.values()[newPosValue];

			applyAnimation(in, out, newPosition.toString());
			textPosition = newPosition;
		}
	}

	@Override
	public boolean onKeyDown(int _keyCode, KeyEvent _event) {
	    if (super.onKeyDown(_keyCode, _event))
	        return true;

	    if (_event.getAction() == KeyEvent.ACTION_DOWN){
	        switch (_keyCode) {
	            case (KeyEvent.KEYCODE_DPAD_LEFT):
	                movePosition(textPosition, TextPosition.Left); return true;
	            case (KeyEvent.KEYCODE_DPAD_RIGHT):
	                movePosition(textPosition, TextPosition.Right); return true;
	            case (KeyEvent.KEYCODE_DPAD_UP):
	                movePosition(textPosition, TextPosition.Top); return true;
	            case (KeyEvent.KEYCODE_DPAD_DOWN):
	                movePosition(textPosition, TextPosition.Bottom);
	            return true;
	        }
	    }
	    return false;
	}
}
