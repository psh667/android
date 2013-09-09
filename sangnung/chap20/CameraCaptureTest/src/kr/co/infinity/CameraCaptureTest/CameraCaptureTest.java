package kr.co.infinity.CameraCaptureTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class CameraCaptureTest extends Activity {
	private static final int CAMERA_CAPTURE = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		Button captureButton = (Button) findViewById(R.id.capture);
		Button processButton = (Button) findViewById(R.id.process);
		captureButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,

						Uri.fromFile(new File("/sdcard/image.jpg")));
				startActivityForResult(i, CAMERA_CAPTURE);
			}
		});
		processButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Bitmap captureBmp = null;

				File file = new File("/sdcard/image.jpg");
				try {
					captureBmp = Images.Media.getBitmap(getContentResolver(),
							Uri.fromFile(file));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				int width = captureBmp.getWidth();
				int height = captureBmp.getHeight();
				Bitmap tmpBmp = captureBmp.copy(Bitmap.Config.ARGB_8888, true);
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						int value = captureBmp.getPixel(x, y);
						if (value < 0xff808080)
							tmpBmp.setPixel(x, y, 0xff000000);
						else
							tmpBmp.setPixel(x, y, 0xffffffff);
					}
				}
				ImageView imgView = (ImageView) findViewById(R.id.imageview);
				imgView.setImageBitmap(tmpBmp);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		Bitmap captureBmp = null;

		if (resultCode == RESULT_OK && requestCode == CAMERA_CAPTURE) {
			File file = new File("/sdcard/image.jpg");
			try {
				captureBmp = Images.Media.getBitmap(getContentResolver(),
						Uri.fromFile(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			ImageView imgView = (ImageView) findViewById(R.id.imageview);
			imgView.setImageBitmap(captureBmp);
		}
	}
}