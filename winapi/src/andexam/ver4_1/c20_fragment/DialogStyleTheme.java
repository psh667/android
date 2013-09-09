package andexam.ver4_1.c20_fragment;

import android.app.*;
import android.os.*;
import android.view.*;
import andexam.ver4_1.*;

public class DialogStyleTheme extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogstyletheme);
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			showDialog(DialogFragment.STYLE_NORMAL,0);
			break;
		case R.id.btn2:
			showDialog(DialogFragment.STYLE_NO_TITLE,0);
			break;
		case R.id.btn3:
			showDialog(DialogFragment.STYLE_NO_FRAME,0);
			break;
		case R.id.btn4:
			showDialog(DialogFragment.STYLE_NO_INPUT,0);
			break;
		case R.id.btn5:
			showDialog(DialogFragment.STYLE_NORMAL,android.R.style.Theme_Holo);
			break;
		case R.id.btn6:
			showDialog(DialogFragment.STYLE_NORMAL,android.R.style.Theme_Holo_Light_Dialog);
			break;
		case R.id.btn7:
			showDialog(DialogFragment.STYLE_NORMAL,android.R.style.Theme_Holo_Light);
			break;
		case R.id.btn8:
			showDialog(DialogFragment.STYLE_NORMAL,android.R.style.Theme_Holo_Light_Panel);
			break;
		}
	}

	void showDialog(int style, int theme) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction tr = fm.beginTransaction();
		Fragment prev = fm.findFragmentByTag("dialog");
		if (prev != null) {
			tr.remove(prev);
		}
		tr.addToBackStack(null);

		NameGenderFragment newFragment = NameGenderFragment.newInstance(style, theme);
		newFragment.show(tr, "dialog");
	}

	public static class NameGenderFragment extends DialogFragment {
		static NameGenderFragment newInstance(int style, int theme) {
			NameGenderFragment df = new NameGenderFragment();

			Bundle args = new Bundle();
			args.putInt("style", style);
			args.putInt("theme", theme);
			df.setArguments(args);

			return df;
		}

		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			int style = getArguments().getInt("style");
			int theme = getArguments().getInt("theme");

			setStyle(style, theme);
		}

		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View root = inflater.inflate(R.layout.namegenderfragment, container, false);
			return root;
		}
	}
}
