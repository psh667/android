package com.androidside.surfaceviewdemoa2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceViewDemoA2 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MySurfaceView(this));
    }
}

class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback,
        Runnable {
    private SurfaceHolder holder; //서피스홀더

    private Paint paint; //페인트
    private Canvas canvas; //캔버스

    private Thread thread; //스레드
    private boolean isRun; //스레드 실행 여부를 나타내는 플래그

    MyIcon myIcon;

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            myIcon.isHit(touchX, touchY);
        }

        return true;
    }

    @Override
    public void run() {
        myIcon = new MyIcon();

        isRun = true;

        while (isRun) {
            canvas = holder.lockCanvas();

            //배경을 검은 색으로 채우기
            fillBackground(Color.BLACK);

            //아이콘을 자동으로 이동시킨다.
            myIcon.moveAuto(canvas);

            holder.unlockCanvasAndPost(canvas);

            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //배경을 지정된 색으로 칠한다.
    private void fillBackground(int bg) {
        paint.setColor(bg);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), paint);
    }

    class MyIcon {
        //방향 상수
        private static final int LEFT = 0;
        private static final int UP = 1;
        private static final int RIGHT = 2;
        private static final int DOWN = 3;

        private Bitmap icon;
        private Bitmap icon2;
        private int androidWidth; //아이콘 너비
        private int androidHeight; //아이콘 높이

        int x = 0; //아이콘의 좌측 상단의 X 좌표
        int y = 0; //아이콘의 좌측 상단의 Y

        int maxX; //아이콘이 이동할 수 있는 최대 X 좌표
        int maxY; //아이콘이 이동할 수 있는 최대 Y 좌표

        int speed = 80; //아이콘 이동 너비

        boolean isHit = false; //아이콘 터치 여부

        //두 개의 아이콘과 아이콘의 너비와 높이를 구한다. 그리고 화면 크기를 구한다.
        MyIcon() {
            //기본 안드로이드 모양
            icon = BitmapFactory
                    .decodeResource(getResources(), R.drawable.img1);
            //터치시 변경될 안드로이드 모양
            icon2 = BitmapFactory.decodeResource(getResources(),
                    R.drawable.img2);

            androidWidth = icon.getWidth(); //아이콘 너비
            androidHeight = icon.getHeight(); //아이콘 높이

            maxX = getWidth() - androidWidth; //아이콘이 이동할 수 있는 최대 X 좌표
            maxY = getHeight() - androidHeight; //아이콘이 이동할 수 있는 최대 Y 좌표
        }

        //아이콘 이동 너비(스피드)를 설정할 수 있는 생성자
        MyIcon(int speed) {
            this();
            this.speed = speed;
        }

        //터치된 곳이 아이콘의 범위 내라면 isHit 변수를 true로 설정한다. 
        public void isHit(int touchX, int touchY) {
            if (touchX >= x && touchX <= x + androidWidth 
                    && touchY >= y && touchY <= y + androidHeight) {
                isHit = true;
            }
        }

        //아이콘을 임의의 방향으로 이동시킨다. 이때 화면을 넘어갈 때는 화면 끝으로 이동시킨다.
        public void setNextPosition() {
            int direction = (int) (Math.random() * 4);

            if (direction == LEFT) {
                x -= speed;
            }

            if (direction == RIGHT) {
                x += speed;
            }

            if (direction == UP) {
                y -= speed;
            }
            if (direction == DOWN) {
                y += speed;
            }

            //X 좌표가 왼쪽 화면을 벗어 났다면
            if (x < 0) {
                x = 0;
            }

            //X 좌표가 오른쪽 화면을 벗어 났다면
            if (x > maxX) {
                x = maxX;
            }

            //Y 좌표가 위쪽 화면을 벗어 났다면
            if (y < 0) {
                y = 0;
            }

            //Y 좌표가 아래쪽 화면을 벗어 났다면
            if (y > maxY) {
                y = maxY;
            }
        }

        //아이콘을 자동으로 이동시킨다.
        public void moveAuto(Canvas canvas) {
            setNextPosition();

            //움직이다가 사용자에 의해 터치되었다면 icon2 이미지를 보여주고 
            //그렇지 않으면 icon 이미지를 보여준다.
            if (isHit) {
                canvas.drawBitmap(icon2, x, y, paint);
                isHit = false;
            } else {
                canvas.drawBitmap(icon, x, y, paint);
            }
        }
    }
}