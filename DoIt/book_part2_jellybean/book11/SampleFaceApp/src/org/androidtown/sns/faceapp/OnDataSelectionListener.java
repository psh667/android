package org.androidtown.sns.faceapp;

import android.view.View;
import android.widget.AdapterView;

/**
 * Interface that is called when an item is selected in DataListView
 *
 * @author Mike
 */
public interface OnDataSelectionListener {

	public void onDataSelected(AdapterView parent, View v, int position, long id);

}
