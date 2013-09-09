package kr.co.company.chap20lab;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Chap20LabActivity extends Activity {
  private final static String TAG = "Camera";
  private Camera camera;
  private int camera_id = 0;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    // do we have a camera?
    if (!getPackageManager()
        .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
      Toast.makeText(this, "카메라가 발견되지 않았읍니다.", Toast.LENGTH_LONG)
          .show();
    } else {
      camera_id = findCamera();
      if (camera_id < 0) {
        Toast.makeText(this, "전면 카메라 없음",
            Toast.LENGTH_LONG).show();
      } else {
        camera = Camera.open(camera_id);
      }
    }
  }

  public void onClick(View view) {
	 if( camera != null ) 
		 camera.takePicture(null, null,  new PhotoSaver(getApplicationContext()));
  }

  private int findCamera() {
    int id = -1;
    int numberOfCameras = Camera.getNumberOfCameras();
    for (int i = 0; i < numberOfCameras; i++) {
      CameraInfo info = new CameraInfo();
      Camera.getCameraInfo(i, info);
      if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
        Log.d(TAG, "카메라 발견");
        id = i;
        break;
      }
    }
    return id;
  }

  @Override
  protected void onPause() {
    if (camera != null) {
      camera.release();
      camera = null;
    }
    super.onPause();
  }

} 