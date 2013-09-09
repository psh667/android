/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appstudio.android.sample.ch_29_2_1;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.SystemClock;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

/*
 * 회전하는 큐브를 그리는 월페이퍼
 */
public class CubeWallpaper extends WallpaperService {
  // 특정 주기별로 배경화면을 그리기 위해 핸들러를 사용한다.
  private final Handler mHandler = new Handler();

  @Override
  public void onCreate() {
    super.onCreate();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  @Override
  public Engine onCreateEngine() {
    return new CubeEngine();
  }

  class CubeEngine extends Engine {
    private final Paint mPaint = new Paint();
    private float mOffset;
    private float mTouchX = -1;
    private float mTouchY = -1;
    private long mStartTime;
    private float mCenterX;
    private float mCenterY;

    private final Runnable mDrawCube = new Runnable() {
      public void run() {
      drawFrame();
      }
    };
    private boolean mVisible;

    CubeEngine() {
      // 큐브를 그리기 위한 페인트 객체를 준비한다.
      final Paint paint = mPaint;
      paint.setColor(0xffffffff);
      paint.setAntiAlias(true);
      paint.setStrokeWidth(2);
      paint.setStrokeCap(Paint.Cap.ROUND);
      paint.setStyle(Paint.Style.STROKE);
      mStartTime = SystemClock.elapsedRealtime();
    }

    @Override
    public void onCreate(SurfaceHolder surfaceHolder) {
      super.onCreate(surfaceHolder);
      // 디폴트는 터치 이벤트를 받지 못하는 것인데, 
      // 이를 변경하여 터치 이벤트가 가능토록 한다.
      setTouchEventsEnabled(true);
    }

    @Override
    public void onDestroy() {
      super.onDestroy();
      // 주기별로 화면을 그리는 콜백을 해제한다.
      mHandler.removeCallbacks(mDrawCube);
    }

    @Override
    public void onVisibilityChanged(boolean visible) {
      mVisible = visible;
      if (visible) {
        drawFrame();
      } else {
        mHandler.removeCallbacks(mDrawCube);
      }
    }

    @Override
    public void onSurfaceChanged(SurfaceHolder holder, 
                         int format, int width, int height) {
      super.onSurfaceChanged(holder, format, width, height);
      // 서피스의 중앙을 저장한다. 
      mCenterX = width/2.0f;
      mCenterY = height/2.0f;
      drawFrame();
    }

    @Override
    public void onSurfaceCreated(SurfaceHolder holder) {
      super.onSurfaceCreated(holder);
    }

    @Override
    public void onSurfaceDestroyed(SurfaceHolder holder) {
      super.onSurfaceDestroyed(holder);
      mVisible = false;
      mHandler.removeCallbacks(mDrawCube);
    }

    @Override
    public void onOffsetsChanged(float xOffset,float yOffset,
        float xStep, float yStep, int xPixels, int yPixels) {
      mOffset = xOffset;
      drawFrame();
    }

    /*
     * 터치한 위치를 저장한다. 후에 이 위치를 중심으로 원 그리기를 수행한다.
     */
    @Override
    public void onTouchEvent(MotionEvent event) {
      Log.d("cube", "ontuoch");
      if (event.getAction() == MotionEvent.ACTION_MOVE) {
        mTouchX = event.getX();
        mTouchY = event.getY();
      } else {
        mTouchX = -1;
        mTouchY = -1;
      }
      super.onTouchEvent(event);
    }

    /*
     * Handler에 등록한 Runnable에 의해 반복적으로 그리기가 수행된다.
     */
    void drawFrame() {
      final SurfaceHolder holder = getSurfaceHolder();
      Canvas c = null;
      try {
        c = holder.lockCanvas();
        if (c != null) {
          drawCube(c);
          drawTouchPoint(c);
        }
      } finally {
        if (c != null) holder.unlockCanvasAndPost(c);
      }

      // 핸들러를 위한 메시지 큐를 초기화하고 새롭게 postDelayed한다.
      mHandler.removeCallbacks(mDrawCube);
      if (mVisible) {
        mHandler.postDelayed(mDrawCube, 1000 / 25);
      }
    }

    /*
     * Draw a wireframe cube by drawing 12 3 dimensional 
     * lines between adjacent corners of the cube
     */
    void drawCube(Canvas c) {
      c.save();
      c.translate(mCenterX, mCenterY);
      c.drawColor(0xff000000);
      drawLine(c, -400, -400, -400,  400, -400, -400);
      drawLine(c,  400, -400, -400,  400,  400, -400);
      drawLine(c,  400,  400, -400, -400,  400, -400);
      drawLine(c, -400,  400, -400, -400, -400, -400);

      drawLine(c, -400, -400,  400,  400, -400,  400);
      drawLine(c,  400, -400,  400,  400,  400,  400);
      drawLine(c,  400,  400,  400, -400,  400,  400);
      drawLine(c, -400,  400,  400, -400, -400,  400);

      drawLine(c, -400, -400,  400, -400, -400, -400);
      drawLine(c,  400, -400,  400,  400, -400, -400);
      drawLine(c,  400,  400,  400,  400,  400, -400);
      drawLine(c, -400,  400,  400, -400,  400, -400);
      c.restore();
    }

    /*
     * Draw a 3 dimensional line on to the screen
     */
    void drawLine(Canvas c, int x1, int y1, int z1, int x2,
                                            int y2, int z2) {
      long now = SystemClock.elapsedRealtime();
      float xrot = ((float)(now - mStartTime)) / 1000;
      float yrot = (0.5f - mOffset) * 2.0f;
      float zrot = 0;
      // 3D transformations
      // rotation around X-axis
      float newy1 = (float)(Math.sin(xrot) * z1 + 
                                        Math.cos(xrot) * y1);
      float newy2 = (float)(Math.sin(xrot) * z2 + 
                                        Math.cos(xrot) * y2);
      float newz1 = (float)(Math.cos(xrot) * z1 - 
                                        Math.sin(xrot) * y1);
      float newz2 = (float)(Math.cos(xrot) * z2 - 
                                        Math.sin(xrot) * y2);

      // rotation around Y-axis
      float newx1 = (float)(Math.sin(yrot) * newz1 + 
                                        Math.cos(yrot) * x1);
      float newx2 = (float)(Math.sin(yrot) * newz2 + 
                                        Math.cos(yrot) * x2);
      newz1 = (float)(Math.cos(yrot) * newz1 - 
                                        Math.sin(yrot) * x1);
      newz2 = (float)(Math.cos(yrot) * newz2 - 
                                        Math.sin(yrot) * x2);

      // 3D-to-2D projection
      float startX = newx1 / (4 - newz1 / 400);
      float startY = newy1 / (4 - newz1 / 400);
      float stopX =  newx2 / (4 - newz2 / 400);
      float stopY =  newy2 / (4 - newz2 / 400);

      c.drawLine(startX, startY, stopX, stopY, mPaint);
    }

    /*
     * Draw a circle around the current touch point, if any.
     */
    void drawTouchPoint(Canvas c) {
      if (mTouchX >=0 && mTouchY >= 0) {
        c.drawCircle(mTouchX, mTouchY, 80, mPaint);
      }
    }

  }
}
