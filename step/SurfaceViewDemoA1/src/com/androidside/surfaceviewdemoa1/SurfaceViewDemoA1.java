package com.androidside.surfaceviewdemoa1;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceViewDemoA1 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MySurfaceView(this));
    }
}

class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder holder; //서피스홀더

    private Paint paint; //페인트
    private Canvas canvas; //캔버스

    private Thread thread; //스레드
    private boolean isRun; //스레드 실행 여부를 나타내는 플래그

    public MySurfaceView(Context context) {
        super(context);

        holder = getHolder();
        holder.addCallback(this);

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
    public void run() {
        isRun = true;

        while (isRun) {
            canvas = holder.lockCanvas();

            //배경을 검은 색으로 채우기
            fillBackground(Color.BLACK);

            //인덱스 숫자를 상단 오른쪽에 흰색으로 출력하기
            printStr(getTime(), Color.WHITE);

            holder.unlockCanvasAndPost(canvas);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //배경을 지정된 색으로 칠하는 메소드
    private void fillBackground(int bg) {
        paint.setColor(bg);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), paint);
    }

    //화면에 문자열을 출력하는 메소드
    private void printStr(String str, int color) {
        paint.setColor(color);
        paint.setTextSize(20);
        canvas.drawText(str, getWidth() - paint.measureText(str), 20, paint);
    }

    //현재 시간/분/초 반환하는 메소드
    private String getTime() {
        Calendar now = Calendar.getInstance();
        int h = now.get(Calendar.HOUR_OF_DAY);

        int m = now.get(Calendar.MINUTE);
        int s = now.get(Calendar.SECOND);

        return h + ":" + m + ":" + s;
    }

}