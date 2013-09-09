package andexam.ver4_1.c23_animation;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;

public class GridAnim extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridanim);

		GridView grid = (GridView)findViewById(R.id.grid);
		ImageAdapter Adapter = new ImageAdapter(this);
		grid.setAdapter(Adapter);
	}

	class ImageAdapter extends BaseAdapter {
		private Context mContext;

		int[] picture = { 
				R.drawable.ccdam,
				R.drawable.soyang2,
				R.drawable.ududong,
				R.drawable.zipdarigol,
				R.drawable.dongul
		};

		public ImageAdapter(Context c) {
			mContext = c;
		}

		public int getCount() {
			return 30;
		}

		public Object getItem(int position) {
			return picture[position % 5];
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) {
				imageView = new ImageView(mContext);
				imageView.setLayoutParams(new GridView.LayoutParams(80, 60));
				imageView.setAdjustViewBounds(false);
				imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			} else {
				imageView = (ImageView) convertView;
			}

			imageView.setImageResource(picture[position % 5]);

			return imageView;
		}
	}
}

