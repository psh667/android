package com.androidside.subway.common;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.androidside.subway.R;

public class ExitInfoAdapter extends ArrayAdapter<StationExitInfo> {
	private ArrayList<StationExitInfo> items;
	Context ctx;

	public ExitInfoAdapter(Context context, int textViewResourceId,	ArrayList<StationExitInfo> items) {
		super(context, textViewResourceId, items);
		ctx = context;

		// TODO Auto-generated constructor stub
		this.setItems(items);
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public void setItems(ArrayList<StationExitInfo> items) {
		this.items = items;
	}

	/**
	 * @return the items
	 */
	public ArrayList<StationExitInfo> getItems() {
		return items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = convertView;
		LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (v == null) {
			v = vi.inflate(R.layout.exitinforow, null);
		}

		final StationExitInfo station = items.get(position);
		if (station != null) {
			TextView exitnumberText = (TextView) v
					.findViewById(R.id.exitnumbertext);
			TextView exitText = (TextView) v.findViewById(R.id.exittext);

			if (exitnumberText != null) {
				exitnumberText.setText(station.getStrExitNumber());
			}

			if (exitText != null) {
				exitText.setText(station.getStrExitInfo());
			}
		}

		return v;
	}
}