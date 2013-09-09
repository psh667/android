package pro.android;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;

public class GridDemoActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.gridview);
		GridView  gv  = (GridView)this.findViewById(R.id.dataGrid);

		Cursor   c2  = getContentResolver().query(Phone.CONTENT_URI, null, null, null, null);
		startManagingCursor(c2);

		String[] cols2 = new String[]{Phone.DISPLAY_NAME};
		int[] names2 = new int[]{android.R.id.text1};

		SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1 ,c2,cols2,names2);

		gv.setAdapter(adapter2);

	}
}
