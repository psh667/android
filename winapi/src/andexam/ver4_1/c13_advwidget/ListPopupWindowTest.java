package andexam.ver4_1.c13_advwidget;

import andexam.ver4_1.*;
import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class ListPopupWindowTest extends Activity {
	Button mBtn;
	ListPopupWindow mList;
	public String[] Colors = {
			"Red", "Green", "Blue", "Yellow", "Cyan", "Magenta"
	};
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listpopupwindowtest);

		mBtn = (Button)findViewById(R.id.btn);
		mList = new ListPopupWindow(this);
		mList.setWidth(300);
		mList.setHeight(300);
		mList.setAnchorView(mBtn);
		mList.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, Colors));
		mList.setModal(true);

		mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0:	mBtn.setBackgroundColor(Color.RED); break;
				case 1:	mBtn.setBackgroundColor(Color.GREEN); break;
				case 2:	mBtn.setBackgroundColor(Color.BLUE); break;
				case 3:	mBtn.setBackgroundColor(Color.YELLOW); break;
				case 4:	mBtn.setBackgroundColor(Color.CYAN); break;
				case 5:	mBtn.setBackgroundColor(Color.MAGENTA); break;
				}
			}
		});
	}

	public void mOnClick(View v) {
		if (mList.isShowing()) {
			mList.dismiss();
		} else {
			mList.show();
		}
	}	
}
