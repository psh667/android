package org.androidtown.ui.pager;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Layout for Page
 *
 * @author Mike
 */
public class PersonPage extends LinearLayout {
	Context mContext;

	TextView nameText;
	Button callButton;
	ImageView iconImage;

	public static final int CALL_NUMBER = 1001;

	public PersonPage(Context context) {
		super(context);

		init(context);
	}

	public PersonPage(Context context, AttributeSet attrs) {
		super(context, attrs);

		init(context);
	}

	private void init(Context context) {
		mContext = context;

		// inflate XML layout
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.person_page, this, true);

		iconImage = (ImageView) findViewById(R.id.iconImage);
		nameText = (TextView) findViewById(R.id.nameText);
		callButton = (Button) findViewById(R.id.callButton);
		callButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String callNumber = (String) callButton.getTag();

				Toast.makeText(mContext, "전화걸기 버튼 클릭됨 : " + callNumber, Toast.LENGTH_LONG).show();

			}
		});

	}

	public void setImage(int resId) {
		iconImage.setImageResource(resId);
	}

	public void setCallNumber(String number) {
		callButton.setTag(number);
	}

	public String getNameText() {
		return nameText.getText().toString();
	}

	public void setNameText(String nameStr) {
		nameText.setText(nameStr);
	}

}

