package org.androidtown.tutorial.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.Toast;

public class TextButtonItem extends Button {

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
	int defaultColor = 0xffff8206;

	/**
	 * Default Size
	 */
	float defaultSize = 16F;
	
	/**
	 * Default ScaleX
	 */
	float defaultScaleX = 1.0F;
	
	/**
	 * Default Typeface
	 */
	Typeface defaultTypeface = Typeface.DEFAULT_BOLD;

	/**
	 * Default Contents Color
	 */
	int defaultContentsColor = 0xffffffff;

	/**
	 * Default Size
	 */
	float defaultContentsSize = 14F;
	
	/**
	 * Default ScaleX
	 */
	float defaultContentsScaleX = 1.0F;
	
	/**
	 * Default Typeface
	 */
	Typeface defaultContentsTypeface = Typeface.DEFAULT;

	
	/**
	 * Icon Bitmap
	 */
	Bitmap iconBitmap;
	
	/**
	 * Title Text
	 */
	String titleText;
	
	/**
	 * Contents Text
	 */
	String contentsText;

	public TextButtonItem(Context context) {
		super(context);
		
		this.context = context;
		init();
	}

	public TextButtonItem(Context context, AttributeSet atts) {
		super(context, atts);

		this.context = context;
		init();
	}

	/**
	 * Initialize
	 */
	public void init() {
		setHeight(64);
		
		setBackgroundResource(R.drawable.title_background);
		
		paint = new Paint();
		paint.setColor(defaultColor);
		paint.setAntiAlias(true);
		paint.setTextScaleX(defaultScaleX);
		paint.setTextSize(defaultSize);
		paint.setTypeface(defaultTypeface);

		Resources res = getResources();
		BitmapDrawable curDrawable = (BitmapDrawable) res.getDrawable(R.drawable.news_icon);
		iconBitmap = curDrawable.getBitmap();
		
	}
	
	
	/**
	 * Handles touch event, move to main screen
	 */
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		
		int action = event.getAction();

		switch (action) {
			case MotionEvent.ACTION_UP:
				
				break;
	
			case MotionEvent.ACTION_DOWN:
				Toast.makeText(context, titleText, 1000).show();
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

		// title
		paint.setColor(defaultColor);
		paint.setTextScaleX(defaultScaleX);
		paint.setTextSize(defaultSize);
		paint.setTypeface(defaultTypeface);
		
		Rect bounds = new Rect();
		paint.getTextBounds(titleText, 0, titleText.length(), bounds);
		float textWidth = ((float)curWidth - bounds.width())/2.0F;
		float textHeight = ((float)(curHeight-2) + bounds.height())/3.0F-1.0F;
		
		canvas.drawText(titleText, textWidth, textHeight, paint);
		
		canvas.drawBitmap(iconBitmap, textWidth-iconBitmap.getWidth()-10, textHeight-iconBitmap.getHeight() + (iconBitmap.getHeight()-bounds.height())/2, paint);
		
		// contents
		paint.setColor(defaultContentsColor);
		paint.setTextScaleX(defaultContentsScaleX);
		paint.setTextSize(defaultContentsSize);
		paint.setTypeface(defaultContentsTypeface);
		
		Rect contentsBounds = new Rect();
		paint.getTextBounds(contentsText, 0, contentsText.length(), contentsBounds);
		float contentsWidth = ((float)curWidth - contentsBounds.width())/2.0F;
		float contentsHeight = (((float)(curHeight-2) + contentsBounds.height())*2.0F/3.0F)-1.0F;
		
		canvas.drawText(contentsText, contentsWidth, contentsHeight, paint);
				
	}
	
	public String getTitleText() {
		return titleText;
	}

	public void setTitleText(String titleText) {
		this.titleText = titleText;
	}

	public String getContentsText() {
		return contentsText;
	}

	public void setContentsText(String contentsText) {
		this.contentsText = contentsText;
	}
	
	public int getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(int defaultColor) {
		this.defaultColor = defaultColor;
	}

	public float getDefaultSize() {
		return defaultSize;
	}

	public void setDefaultSize(float defaultSize) {
		this.defaultSize = defaultSize;
	}

	public float getDefaultScaleX() {
		return defaultScaleX;
	}

	public void setDefaultScaleX(float defaultScaleX) {
		this.defaultScaleX = defaultScaleX;
	}
	
	public Typeface getDefaultTypeface() {
		return defaultTypeface;
	}

	public void setDefaultTypeface(Typeface defaultTypeface) {
		this.defaultTypeface = defaultTypeface;
	}

	public int getDefaultContentsColor() {
		return defaultContentsColor;
	}

	public void setDefaultContentsColor(int defaultContentsColor) {
		this.defaultContentsColor = defaultContentsColor;
	}

	public float getDefaultContentsSize() {
		return defaultContentsSize;
	}

	public void setDefaultContentsSize(float defaultContentsSize) {
		this.defaultContentsSize = defaultContentsSize;
	}

	public float getDefaultContentsScaleX() {
		return defaultContentsScaleX;
	}

	public void setDefaultContentsScaleX(float defaultContentsScaleX) {
		this.defaultContentsScaleX = defaultContentsScaleX;
	}

	public Typeface getDefaultContentsTypeface() {
		return defaultContentsTypeface;
	}

	public void setDefaultContentsTypeface(Typeface defaultContentsTypeface) {
		this.defaultContentsTypeface = defaultContentsTypeface;
	}
	
	
}
