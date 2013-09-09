package kr.co.infinity.GraphicCode;

import android.R.color;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GraphicCode extends Activity {
	LinearLayout mLinearLayout;

	protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);

	  // 리니어 레이아웃을 생성한다. 
	  mLinearLayout = new LinearLayout(this);

	  float [] array = new float [] { 20, 20, 20, 20, 20, 20, 20, 20};
	  ShapeDrawable rect = new ShapeDrawable(new RoundRectShape(array , null, null));
	  rect.setIntrinsicHeight(100);
	  rect.setIntrinsicWidth(200);
	  rect.getPaint().setColor(Color.BLUE);

	  ImageView i = new ImageView(this);
	  i.setImageDrawable(rect);	// 여기서 바로 설정하면 된다. 

	  // ImageView를 레이아웃에 추가한다.
	  mLinearLayout.addView(i);
	  setContentView(mLinearLayout);

	}

}