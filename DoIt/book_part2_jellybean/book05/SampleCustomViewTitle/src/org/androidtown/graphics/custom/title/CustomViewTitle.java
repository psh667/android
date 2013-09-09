package org.androidtown.graphics.custom.title;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 뷰를 상속하여 새로운 뷰 정의
 *
 * @author Mike
 *
 */
public class CustomViewTitle extends View {

	/**
	 * Base Context
	 */
	Context context;

	/**
	 * Paint instance
	 */
	Paint paint;

	/**
	 * Default Color for the title text
	 */
	int defaultColor = 0xff0000ff;

	/**
	 * Default Size for the title text
	 */
	float defaultSize = 48F;

	/**
	 * Default ScaleX for the title text
	 */
	float defaultScaleX = 1.0F;

	/**
	 * Default Typeface
	 */
	Typeface defaultTypeface = Typeface.DEFAULT_BOLD;

	/**
	 * Title Text
	 */
	String titleText = "";

	/**
	 * Icon Status
	 */
	int iconStatus = 0;

	public static final int ICON_STATUS_NORMAL = 0;

	public static final int ICON_STATUS_CLICKED = 1;

	public static final int BITMAP_ALIGN_CENTER = 0;

	public static final int BITMAP_ALIGN_LEFT = 1;

	public static final int BITMAP_ALIGN_RIGHT = 2;


	int backgroundBitmapNormal;
	int backgroundBitmapClicked;

	/**
	 * Alignment
	 */
	int bitmapAlign = BITMAP_ALIGN_CENTER;

	/**
	 * Padding for Left or Right
	 */
	int bitmapPadding = 10;



	/**
	 * Flag for paint changed
	 */
	boolean paintChanged = false;

	/**
	 * Constructor
	 *
	 * @param context
	 */
	public CustomViewTitle(Context context) {
		super(context);

		this.context = context;

		init();
	}

	public CustomViewTitle(Context context, AttributeSet atts) {
		super(context, atts);

		this.context = context;

		init();
	}


	/**
	 * Initialize
	 */
	public void init() {
		// set default bitmap
		backgroundBitmapNormal = R.drawable.background_normal;
		backgroundBitmapClicked = R.drawable.background_clicked;

		setBackgroundResource(backgroundBitmapNormal);

		paint = new Paint();
		paint.setColor(defaultColor);
		paint.setAntiAlias(true);
		paint.setTextScaleX(defaultScaleX);
		paint.setTextSize(defaultSize);
		paint.setTypeface(defaultTypeface);

	}

	/**
	 * Set background bitmap
	 *
	 * @param resNormal
	 * @param resClicked
	 */
	public void setBackgroundBitmap(int resNormal, int resClicked) {
		backgroundBitmapNormal = resNormal;
		backgroundBitmapClicked = resClicked;

		setBackgroundResource(backgroundBitmapNormal);
	}


	/**
	 * Handles touch event, move to main screen
	 */
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		int action = event.getAction();

		switch (action) {
			case MotionEvent.ACTION_UP:

				setBackgroundResource(backgroundBitmapNormal);

				paintChanged = true;
				defaultColor = Color.WHITE;

				break;

			case MotionEvent.ACTION_DOWN:

				setBackgroundResource(backgroundBitmapClicked);

				paintChanged = true;
				defaultColor = Color.BLACK;

				break;

		}

		// repaint the screen
		invalidate();

		return true;
	}



	/**
	 * Draw the text
	 */
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int curWidth = getWidth();
		int curHeight = getHeight();


		// text
		Rect bounds = new Rect();
		paint.getTextBounds(titleText, 0, titleText.length(), bounds);
		float textWidth = ((float)curWidth - bounds.width())/2.0F;
		float textHeight = ((float)(curHeight) + bounds.height())/2.0F;


		paint.setStyle(Style.FILL);
		paint.setAntiAlias(true);
		paint.setColor(defaultColor);
		paint.setTextScaleX(defaultScaleX);
		paint.setTextSize(defaultSize);
		paint.setTypeface(defaultTypeface);
		paint.setShadowLayer(4, 1, 1, Color.WHITE);

		canvas.drawText(titleText, textWidth, textHeight, paint);
 
	}



	public String getTitleText() {
		return titleText;
	}

	public void setTitleText(String titleText) {
		this.titleText = titleText;
	}

	public int getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(int defaultColor) {
		this.defaultColor = defaultColor;
		paintChanged = true;
	}

	public float getDefaultSize() {
		return defaultSize;
	}

	public void setDefaultSize(float defaultSize) {
		this.defaultSize = defaultSize;
		paintChanged = true;
	}

	public float getDefaultScaleX() {
		return defaultScaleX;
	}

	public void setDefaultScaleX(float defaultScaleX) {
		this.defaultScaleX = defaultScaleX;
		paintChanged = true;
	}

	public Typeface getDefaultTypeface() {
		return defaultTypeface;
	}

	public void setDefaultTypeface(Typeface defaultTypeface) {
		this.defaultTypeface = defaultTypeface;
		paintChanged = true;
	}


}
