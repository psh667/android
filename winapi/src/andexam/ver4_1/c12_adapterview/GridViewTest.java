package andexam.ver4_1.c12_adapterview;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class GridViewTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gridviewtest);

		GridView grid = (GridView)findViewById(R.id.grid);
		ImageAdapter Adapter = new ImageAdapter(this);
		grid.setAdapter(Adapter);
		
		grid.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(GridViewTest.this, position + "번째 그림 선택",
						Toast.LENGTH_SHORT).show();
			}
		});
	}
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
		return 100;
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
