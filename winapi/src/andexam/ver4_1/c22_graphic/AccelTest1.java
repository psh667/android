package andexam.ver4_1.c22_graphic;

import java.util.*;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class AccelTest1 extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AccelTestView vw = new AccelTestView(this);
		setContentView(vw);
	}
}

class AccelTestView extends View {
	Random Rnd = new Random();
	int Radius = 1;
	public AccelTestView(Context context) {
		super(context);
	}

	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.GRAY);
		Paint Pnt = new Paint();
		Pnt.setAntiAlias(true);
		Pnt.setTextSize(30);
		
		if (canvas.isHardwareAccelerated()) {
			canvas.drawText("Hardware Accel", 30, 30, Pnt);
		} else {
			canvas.drawText("Software Draw", 30, 30, Pnt);
		}

		Pnt.setStyle(Paint.Style.STROKE);
		Pnt.setStrokeWidth(3);
		Radius += 3;
		if (Radius > 255) Radius = 1;
		for (int i=1;i<Radius;i+=3) {
			Pnt.setColor(Color.rgb(i,i,i));
			canvas.drawCircle(240, 400, i, Pnt);
		}
		invalidate();
	}
}
