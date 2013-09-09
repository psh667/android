package kr.co.company.graphicresource1;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class GraphicResource1Activity extends Activity {
	LinearLayout mLinearLayout;

	protected void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);

	    // 리니어 레이아웃을 생성한다 
	    mLinearLayout = new LinearLayout(this);

	    ShapeDrawable oval = new ShapeDrawable(new OvalShape());
	    oval.setIntrinsicHeight(100);
	    oval.setIntrinsicWidth(100);
	    oval.getPaint().setColor(Color.RED);

	    ImageView i = new ImageView(this);
	    i.setImageDrawable(oval);	// 여기서 설정하면 된다. 
	    // ImageView를 레이아웃에 추가한다
	    mLinearLayout.addView(i);
	    setContentView(mLinearLayout);
	}

}