package org.androidtown.ui.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SampleListFragment extends ListFragment {
	private int index = 0;
	private ListItemSelectedListener selectedListener;

	public void onListItemClick(ListView l, View v, int position, long id) {
		index = position;
		selectedListener.onListItemSelected(position);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(ArrayAdapter.createFromResource(getActivity(),
				R.array.image_titles, android.R.layout.simple_list_item_1));
		if (savedInstanceState != null) {
			index = savedInstanceState.getInt("index", 0);
			selectedListener.onListItemSelected(index);
		}
	}

	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("index", index);
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			selectedListener = (ListItemSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement ListItemSelectedListener in Activity");
		}
	}

	public interface ListItemSelectedListener {
		public void onListItemSelected(int index);
	}
}
