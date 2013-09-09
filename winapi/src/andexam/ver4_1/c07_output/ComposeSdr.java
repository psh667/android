package andexam.ver4_1.c07_output;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.Shader.TileMode;
import android.os.*;
import android.view.*;

public class ComposeSdr extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}

	class MyView extends View {
		public MyView(Context context) {
			super(context);
		}

		public void onDraw(Canvas canvas) {
			Paint Pnt = new Paint();
			Pnt.setAntiAlias(true);

			Bitmap clover = BitmapFactory.decodeResource(getContext().getResources(), 
					R.drawable.clover);

			ComposeShader comp = new ComposeShader(
					new BitmapShader(clover, TileMode.REPEAT, TileMode.REPEAT),
					new LinearGradient(0,0,320,0, 0x0, Color.BLACK, TileMode.REPEAT), 
					new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
			Pnt.setShader(comp);
			canvas.drawRect(0, 0, 320, 200, Pnt);
		}
	}
}
