package kr.co.infinity.SelectImageTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class SelectImageTest extends Activity {
	final static int SELECT_IMAGE = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Button selectButton = (Button) findViewById(R.id.select);
		selectButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				Intent intent = new Intent(Intent.ACTION_PICK, uri);
				startActivityForResult(intent, SELECT_IMAGE);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		Bitmap bitmap = null;
		if (resultCode == RESULT_OK && requestCode == SELECT_IMAGE) {
			Uri image = intent.getData();
			try {
				bitmap = Images.Media.getBitmap(getContentResolver(),
						image);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			ImageView imgView = (ImageView) findViewById(R.id.imageview);
			imgView.setImageBitmap(bitmap);
		}
	}
}