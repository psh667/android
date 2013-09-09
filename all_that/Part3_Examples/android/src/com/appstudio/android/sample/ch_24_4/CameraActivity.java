package com.appstudio.android.sample.ch_24_4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
        mCamera = getCameraInstance();
        mPreview = new CameraPreview(this);
        FrameLayout preview = 
            (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        
        Button pictureButton = 
            (Button) findViewById(R.id.button_capture_picture);
        pictureButton.setOnClickListener(
            new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 카메라에서 이미지를 캡쳐한다.
                mCamera.takePicture(
                                 new Camera.ShutterCallback() {
                    @Override
                    public void onShutter() {
                    }
                }, null, mPicture);
            }
        });
        mVideoButton = 
            (Button) findViewById(R.id.button_capture_video); 
        mVideoButton.setOnClickListener(mVideoClicker);
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();       
        releaseCamera();              
    }

    private void releaseMediaRecorder(){
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();   
            mMediaRecorder.release(); 
            mMediaRecorder = null;
            mCamera.lock();           
        }
    }

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();       
            mCamera = null;
        }
    }    

    /** 디바이스가 카메라를 지원하는지를 체크한다. */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(
                               PackageManager.FEATURE_CAMERA)){
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if(!checkCameraHardware(this))  {
            finish();
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
    public class CameraPreview extends SurfaceView 
                            implements SurfaceHolder.Callback {
        private SurfaceHolder mHolder;
        public CameraPreview(Context context) {
            super(context);
            mHolder = getHolder();
            mHolder.addCallback(this);
            mHolder.setType(
                    SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                if(mCamera==null)  {
                    mCamera = getCameraInstance();
                }
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                Log.d(TAG, "Error camera preview: " 
                           + e.getMessage());
            }
        }
        
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        }
        
        @Override
        public void surfaceChanged(SurfaceHolder holder, 
                                    int format, int w, int h) {
            if (mHolder.getSurface() == null){
              // 서피스가 없으면 즉시 리턴
              return;
            }

            // 카메라의 미리 보기 정지
            try {
                mCamera.stopPreview();
            } catch (Exception e){
            }
            // 재설정 후 미리보기 시작
            try {
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();
            } catch (Exception e){
                Log.d(TAG, "Error starting camera preview: " 
                           + e.getMessage());
            }
        }
    }
   
    private PictureCallback mPicture = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, 
                                         final Camera camera) {
            File pictureFile = 
                getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                Log.d(TAG, "파일쓰기 에러, 퍼미션 확인!");
                return;
            }
            try {
                FileOutputStream fos = 
                    new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " 
                           + e.getMessage());
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable()  {
                @Override
                public void run() {
                    if(mCamera!=null)  {
                        camera.startPreview();
                    }
                }
            }, 3000);
        }
    };
    
    private boolean prepareVideoRecorder(){
        if(mCamera==null)  {
            mCamera = getCameraInstance();
            Camera.Parameters params = mCamera.getParameters();
            params.setFocusMode(
                    Camera.Parameters.FOCUS_MODE_AUTO);
        }
        mMediaRecorder = new MediaRecorder();
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);
        mMediaRecorder.setAudioSource(
                MediaRecorder.AudioSource.CAMCORDER);  
        mMediaRecorder.setVideoSource(
                MediaRecorder.VideoSource.CAMERA);  
        mMediaRecorder.setProfile(CamcorderProfile.get(
                CamcorderProfile.QUALITY_HIGH));
        mMediaRecorder.setOutputFile(getOutputMediaFile(
                MEDIA_TYPE_VIDEO).toString());
        mMediaRecorder.setPreviewDisplay(
                mPreview.getHolder().getSurface());
        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            Log.d(TAG, "IllegalStateException preparing " 
                       + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            Log.d(TAG, "IOException preparing MediaRecorder: " 
                       + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;
    }    
    

    // 이미지나 비디오 저장을 위한 파일 생성
    private static File getOutputMediaFile(int type){
        // 안드로이드 권장 디렉토리아래에 MyCameraApp 디렉토리
        File mediaStorageDir = new File(Environment
            .getExternalStoragePublicDirectory(
               Environment.DIRECTORY_PICTURES), "MyCameraApp");
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d(TAG, "failed to create directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat(
                "yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() 
                 + File.separator +"IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() 
                 + File.separator +"VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }
        return mediaFile;
    }   
    
    private View.OnClickListener mVideoClicker = 
                                   new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isRecording) {
                mMediaRecorder.stop();  
                releaseMediaRecorder(); 
                mCamera.lock();         
                mVideoButton.setText("동영상");
                isRecording = false;
            } else {
                if (prepareVideoRecorder()) {
                    mMediaRecorder.start();
                    mVideoButton.setText("촬영중");
                    isRecording = true;
                } else {
                    releaseMediaRecorder();
                }
            }
        }
    };
}