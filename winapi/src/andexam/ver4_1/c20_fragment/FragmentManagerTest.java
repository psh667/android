package andexam.ver4_1.c20_fragment;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;
import andexam.ver4_1.c20_fragment.SaveFragmentState.CounterFragment;

public class FragmentManagerTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragmentmanagertest);
	}

	public void mOnClick(View v) {
		FragmentManager fm = getFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.frame);

		switch (v.getId()) {
		case R.id.btnadd:
			if (fragment == null) {
				FragmentTransaction tr = fm.beginTransaction();
				CounterFragment cf = new CounterFragment();
				tr.add(R.id.frame, cf, "counter");
				tr.commit();
				//fm.beginTransaction().add(R.id.frame, cf, "counter").commit();
			} else {
				Toast.makeText(this, "이미 추가되어 있습니다.", 0).show();
			}
			break;
		case R.id.btnremove:
			if (fragment == null) {
				Toast.makeText(this, "프래그먼트가 없습니다.", 0).show();
			} else {
				FragmentTransaction tr = fm.beginTransaction();
				tr.remove(fragment);
				tr.commit();
			}
			break;
		case R.id.btnreplace:
			if (fragment == null) {
				Toast.makeText(this, "프래그먼트가 없습니다.", 0).show();
			} else {
				FragmentTransaction tr = fm.beginTransaction();
				if (fragment.getTag() == "counter") {
					TextFragment tf = new TextFragment();
					tr.replace(R.id.frame, tf, "text");
				} else {
					CounterFragment cf = new CounterFragment();
					tr.replace(R.id.frame, cf, "counter");
				}
				tr.commit();
			}
			break;
		case R.id.btnhideshow:
			if (fragment == null) {
				Toast.makeText(this, "프래그먼트가 없습니다.", 0).show();
			} else {
				FragmentTransaction tr = fm.beginTransaction();
				if (fragment.isHidden()) {
					tr.show(fragment);
				} else {
					tr.hide(fragment);
				}
				tr.commit();
			}
			break;
		}
	}

	public static class TextFragment extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container, 
				Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.textfragment, container, false);
			TextView text = (TextView)root.findViewById(R.id.text);
			text.setSaveEnabled(true);
			return root;
		}
	}
}
