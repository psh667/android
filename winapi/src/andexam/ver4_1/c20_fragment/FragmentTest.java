package andexam.ver4_1.c20_fragment;

import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

//* 1.전개하기
public class FragmentTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragmenttest);
	}

	public static class CounterFragment extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container, 
				Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.counterfragment, container, false);

			Button btnIncrease = (Button)root.findViewById(R.id.btnincrease);
			final TextView textCounter=(TextView)root.findViewById(R.id.txtcounter);
			btnIncrease.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					int count = Integer.parseInt(textCounter.getText().toString());
					textCounter.setText(Integer.toString(count + 1));
				}
			});

			return root;
		}
	}
}
//*/
  
/* 2.직접 생성하기
public class FragmentTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragmenttest);
	}

	public static class CounterFragment extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container, 
				Bundle savedInstanceState) {
			LinearLayout root = new LinearLayout(getActivity());
			root.setOrientation(LinearLayout.VERTICAL);
			root.setBackgroundColor(Color.YELLOW);
			root.setGravity(Gravity.CENTER_HORIZONTAL);

			final TextView textCounter = new TextView(getActivity());
			textCounter.setText("0");
			textCounter.setTextColor(Color.RED);
			textCounter.setTextSize(40);

			Button btnIncrease = new Button(getActivity());
			btnIncrease.setText("Increase");

			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT); 
			root.addView(textCounter, param);
			root.addView(btnIncrease, param);

			btnIncrease.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					int count = Integer.parseInt(textCounter.getText().toString());
					textCounter.setText(Integer.toString(count + 1));
				}
			});

			return root;
		}
	}
}
//*/

/* 3.액티비티의 차일드 검색하기 - 다운됨
public class FragmentTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragmenttest);
	}

	public static class CounterFragment extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container, 
				Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.counterfragment, container, false);

			Button btnIncrease = (Button)getActivity().findViewById(R.id.btnincrease);
			final TextView textCounter=(TextView)getActivity().findViewById(R.id.txtcounter);
			btnIncrease.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					int count = Integer.parseInt(textCounter.getText().toString());
					textCounter.setText(Integer.toString(count + 1));
				}
			});

			return root;
		}
	}
}
//*/

/* 4.onActivityCreated에서 getActivity로 검색하여 핸들러 설치하기
public class FragmentTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragmenttest);
	}

	public static class CounterFragment extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container, 
				Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.counterfragment, container, false);
			return root;
		}

		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			Button btnIncrease = (Button)getActivity().findViewById(R.id.btnincrease);
			final TextView textCounter=(TextView)getActivity().findViewById(R.id.txtcounter);
			btnIncrease.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					int count = Integer.parseInt(textCounter.getText().toString());
					textCounter.setText(Integer.toString(count + 1));
				}
			});
		}
	}
}
//*/

/* 5.onActivityCreated에서 getView로 검색하여 핸들러 설치하기
public class FragmentTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragmenttest);
	}

	public static class CounterFragment extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container, 
				Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.counterfragment, container, false);
			return root;
		}

		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			Button btnIncrease = (Button)getView().findViewById(R.id.btnincrease);
			final TextView textCounter=(TextView)getView().findViewById(R.id.txtcounter);
			btnIncrease.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					int count = Integer.parseInt(textCounter.getText().toString());
					textCounter.setText(Integer.toString(count + 1));
				}
			});
		}
	}
}
//*/

/* 6.액티비티가 프래그먼트의 핸들러 설치하기
public class FragmentTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragmenttest);

		Button btnIncrease = (Button)findViewById(R.id.btnincrease);
		final TextView textCounter=(TextView)findViewById(R.id.txtcounter);
		btnIncrease.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				int count = Integer.parseInt(textCounter.getText().toString());
				textCounter.setText(Integer.toString(count + 1));
			}
		});
	}

	public static class CounterFragment extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container, 
				Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.counterfragment, container, false);
			return root;
		}
	}
}
//*/
