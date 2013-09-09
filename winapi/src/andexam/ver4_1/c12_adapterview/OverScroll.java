package andexam.ver4_1.c12_adapterview;

import java.util.*;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class OverScroll extends Activity {
	ArrayList<String> mItem = new ArrayList<String>();
	ArrayAdapter<String> mAdapter;
	ListView mList;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overscroll);

		for (int i = 1001; i <= 1003; i++) {
			mItem.add(Integer.toString(i));
		}

		mAdapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1, mItem);

		mList = (ListView)findViewById(R.id.list);
		mList.setAdapter(mAdapter);
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btn3:
			mItem.clear();
			for (int i = 1001; i <= 1003; i++) {
				mItem.add(Integer.toString(i));
			}
			mAdapter.notifyDataSetChanged();
			break;
		case R.id.btn10:
			mItem.clear();
			for (int i = 1001;i <= 1010; i++) {
				mItem.add(Integer.toString(i));
			}
			mAdapter.notifyDataSetChanged();
			break;
		case R.id.btnalways:
			mList.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
			break;
		case R.id.btnnever:
			mList.setOverScrollMode(View.OVER_SCROLL_NEVER);
			break;
		case R.id.btnifscroll:
			mList.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS );
			break;
		}
	}
}

