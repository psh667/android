package andexam.ver4_1.c20_fragment;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class SaveFragmentState extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.savefragmentstate);
	}

	public static class CounterFragment extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container, 
				Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.counterfragment, container, false);

			Button btnIncrease = (Button)root.findViewById(R.id.btnincrease);
			final TextView textCounter=(TextView)root.findViewById(R.id.txtcounter);

			if (savedInstanceState != null) {
				textCounter.setText(Integer.toString(savedInstanceState.getInt("counter")));
			}

			btnIncrease.setOnClickListener(new Button.OnClickListener() {
				public void onClick(View v) {
					int count = Integer.parseInt(textCounter.getText().toString());
					textCounter.setText(Integer.toString(count + 1));
				}
			});

			return root;
		}

		public void onSaveInstanceState(Bundle outState) {
			super.onSaveInstanceState(outState);

			TextView textCounter=(TextView)getView().findViewById(R.id.txtcounter);
			int a = Integer.parseInt(textCounter.getText().toString());
			outState.putInt("counter", a);
		}
	}
}