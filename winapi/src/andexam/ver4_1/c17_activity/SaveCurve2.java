package andexam.ver4_1.c17_activity;

import andexam.ver4_1.*;
import java.util.*;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;

class Vertex implements Parcelable {
	Vertex(float ax, float ay, boolean ad) {
		x = ax;
		y = ay;
		Draw = ad;
	}
	float x;
	float y;
	boolean Draw;

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeFloat(x);
		dest.writeFloat(y);
		dest.writeBooleanArray( new boolean[] { Draw } );
	}
	
	public static final Parcelable.Creator<Vertex> CREATOR = new Creator<Vertex>() {
		public Vertex createFromParcel(Parcel source) {
			int x = source.readInt();
			int y = source.readInt();
			boolean[] td = new boolean[1];
			source.readBooleanArray(td);
			return new Vertex(x, y, td[0]);
		}

		public Vertex[] newArray(int size) {
			return new Vertex[size];
		}
		
	};
}

public class SaveCurve2 extends Activity {
	private MyView vw;
	ArrayList<Vertex> arVertex;
	int Count;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		vw = new MyView(this);
		setContentView(vw);
  
		if (savedInstanceState == null) {
			arVertex = new ArrayList<Vertex>();
		} else {
			arVertex = savedInstanceState.getParcelableArrayList("Curve");
		}
	}

	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList("Curve", arVertex);
	}

	protected class MyView extends View {
		Paint mPaint;

		public MyView(Context context) {
			super(context);
			
			// Paint 객체 미리 초기화
			mPaint = new Paint();
			mPaint.setColor(Color.BLACK);
			mPaint.setStrokeWidth(3);
			mPaint.setAntiAlias(true);
		}

		public void onDraw(Canvas canvas) {
			canvas.drawColor(0xffe0e0e0);
			
			// 정점을 순회하면서 선분으로 잇는다.
			for (int i=0;i<arVertex.size();i++) {
				if (arVertex.get(i).Draw) {
					canvas.drawLine(arVertex.get(i-1).x, arVertex.get(i-1).y, 
							arVertex.get(i).x, arVertex.get(i).y, mPaint);
				}
			}
		}

		// 터치 이동시마다 정점들을 추가한다.
		public boolean onTouchEvent(MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				arVertex.add(new Vertex(event.getX(), event.getY(), false));
				return true;
			}
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				arVertex.add(new Vertex(event.getX(), event.getY(), true));
				invalidate();
				return true;
			}
			return false;
		}
	}
}
