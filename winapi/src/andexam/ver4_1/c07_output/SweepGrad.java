package andexam.ver4_1.c07_output;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

public class SweepGrad extends Activity {
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

			int[] colors = { Color.RED, Color.GREEN, Color.BLUE, 
					Color.YELLOW, Color.WHITE };
			float[] pos = { 0.0f, 0.1f, 0.6f, 0.9f, 1.0f };

			// 파란색 흰색
			Pnt.setShader(new SweepGradient(80, 80, Color.BLUE, Color.WHITE));
			canvas.drawCircle(80, 80, 70, Pnt);
			
			// 흰색 파란색
			Pnt.setShader(new SweepGradient(230, 80, Color.WHITE, Color.BLUE));
			canvas.drawCircle(230, 80, 70, Pnt);
			
			// 여러 가지 색 균등
			Pnt.setShader(new SweepGradient(80, 230, colors, null));
			canvas.drawCircle(80, 230, 70, Pnt);
			
			// 여러 가지 색 차등
			Pnt.setShader(new SweepGradient(230, 230, colors, pos));
			canvas.drawCircle(230, 230, 70, Pnt);
		}
	}
}
