package andexam.ver4_1.c22_graphic;

import andexam.ver4_1.*;
import java.util.*;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.Shader.*;
import android.os.*;
import android.view.*;

public class ReDraw1 extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}

	class MyView extends View {
		ArrayList<Vertex> Picture;
		public MyView(Context context) {
			super(context);
			Picture = new ArrayList<Vertex>();
		}		

		public void onDraw(Canvas canvas) {
			Paint Pnt = new Paint();

			// 배경 그림
			LinearGradient lshader = new LinearGradient(0,0,480,0,
					Color.DKGRAY, Color.LTGRAY, TileMode.REPEAT); 
			Pnt.setShader(lshader);
			canvas.drawRect(0,0,getWidth(),getHeight(),Pnt);
			Pnt.setShader(null);

			// 배경 도형 그림
			Pnt.setStyle(Paint.Style.STROKE);
			Pnt.setStrokeWidth(2);
			Pnt.setColor(0x80ff0000);
			for (int x = 0; x < 7 ; x++) {
				for (int y = 0; y < 7 ; y++) {
					Path path = new Path();
					path.moveTo(x*70, y*70);
					for (int dis = 32; dis > 1; dis--) {
						switch (dis % 4) {
						case 0:path.rLineTo(dis*2, 0);break;
						case 3:path.rLineTo(0, dis*2);break;
						case 2:path.rLineTo(-dis*2, 0);break;
						case 1:path.rLineTo(0, -dis*2);break;
						}
					}
					canvas.drawPath(path, Pnt);
				}
			}

			// 전경 도형 그림
			Pnt.setColor(Color.BLACK);
			Pnt.setStrokeWidth(3);
			Pnt.setAntiAlias(true);
			for (int i=0;i<Picture.size();i++) {
				if (Picture.get(i).Draw) {
					canvas.drawLine(Picture.get(i-1).x, Picture.get(i-1).y, 
							Picture.get(i).x, Picture.get(i).y, Pnt);
				}
			}
		}

		public boolean onTouchEvent(MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				Picture.add(new Vertex(event.getX(), event.getY(), false));
				return true;
			}
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				Picture.add(new Vertex(event.getX(), event.getY(), true));
				invalidate();
				return true;
			}
			return false;
		}
	}
	
	public class Vertex {
		Vertex(float ax, float ay, boolean ad) {
			x = ax;
			y = ay;
			Draw = ad;
		}
		float x;
		float y;
		boolean Draw;
	}
}
