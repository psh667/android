package andexam.ver4_1.c20_fragment;

import android.app.*;
import android.os.*;
import android.view.*;
import andexam.ver4_1.*;
import andexam.ver4_1.c20_fragment.FragmentArgument.CounterFragment;

public class BackStack extends Activity {
	int mStart = 10;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.backstack);

		if (savedInstanceState != null) {
			mStart = savedInstanceState.getInt("mStart");
		}

		FragmentManager fm = getFragmentManager();
		FragmentTransaction tr = fm.beginTransaction();
		CounterFragment cf = CounterFragment.newInstance(mStart);
		tr.add(R.id.frame, cf).commit();
	}

	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putInt("mStart", mStart);
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btnadd:
			mStart += 10;
			FragmentManager fm = getFragmentManager();
			FragmentTransaction tr = fm.beginTransaction();
			CounterFragment cf = CounterFragment.newInstance(mStart);
			tr.replace(R.id.frame, cf);
			tr.addToBackStack(null);
			tr.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
			tr.commit();
			break;
		}
	}
}
