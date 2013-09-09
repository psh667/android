package kr.co.company.graphicfont;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;


class MyView extends View {
    public MyView(Context context) {
        super(context);
        setBackgroundColor(Color.YELLOW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
    	Typeface myFont;
    	Paint paint = new Paint();
    	paint.setAntiAlias(true);
    	myFont = Typeface.createFromAsset(getContext().getAssets(), 
    		"animeace2_ital.ttf");
    	paint.setTypeface(myFont);
    	paint.setTextSize(25);
    	canvas.drawText("This is a New Font!!!", 10, 100, paint);
    	canvas.drawText("Have Fun!!!", 10, 200, paint);
    }
}

public class GraphicFont extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView w = new MyView(this);
        setContentView(w);
    }
}
