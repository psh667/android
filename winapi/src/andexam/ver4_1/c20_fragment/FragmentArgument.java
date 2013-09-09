package andexam.ver4_1.c20_fragment;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class FragmentArgument extends Activity {
	EditText mStartNum;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragmentargument);

		mStartNum = (EditText)findViewById(R.id.startnum);
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btnadd:
			FragmentManager fm = getFragmentManager();
			FragmentTransaction tr = fm.beginTransaction();
			int start = Integer.parseInt(mStartNum.getText().toString());
			CounterFragment cf = CounterFragment.newInstance(start);
			tr.add(R.id.frame, cf, "counter");
			tr.commit();
			break;
		}
	}

	//* 아규먼트에 저장하기
	public static class CounterFragment extends Fragment {
		public static CounterFragment newInstance(int start) {
			CounterFragment cf = new CounterFragment();

			Bundle args = new Bundle();
			args.putInt("start", start);
			cf.setArguments(args);

			return cf;
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container, 
				Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.counterfragment, container, false);

			Button btnIncrease = (Button)root.findViewById(R.id.btnincrease);
			final TextView textCounter=(TextView)root.findViewById(R.id.txtcounter);

			int start = 0;
			Bundle args = getArguments();
			if (args != null) {
				start = args.getInt("start");
			}
			textCounter.setText(Integer.toString(start));

			btnIncrease.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					int count = Integer.parseInt(textCounter.getText().toString());
					textCounter.setText(Integer.toString(count + 1));
				}
			});

			return root;
		}
	}
	//*/

	/* 필드에 저장하기
	public static class CounterFragment extends Fragment {
		int mStart;

		public static CounterFragment newInstance(int start) {
			CounterFragment cf = new CounterFragment();
			cf.mStart = start;
			return cf;
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container, 
				Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.counterfragment, container, false);

			final Button btnIncrease = (Button)root.findViewById(R.id.btnincrease);
			final TextView textCounter=(TextView)root.findViewById(R.id.txtcounter);
			textCounter.setText(Integer.toString(mStart));

			btnIncrease.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					int count = Integer.parseInt(textCounter.getText().toString());
					textCounter.setText(Integer.toString(count + 1));
				}
			});

			return root;
		}
	}
	//*/	
}