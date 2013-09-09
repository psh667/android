package com.androidside.surfaceviewdemoa3;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceViewDemoA3 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MySurfaceView(this));
    }
}

//화면에 그려질 원에 대한 정보를 가지고 있는 클래스
class Circle {    
    float x;
    float y;
    int radius;
    
    Paint paint;
    
    Circle(float x, float y, int radius, int a, int r, int g, int b) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        
        paint = new Paint();
        paint.setARGB(a, r, g, b); //색상 지정  
        paint.setStyle(Paint.Style.STROKE); //테두리를 그린다.
        paint.setStrokeWidth(10); //테두리 너비
    }
    
    //화면에 원을 그린다.
    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, radius, paint);
    }
}

class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder holder; //서피스홀더

    private ArrayList<Circle> circleList = new ArrayList<Circle>();
    
    private Paint paint; //페인트
    private Canvas canvas; //캔버스

    private Thread thread; //스레드
    private boolean isRun; //스레드 실행 여부를 나타내는 플래그

    public MySurfaceView(Context context) {
        super(context);

        //서피스뷰를 접근하고 제어하는 서피스홀더 객체를 얻는다.
        holder = getHolder();
        holder.addCallback(this);

        //페이트 객체를 얻는다.
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        isRun = false;
    }

    //터치가 되거나 터치된 손가락이 움직일 때마다 위치 정보를 추가한다.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            addCircle(x, y);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            addCircle(x, y);
        }
        

        return true;
    }
    
    //원을 그릴 위치를 저장한다.
    private void addCircle(float x, float y) {        
        int radius = (int) (Math.random() * 30); //원의 반지름
        
        int a = (int) (Math.random() * 255); //투명도
        int r = (int) (Math.random() * 255); //적색
        int g = (int) (Math.random() * 255); //녹색
        int b = (int) (Math.random() * 255); //청색
        
        
        Circle circle = new Circle(x, y, radius, a, r, g, b);
        circleList.add(circle);
    }
    
    //추가된 모든 원을 화면에 그린다.
    private void drawAllCircle(Canvas canvas) {
        if (circleList == null) return;
        
        for (int i = 0; i < circleList.size(); i++) {
            circleList.get(i).draw(canvas);
        }
    }

    @Override
    public void run() {
        isRun = true;

        while (isRun) {
            canvas = holder.lockCanvas();

            //배경을 검은 색으로 채우기
            fillBackground(Color.BLACK);

            //추가된 모든 원을 그린다.
            drawAllCircle(canvas);
            
            holder.unlockCanvasAndPost(canvas);
        }
    }

    //배경을 지정된 색으로 칠한다.
    private void fillBackground(int bg) {
        paint.setColor(bg);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), paint);
    }
}