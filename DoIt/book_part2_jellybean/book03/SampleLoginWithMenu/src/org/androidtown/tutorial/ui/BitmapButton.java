package org.androidtown.tutorial.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * 이미지가 보이는 비트맵 버튼 정의
 * 
 * @author Mike
 */
public class BitmapButton extends Button {

	int normalBitmapId;
	int clickedBitmapId;
	
	public BitmapButton(Context context) {
		super(context);
	}

	public BitmapButton(Context context, AttributeSet atts) {
		super(context, atts);

	}

	public void setBitmapId(int normalId, int clickedId) {
		normalBitmapId = normalId;
		clickedBitmapId = clickedId;
		
		setBackgroundResource(normalBitmapId);
	}
	
	
	/**
	 * 터치 이벤트 처리
	 */
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		
		int action = event.getAction();

		switch (action) {
			case MotionEvent.ACTION_UP:
				setBackgroundResource(normalBitmapId);
				
				break;
	
			case MotionEvent.ACTION_DOWN:
				setBackgroundResource(clickedBitmapId);
				
				break;

		}

		// 다시 그리기
		invalidate();

		return true;
	}	
	
}
