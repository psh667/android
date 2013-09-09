package pro.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LayoutAnimationActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_layout);
		setupListView();
	}
	private void setupListView()
	{
		String[] listItems = new String[] {
		"亲格 1", "亲格 2", "亲格 3",
		"亲格 4", "亲格 5", "亲格 6",
		};
		ArrayAdapter listItemAdapter =
		new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
		ListView lv = (ListView)this.findViewById(R.id.list_view_id);
		lv.setAdapter(listItemAdapter);
	}
}