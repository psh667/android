package andexam.ver4_1.c20_fragment;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class TwoFragment2 extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twofragment2);
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