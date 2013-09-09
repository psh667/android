package andexam.ver4_1.c07_output;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class OffScreen extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyView vw = new MyView(this);
		setContentView(vw);
	}

	class MyView extends View {
		public MyView(Context context) {
			super(context);
		}

		public void onDraw(Canvas canvas) {
			canvas.drawColor(Color.LTGRAY);
			Paint Pnt = new Paint();
			
			Bitmap BackBit = Bitmap.createBitmap(300,200,Bitmap.Config.ARGB_8888);
			Canvas offscreen = new Canvas(BackBit);
			offscreen.drawColor(Color.GREEN);
			Pnt.setColor(Color.RED);
			for (int x = 0;x < 300;x += 10) {
				offscreen.drawLine(x,0,300-x,200,Pnt);
			}

			canvas.drawBitmap(BackBit,10,10,null);
		}
	}
}
