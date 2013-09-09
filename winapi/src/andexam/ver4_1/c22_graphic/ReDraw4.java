package andexam.ver4_1.c22_graphic;

import andexam.ver4_1.*;
import java.util.*;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.Shader.*;
import android.os.*;
import android.view.*;

public class ReDraw4 extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}

	class MyView extends View {
		ArrayList<Vertex> Picture;
		Paint mPntBack;
		Paint mPntPath;
		Paint mPntFore;
		Path[][] arPath;
		Rect mUnion = new Rect();
		int mPass = 0;
		long mLastInv = 0;

		public MyView(Context context) {
			super(context);
			Picture = new ArrayList<Vertex>();

			mPntBack = new Paint();
			LinearGradient lshader = new LinearGradient(0,0,480,0,
					Color.DKGRAY, Color.LTGRAY, TileMode.REPEAT); 
			mPntBack.setShader(lshader);
			
			mPntPath = new Paint();
			mPntPath.setStyle(Paint.Style.STROKE);
			mPntPath.setStrokeWidth(2);
			mPntPath.setColor(0x80ff0000);
			
			mPntFore = new Paint();
			mPntFore.setColor(Color.BLACK);
			mPntFore.setStrokeWidth(3);
			mPntFore.setAntiAlias(true);
			
			arPath = new Path[7][7];
			for (int x = 0; x < 7 ; x++) {
				for (int y = 0; y < 7 ; y++) {
					arPath[x][y] = new Path();
					arPath[x][y].moveTo(x*70, y*70);
					for (int dis = 32; dis > 1; dis--) {
						switch (dis % 4) {
						case 0:arPath[x][y].rLineTo(dis*2, 0);break;
						case 3:arPath[x][y].rLineTo(0, dis*2);break;
						case 2:arPath[x][y].rLineTo(-dis*2, 0);break;
						case 1:arPath[x][y].rLineTo(0, -dis*2);break;
						}
					}
				}
			}
		}		

		public void onDraw(Canvas canvas) {
			canvas.drawRect(0,0,getWidth(),getHeight(),mPntBack);

			for (int x = 0; x < 7 ; x++) {
				for (int y = 0; y < 7 ; y++) {
					canvas.drawPath(arPath[x][y], mPntPath);
				}
			}

			for (int i=0;i<Picture.size();i++) {
				if (Picture.get(i).Draw) {
					canvas.drawLine(Picture.get(i-1).x, Picture.get(i-1).y, 
							Picture.get(i).x, Picture.get(i).y, mPntFore);
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
				Rect rect = GetLineRect(Picture.size()-1);
				
				mUnion.union(rect);
				long Now = System.currentTimeMillis();
				if (mUnion.width() > 16 || mUnion.height() > 16 
						|| Now - mLastInv > 200 || mPass >= 8) {
					invalidate(mUnion);
					mUnion.setEmpty();
					mLastInv = Now;
					mPass = 0;
				} else {
					mPass++;
				}
				return true;
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
				if (mUnion.isEmpty() == false) {
					invalidate(mUnion);
					mUnion.setEmpty();
					mLastInv = System.currentTimeMillis();
					mPass = 0;
				}
				return true;
			}
			return false;
		}
		
		Rect GetLineRect(int idx) {
			Rect rect = new Rect();
			Vertex prev = Picture.get(idx-1);
			Vertex now = Picture.get(idx);
			rect.set((int)Math.min(now.x, prev.x)-2, (int)Math.min(now.y, prev.y)-2,
					(int)Math.max(now.x, prev.x)+3, (int)Math.max(now.y, prev.y)+3);
			return rect;
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
