package andexam.ver4_1.c12_adapterview;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

public class ManyItem extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewtest);

		((ListView)findViewById(R.id.list)).setAdapter(new ManyAdapter(this));
	}
}

class ManyAdapter extends BaseAdapter {
	Context maincon;
	LayoutInflater Inflater;
	Toast mToast;

	public ManyAdapter(Context context) {
		maincon = context;
		Inflater = (LayoutInflater)context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return 1000;
	}

	public String getItem(int position) {
		return "" + position;
	}

	public long getItemId(int position) {
		return position;
	}

	// 각 항목의 뷰 생성
	public View getView(int position, View convertView, ViewGroup parent) {
		String log = "position = " + position + ", ";
		if (convertView == null) {
			convertView = Inflater.inflate(android.R.layout.simple_list_item_1, 
					parent, false);
			log += "convertView is null";
		} else {
			log += "convertView is not null";
		}
		Log.d("ManyItem", log);
		
		if (mToast == null) {
			mToast = Toast.makeText(maincon, log, 0);
		} else {
			mToast.setText(log);
		}
		mToast.show();

		TextView txt = (TextView)convertView.findViewById(android.R.id.text1);
		txt.setText("ManyItem ListView : " + position);
		return convertView;
	}
}
