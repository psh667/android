package andexam.ver4_1.c23_animation;

import andexam.ver4_1.*;
import java.util.*;

import android.app.*;
import android.os.*;
import android.view.animation.*;
import android.widget.*;

public class ListAnim2 extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listanim2);

		ArrayAdapter<CharSequence> Adapter;
		Adapter = ArrayAdapter.createFromResource(this, R.array.listanim,
				android.R.layout.simple_list_item_1);
		ListView list = (ListView)findViewById(R.id.list);
		list.setAdapter(Adapter);
	}
}
