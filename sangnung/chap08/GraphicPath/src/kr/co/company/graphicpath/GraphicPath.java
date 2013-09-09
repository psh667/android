package kr.co.company.graphicpath;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;

class MyView extends View {
    public MyView(Context context) {
        super(context);
        setBackgroundColor(Color.YELLOW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
    	Path path = new Path();
    	Paint paint = new Paint();

    	paint.setStyle(Paint.Style.STROKE);

    	path.moveTo(20, 100);
    	path.lineTo(100, 200);
    	path.cubicTo(150, 30, 200, 300, 300, 200);

    	paint.setColor(Color.BLUE);
    	canvas.drawPath(path, paint);

    	paint.setStyle(Paint.Style.FILL);
    	paint.setTextSize(50);
    	canvas.drawTextOnPath("This is a test!!", path, 0, 0, paint);
    }
}

public class GraphicPath extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView w = new MyView(this);
        setContentView(w);
    }
}
