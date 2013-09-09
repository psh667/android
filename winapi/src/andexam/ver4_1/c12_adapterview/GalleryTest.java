package andexam.ver4_1.c12_adapterview;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

public class GalleryTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallerytest);

		@SuppressWarnings("deprecation")
		Gallery gal = (Gallery) findViewById(R.id.gallery);
		gal.setAdapter(new ImageAdapter2(this));
		gal.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, 
					int position, long id) {
				Toast.makeText(GalleryTest.this, position + 
						"번째 그림 선택", Toast.LENGTH_SHORT).show();
			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}
}

class ImageAdapter2 extends BaseAdapter {
	private Context mContext;
	private int[] mImageIds = {
			R.drawable.ccdam,
			R.drawable.soyang2,
			R.drawable.ududong,
			R.drawable.zipdarigol,
			R.drawable.dongul,
			R.drawable.lotteworld,
	};

	public ImageAdapter2(Context c) {
		mContext = c;
	}

	public int getCount() {
		return mImageIds.length;
	}

	public Object getItem(int position) {
		return mImageIds[position];
	}

	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("deprecation")
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;

		if (convertView == null) {
			imageView = new ImageView(mContext);
		} else {
			imageView = (ImageView)convertView;
		}

		imageView.setImageResource(mImageIds[position]);
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		imageView.setLayoutParams(new Gallery.LayoutParams(136, 88));

		return imageView;
	}
}
