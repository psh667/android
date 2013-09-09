package com.msi.manning.chapter10.SimpleCamera;


import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SimpleCamera extends Activity implements SurfaceHolder.Callback
{
    private Camera camera;
    private boolean isPreviewRunning = false;
    private SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
    
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Uri targetResource = Media.EXTERNAL_CONTENT_URI;

    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        Log.e(getClass().getSimpleName(), "onCreate");
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.main);
        surfaceView = (SurfaceView)findViewById(R.id.surface);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuItem item = menu.add(0, 0, 0, "View Pictures");
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(Intent.ACTION_VIEW, targetResource);
                startActivity(intent);
                return true;
            }
        });
        return true;
    }
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
    }
    
   
    Camera.PictureCallback mPictureCallbackRaw = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera c) {
            camera.startPreview();
        }
    };
    

    Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
    	public void onShutter() {
    	}
    };
    

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
    	ImageCaptureCallback camDemo = null;
    	if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
    	try {
    		String filename = timeStampFormat.format(new Date());
    		ContentValues values = new ContentValues();
    		values.put(Media.TITLE, filename);
    		values.put(Media.DESCRIPTION, "Image from Android Emulator");
    		Uri uri = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, values);
    		camDemo = new ImageCaptureCallback( getContentResolver().openOutputStream(uri));
    	} catch(Exception ex ){
    		}
    	}
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event);
        }
 
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            camera.takePicture(mShutterCallback, mPictureCallbackRaw, camDemo);
            return true;
        }

        return false;
    }

    protected void onResume()
    {
        Log.e(getClass().getSimpleName(), "onResume");
        super.onResume();
    }

    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
    }

    protected void onStop()
    {
    	super.onStop();
    }
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h)
    {
        if (isPreviewRunning) {
            camera.stopPreview();
        }
        Camera.Parameters p = camera.getParameters();
        p.setPreviewSize(w, h);
        camera.setParameters(p);
        camera.setPreviewDisplay(holder);
        camera.startPreview();
        isPreviewRunning = true;
    }
    public void surfaceCreated(SurfaceHolder holder)
    {
       camera = Camera.open();
    }

    public void surfaceDestroyed(SurfaceHolder holder)
    {
        camera.stopPreview();
        isPreviewRunning = false;
        camera.release();
    }
}