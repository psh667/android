package andexam.ver4_1.c20_fragment;

import android.app.*;
import android.os.*;
import android.view.*;
import andexam.ver4_1.*;

public class DialogFragmentEmbed extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogfragmentembed);
	}
	
	public static class NameGenderFragment extends DialogFragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.namegenderfragment, container, false);
			return root;
		}
	}	
}
