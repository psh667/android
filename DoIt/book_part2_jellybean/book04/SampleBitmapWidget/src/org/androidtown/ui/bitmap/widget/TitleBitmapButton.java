package org.androidtown.ui.bitmap.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

public class TitleBitmapButton extends Button {

	/**
	 * Base Context
	 */
	Context context;

	/**
	 * Paint instance
	 */
	Paint paint;

	/**
	 * Default Color
	 */
	int defaultColor = 0xffffffff;

	/**
	 * Default Size
	 */
	float defaultSize = 18F;

	/**
	 * Default ScaleX
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
	 * Icon Status : 0 - Normal, 1 - Clicked
	 */
	int iconStatus = 0;

	/**
	 * Icon Clicked Bitmap
	 */
	Bitmap iconNormalBitmap;

	/**
	 * Icon Clicked Bitmap
	 */
	Bitmap iconClickedBitmap;

	public static final int BITMAP_ALIGN_CENTER = 0;

	public static final int BITMAP_ALIGN_LEFT = 1;

	public static final int BITMAP_ALIGN_RIGHT = 2;



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

	public TitleBitmapButton(Context context) {
		super(context);

		this.context = context;
		init();
	}

	public TitleBitmapButton(Context context, AttributeSet atts) {
		super(context, atts);

		this.context = context;
		init();
	}

	/**
	 * Initialize
	 */
	public void init() {
		setBackgroundResource(R.drawable.title_bitmap_button_normal);

		paint = new Paint();
		paint.setColor(defaultColor);
		paint.setAntiAlias(true);
		paint.setTextScaleX(defaultScaleX);
		paint.setTextSize(defaultSize);
		paint.setTypeface(defaultTypeface);

	}

	/**
	 * Set icon bitmap
	 *
	 * @param icon
	 */
	public void setIconBitmap(Bitmap iconNormal, Bitmap iconClicked) {
		iconNormalBitmap = iconNormal;
		iconClickedBitmap = iconClicked;

	}


	/**
	 * Handles touch event, move to main screen
	 */
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		int action = event.getAction();

		switch (action) {
			case MotionEvent.ACTION_OUTSIDE:
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				setBackgroundResource(R.drawable.title_bitmap_button_normal);

				iconStatus = 0;

				break;

			case MotionEvent.ACTION_DOWN:
				setBackgroundResource(R.drawable.title_bitmap_button_clicked);

				iconStatus = 1;

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

		// apply paint attributes
		if (paintChanged) {
			paint.setColor(defaultColor);
			paint.setTextScaleX(defaultScaleX);
			paint.setTextSize(defaultSize);
			paint.setTypeface(defaultTypeface);
		}

		// bitmap
		Bitmap iconBitmap = iconNormalBitmap;
		if (iconStatus == 1) {
			iconBitmap = iconClickedBitmap;
		}

		if (iconBitmap != null) {
			int iconWidth = iconBitmap.getWidth();
			int iconHeight = iconBitmap.getHeight();
			int bitmapX = 0;
			if (bitmapAlign == BITMAP_ALIGN_CENTER) {
				bitmapX = (curWidth-iconWidth)/2;
			} else if(bitmapAlign == BITMAP_ALIGN_LEFT) {
				bitmapX = bitmapPadding;
			} else if(bitmapAlign == BITMAP_ALIGN_RIGHT) {
				bitmapX = curWidth-bitmapPadding;
			}

			canvas.drawBitmap(iconBitmap, bitmapX, (curHeight-iconHeight)/2, paint);
		}

		// text
		Rect bounds = new Rect();
		paint.getTextBounds(titleText, 0, titleText.length(), bounds);
		float textWidth = ((float)curWidth - bounds.width())/2.0F;
		float textHeight = ((float)(curHeight-4) + bounds.height())/2.0F-1.0F;

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

	public int getBitmapAlign() {
		return bitmapAlign;
	}

	public void setBitmapAlign(int bitmapAlign) {
		this.bitmapAlign = bitmapAlign;
	}

	public int getBitmapPadding() {
		return bitmapPadding;
	}

	public void setBitmapPadding(int bitmapPadding) {
		this.bitmapPadding = bitmapPadding;
	}



}
