package pro.android;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.SimpleCursorAdapter;

public class ListDemoActivity extends ListActivity
{
	private SimpleCursorAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// 첫 번째 XML을 콘텐트 뷰로 지정
		setContentView(R.layout.list);

		// 콘텐트 프로바이더 가져옴
		Cursor c = getContentResolver().query(Phone.CONTENT_URI, null, null, null, null);
		
		// 액티비티와 커서의 수명주기를 일치시킴
		startManagingCursor(c);
		String[] cols = new String[]{Phone.DISPLAY_NAME};
		int[] names = new int[]{R.id.row_tv};
		adapter = new SimpleCursorAdapter(this,R.layout.listview,c,cols,names);
		this.setListAdapter(adapter);
	}
}
