package andexam.ver4_1.c33_multimedia;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.database.*;
import android.graphics.*;
import android.os.*;
import android.provider.MediaStore.*;
import android.view.*;
import android.widget.*;

public class ImageViewer extends Activity {
	ImageView mImage;
	Cursor mCursor;
	
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageviewer);
		
		ListView list = (ListView)findViewById(R.id.list);
		mImage = (ImageView)findViewById(R.id.image);
		
		ContentResolver cr = getContentResolver();
		mCursor = cr.query(Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
		SimpleCursorAdapter Adapter = new SimpleCursorAdapter(this, 
				android.R.layout.simple_list_item_1,
				mCursor, new String[] { MediaColumns.DISPLAY_NAME }, 
				new int[] { android.R.id.text1});
		list.setAdapter(Adapter);
		list.setOnItemClickListener(mItemClickListener);
		startManagingCursor(mCursor);
	}

	AdapterView.OnItemClickListener mItemClickListener = 
		new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, 
				int position, long id) {
			mCursor.moveToPosition(position);
			String path = mCursor.getString(mCursor.getColumnIndex(
					Images.ImageColumns.DATA));
			try {
				BitmapFactory.Options opt = new BitmapFactory.Options();
				opt.inSampleSize = 2;
				Bitmap bm = BitmapFactory.decodeFile(path, opt);
				mImage.setImageBitmap(bm);
			}
			catch (OutOfMemoryError e) {
				Toast.makeText(ImageViewer.this,"이미지가 너무 큽니다.",0).show();
			}
		}
	};
}
