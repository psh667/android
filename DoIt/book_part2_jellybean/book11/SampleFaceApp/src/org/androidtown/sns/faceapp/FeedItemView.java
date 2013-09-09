package org.androidtown.sns.faceapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FeedItemView extends LinearLayout {

	/**
	 * Icon
	 */
	private ImageView mIcon;

	/**
	 * TextView 01
	 */
	private TextView mText01;

	/**
	 * TextView 02
	 */
	private TextView mText02;

	/**
	 * TextView 03
	 */
	private TextView mText03;

	public FeedItemView(Context context) {
		super(context);

		// Layout Inflation
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.listitem, this, true);

		// Set Icon
		mIcon = (ImageView) findViewById(R.id.iconItem);

		// Set Text 01
		mText01 = (TextView) findViewById(R.id.dataItem01);

		// Set Text 02
		mText02 = (TextView) findViewById(R.id.dataItem02);

		// Set Text 03
		mText03 = (TextView) findViewById(R.id.dataItem03);

	}

	/**
	 * set Text
	 *
	 * @param index
	 * @param data
	 */
	public void setText(int index, String data) {
		if (index == 0) {
			mText01.setText(data);
		} else if (index == 1) {
			mText02.setText(data);
		} else if (index == 2) {
			mText03.setText(data);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * set Icon
	 *
	 * @param icon
	 */
	public void setIcon(Drawable icon) {
		mIcon.setImageDrawable(icon);
	}

	public void setIcon(Bitmap bitmap) {
		mIcon.setImageBitmap(bitmap);
	}

}
