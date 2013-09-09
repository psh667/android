package andexam.ver4_1.c33_multimedia;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.widget.*;

public class ImageGridFull extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ImageView imageView = new ImageView(this);
		setContentView(imageView);

		Intent intent = getIntent();
		String path = intent.getStringExtra("path");
		try {
			Bitmap bm = BitmapFactory.decodeFile(path);
			imageView.setImageBitmap(bm);
		}
		catch (OutOfMemoryError e) {
			Toast.makeText(ImageGridFull.this,"이미지가 너무 큽니다.",
					Toast.LENGTH_SHORT).show();
		}
	}
}
