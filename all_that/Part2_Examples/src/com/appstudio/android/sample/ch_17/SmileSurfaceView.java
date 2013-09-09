package com.appstudio.android.sample.ch_17;

import com.appstudio.android.sample.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class SmileSurfaceView extends SurfaceView implements Callback
{
    SmileThread sThread;
    SurfaceHolder mHolder;

    public SmileSurfaceView( Context context)
    {
        super( context );
        SurfaceHolder smileHolder = getHolder( );
        smileHolder.addCallback( this );
        mHolder = smileHolder;
        sThread = new SmileThread( smileHolder, context );
        setFocusable( true );
    }

    @Override
    public void surfaceChanged( SurfaceHolder holder, int format, int width, int height )
    {
       

    }

    @Override
    public void surfaceCreated( SurfaceHolder holder )
    {
        sThread.start( );

    }

    @Override
    public void surfaceDestroyed( SurfaceHolder holder )
    {
        sThread.interrupt( );

    }
    
    
    
    @Override
    public boolean onTouchEvent( MotionEvent event )
    {
        if(event.getAction( ) == MotionEvent.ACTION_DOWN){
            Log.i( "TAG", "ACTION_DOWN" );
        }else if(event.getAction( ) == MotionEvent.ACTION_UP){
            Log.i( "TAG", "ACTION_UP" );
        }else if(event.getAction( ) == MotionEvent.ACTION_MOVE){
            sThread.x = (int)event.getX( );
            sThread.y = (int)event.getY( );
            Log.i( "TAG", "ACTION_MOVE" );
        }
        return super.onTouchEvent( event );
    }



    class SmileThread extends Thread {
        SurfaceHolder threadHolder;
        int x;
        int y;
        int smilex;
        int smiley;

        Bitmap smileBitmap;
        
        public SmileThread( SurfaceHolder mHolder , Context mContext)
        {
            super( );
            this.threadHolder = mHolder;
            
            smileBitmap = BitmapFactory.decodeResource( getResources(), R.drawable.smile );
            
            smilex = smileBitmap.getWidth( ) / 2;
            smiley = smileBitmap.getHeight( ) / 2;
       
            x = 100;
            y = 100;
            setClickable( true );
        }
        
        public void run(){
            Canvas canvas = null;
            while(true){
                canvas = threadHolder.lockCanvas( );
                try{
                    synchronized (threadHolder)
                    {
                        if(canvas!=null){
                            canvas.drawColor( Color.BLACK );
                            canvas.drawBitmap( smileBitmap, x-smilex, y-smiley, null );
                        }
                        
                    }
                }finally {
                    if(canvas != null){
                        threadHolder.unlockCanvasAndPost( canvas );
                    }
                }
            }
        }
        
  
        
        
    }

}
