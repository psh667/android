package andexam.ver4_1.c33_multimedia;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.database.*;
import android.graphics.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.provider.MediaStore.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;

public class ImageGrid extends Activity {
	GridView mGrid;
	Cursor mCursor;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imagegrid);
		
		mGrid = (GridView) findViewById(R.id.imagegrid);

		ContentResolver cr = getContentResolver();
		mCursor = cr.query(Images.Thumbnails.EXTERNAL_CONTENT_URI, 
				null, null, null, null);
		ImageAdapter Adapter = new ImageAdapter(this);
		mGrid.setAdapter(Adapter);
		
		mGrid.setOnItemClickListener(mItemClickListener);
	}
	
	AdapterView.OnItemClickListener mItemClickListener = 
		new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, 
				int position, long id) {
			mCursor.moveToPosition(position);
			String path = mCursor.getString(mCursor.getColumnIndex(
					Images.ImageColumns.DATA));
			Intent intent = new Intent(ImageGrid.this, 
					ImageGridFull.class);
			intent.putExtra("path", path);
			startActivity(intent);
		}
	};

	class ImageAdapter extends BaseAdapter {
		private Context mContext;

		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return mCursor.getCount();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) {
				imageView = new ImageView(mContext);
			} else {
				imageView = (ImageView) convertView;
			}
			mCursor.moveToPosition(position);
			Uri uri = Uri.withAppendedPath(MediaStore.Images.Thumbnails.
					EXTERNAL_CONTENT_URI, 
					mCursor.getString(mCursor.getColumnIndex(MediaStore.
							Images.Thumbnails._ID)));
			imageView.setImageURI(uri);
			imageView.setAdjustViewBounds(true);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

			return imageView;
		}
	}
}
