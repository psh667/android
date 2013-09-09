package andexam.ver4_1.c20_fragment;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class TwoFragment1 extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twofragment1);
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