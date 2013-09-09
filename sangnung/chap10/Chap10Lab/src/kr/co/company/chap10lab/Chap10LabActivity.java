package kr.co.company.chap10lab;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Chap10LabActivity extends ListActivity {
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		String[] values = new String[] { "Sunday", "Monday", "Tuesday",
				"Wednesday", "Thursday", "Friday", "Saturday" };
		// Use your own layout
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.item, R.id.label, values);
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String item = (String) getListAdapter().getItem(position);
		Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
	}
}