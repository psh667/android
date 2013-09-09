package andexam.ver4_1.c21_actionbar;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class ActionModeTest extends Activity {
	ActionMode mActionMode;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actionmodetest);
	}
	
	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btnaction:
			if (mActionMode == null) {
				mActionMode = startActionMode(mActionCallback);
				mActionMode.setTitle("Test");
			}
			break;
		}
	}
	
	ActionMode.Callback mActionCallback = new ActionMode.Callback() {
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.actionmodetestmenu, menu);
			return true;
		}

		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}

		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.add:
				Toast.makeText(ActionModeTest.this, "add", 0).show();
				break;
			case R.id.edit:
				Toast.makeText(ActionModeTest.this, "edit", 0).show();
				break;
			case R.id.search:
				Toast.makeText(ActionModeTest.this, "search", 0).show();
				break;
			}
			return false;
		}

		public void onDestroyActionMode(ActionMode mode) {
			mActionMode = null;
		}
	};
}