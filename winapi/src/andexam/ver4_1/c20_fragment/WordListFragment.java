package andexam.ver4_1.c20_fragment;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class WordListFragment extends ListFragment {
	OnWordChangedListener mHost;

	public interface OnWordChangedListener {
		public void onWordChanged(int index);
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			mHost = (OnWordChangedListener)activity;
		} catch (ClassCastException e) {
			throw new ClassCastException("activity must implement OnWordChanged");
		}
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_activated_1, 
				ListFragmentTest.WORDS));
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		l.setItemChecked(position, true);
		mHost.onWordChanged(position);
	}
}

