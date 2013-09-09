package andexam.ver4_1.c20_fragment;

import android.app.*;
import android.os.*;
import android.view.*;
import andexam.ver4_1.*;

public class DialogFragmentTest extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogfragmenttest);
	}

	public void mOnClick(View v) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction tr = fm.beginTransaction();
		Fragment prev = fm.findFragmentByTag("dialog");
		if (prev != null) {
			tr.remove(prev);
		}
		NameGenderFragment dialog = new NameGenderFragment();
		dialog.show(tr, "dialog");
		//dialog.show(fm, "dialog");
	}

	public static class NameGenderFragment extends DialogFragment {
		//* 레이아웃 전개해서 리턴
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.namegenderfragment, container, false);
			return root;
		}
		//*/

		/* Dialog 객체 생성해서 리턴
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			Dialog dialog = new Dialog(getActivity());
			dialog.setContentView(R.layout.namegenderfragment);

			return dialog;
		}
		//*/
	}
}
