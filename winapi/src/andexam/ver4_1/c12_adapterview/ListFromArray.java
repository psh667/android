package andexam.ver4_1.c12_adapterview;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.widget.*;

public class ListFromArray extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewtest);

		ArrayAdapter<CharSequence> Adapter;
		Adapter = ArrayAdapter.createFromResource(this, R.array.country,
				android.R.layout.simple_list_item_1);

		ListView list = (ListView)findViewById(R.id.list);
		list.setAdapter(Adapter);
	}
}
