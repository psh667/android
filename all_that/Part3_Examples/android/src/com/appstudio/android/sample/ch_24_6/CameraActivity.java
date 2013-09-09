package com.appstudio.android.sample.ch_24_6;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class CameraActivity extends Activity {
    private static final String TAG = "APPSTUDIO";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    
    private Camera mCamera;
    private CameraPreview mPreview;	
    private MediaRecorder mMediaRecorder;   
    

    private Button mVideoButton;
    private boolean isRecording = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.camera_activity);

        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        
        Button pictureButton = (Button) findViewById(R.id.button_capture_picture);
        pictureButton.setOnClickListener(
            new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get an image from the camera
                mCamera.takePicture(new Camera.ShutterCallback() {
					
					@Override
					public void onShutter() {
						Log.d(TAG, "ShutterCallback");
						
					}
				}, null, mPicture);
            }
        });
        mVideoButton = (Button) findViewById(R.id.button_capture_video); 
        mVideoButton.setOnClickListener(mVideoClicker);
        

    }

    

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();       // if you are using MediaRecorder, release it first
        releaseCamera();              // release the camera immediately on pause event
        Log.d(TAG, "onPause()");
    }

    private void releaseMediaRecorder(){
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();   // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
            mCamera.lock();           // lock camera for later use
        }
    }

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            Log.d("TAG", "releaseCamera()");
            mCamera = null;
        }
    }    
    

    /** 디바이스가 카메라를 지원하는지를 체크한다. */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
    
    /** 카메라 객체를 가져온다. */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); 
        }
        catch (Exception e){
            
        }
        return c; 
    }
    
    /** 카메라 미리보기 */
    public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder mHolder;

        public CameraPreview(Context context) {
            
            super(context);
            Log.d(TAG, "CameraPreview()");

            // Install a SurfaceHolder.Callback so we get notified when the
            // underlying surface is created and destroyed.
            mHolder = getHolder();
            mHolder.addCallback(this);
            // deprecated setting, but required on Android versions prior to 3.0
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            
            
        }

        public void surfaceCreated(SurfaceHolder holder) {
            // The Surface has been created, now tell the camera where to draw the preview.
            try {
        	if(mCamera==null)  {
                    Log.d(TAG, "mCamera is null");
        	    mCamera = getCameraInstance();
        	}

        	Log.d(TAG, "surfaceCreated()");
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                Log.d(TAG, "Error setting camera preview: " + e.getMessage());
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            // empty. Take care of releasing the Camera preview in your activity.
        	Log.d(TAG, "surfaceDestroyed()");
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            
            // If your preview can change or rotate, take care of those events here.
            // Make sure to stop the preview before resizing or reformatting it.
            Log.d(TAG, "surfaceChanged()");
            if (mHolder.getSurface() == null){
              // preview surface does not exist
              return;
            }

            // stop preview before making changes
            try {
                mCamera.stopPreview();
            } catch (Exception e){
              // ignore: tried to stop a non-existent preview
            }

            // set preview size and make any resize, rotate or
            // reformatting changes here

            // start preview with new settings
            try {
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();

            } catch (Exception e){
                Log.d(TAG, "Error starting camera preview: " + e.getMessage());
            }
        }
    }
   
    private PictureCallback mPicture = new PictureCallback() {

	    @Override
	    public void onPictureTaken(byte[] data, final Camera camera) {
	        File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
	        if (pictureFile == null){
	            Log.d(TAG, "Error creating media file, check storage permissions");
	            return;
	        }

	        try {
	            FileOutputStream fos = new FileOutputStream(pictureFile);
	            fos.write(data);
	            fos.close();
//	            broadcastPictureInsert(pictureFile.getPath());
	            scanFile(pictureFile.getPath());
	            Log.d(TAG, "path "+pictureFile.getPath());
	            Log.d(TAG, "path "+pictureFile.toString());
//	            insertPictureMediaStore(pictureFile.getPath());
	        } catch (FileNotFoundException e) {
	            Log.d(TAG, "File not found: " + e.getMessage());
	        } catch (IOException e) {
	            Log.d(TAG, "Error accessing file: " + e.getMessage());
	        }
	        
	        Handler handler = new Handler();
	        handler.postDelayed(new Runnable()  {
			@Override
			public void run() {
				if(mCamera!=null)  {
				    camera.startPreview();
				}
				
			}}, 3000);
	    }
	};
	   
    
    private boolean prepareVideoRecorder(){
	    if(mCamera==null)  {
		mCamera = getCameraInstance();
		Camera.Parameters params = mCamera.getParameters();
		params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
	    }
	    mMediaRecorder = new MediaRecorder();

	    // Step 1: Unlock and set camera to MediaRecorder
	    mCamera.unlock();
	    mMediaRecorder.setCamera(mCamera);

	    

	    // Step 2: Set sources
	    mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);  
	    mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);  

	    mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

	    // Step 4: Set output file
	    mMediaRecorder.setOutputFile(getOutputMediaFile(MEDIA_TYPE_VIDEO).toString());
	    
	    // Step 5: Set the preview output
	    mMediaRecorder.setPreviewDisplay(mPreview.getHolder().getSurface());
	    
	    // Step 6: Prepare configured MediaRecorder
	    try {
		    
	        mMediaRecorder.prepare();
	        
	    } catch (IllegalStateException e) {
	        Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
	        releaseMediaRecorder();
	        return false;
	    } catch (IOException e) {
	        Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
	        releaseMediaRecorder();
	        return false;
	    }
	    return true;
	}    
    
    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
          return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
	Log.d(TAG, "getOutputMediaFile()" + type);
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                  Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }   
    
    private View.OnClickListener mVideoClicker = new View.OnClickListener() {
 	@Override
	public void onClick(View v) {
	    if (isRecording) {
	        // stop recording and release camera
	        mMediaRecorder.stop();  // stop the recording
	        releaseMediaRecorder(); // release the MediaRecorder object
	        mCamera.lock();         // take camera access back from MediaRecorder

	        // inform the user that recording has stopped
	        mVideoButton.setText("동영상");
	        isRecording = false;
	    } else {
	        // initialize video camera
	        if (prepareVideoRecorder()) {
	            // Camera is available and unlocked, MediaRecorder is prepared,
	            // now you can start recording
	            mMediaRecorder.start();

	            // inform the user that recording has started
	            mVideoButton.setText("촬영중");
	            isRecording = true;
	        } else {
	           // prepare didn't work, release the camera
	           releaseMediaRecorder();
	           // inform user
	       }
	   }
 	}
    };
	
    private void insertPictureMediaStore(String path)  {
    	ContentValues values = new ContentValues();
    	values.put(Images.Media.DISPLAY_NAME, "MyAppPicture");
    	values.put(Images.Media.DESCRIPTION, "picture by my app");
    	values.put(Images.Media.MIME_TYPE, "image/jpeg");
    	values.put(Images.Media.DATA, path);
    	getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI, values);
    }
    
    private void broadcastPictureInsert(String path)  {
    	Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
    	Uri uri = Uri.parse("file://"+path);
    	intent.setData(uri);
    	sendBroadcast(intent);
    }    
    
    
    private void scanFile(String path)  {
        MediaScannerConnection.scanFile(this,
                new String[] { path }, null,
                new MediaScannerConnection.OnScanCompletedListener() {
            public void onScanCompleted(String path, Uri uri) {
                Log.d("ExternalStorage", "Scanned " + path + ":");
                Log.d("ExternalStorage", "-> uri=" + uri);
            }
        });    
    }
    
}