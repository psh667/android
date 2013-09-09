package andexam.ver4_1.c22_graphic;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class Scale extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}

	protected class MyView extends View {
		public MyView(Context context) {
			super(context);
		}

		public void onDraw(Canvas canvas) {
			Paint Pnt = new Paint();
			Pnt.setAntiAlias(true);

			Pnt.setColor(Color.YELLOW);
			canvas.drawRect(50,50,150,150,Pnt);

			Pnt.setColor(Color.GREEN);
			canvas.save();
			canvas.scale(1.5f,1.5f);
			canvas.drawRect(50,50,150,150,Pnt);

			Pnt.setColor(Color.BLUE);
			canvas.restore();
			canvas.save();
			canvas.scale(0.5f,0.5f);
			canvas.drawRect(50,50,150,150,Pnt);

			Pnt.setColor(Color.RED);
			canvas.restore();
			canvas.scale(0.5f,0.5f,100,100);
			canvas.drawRect(50,50,150,150,Pnt);
		}
	}
}