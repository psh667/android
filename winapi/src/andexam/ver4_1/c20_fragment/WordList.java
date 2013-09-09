package andexam.ver4_1.c20_fragment;

import android.app.*;
import android.os.*;
import android.widget.*;
import andexam.ver4_1.*;

public class WordList extends Activity implements 
		WordListFragment.OnWordChangedListener {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wordlist);
	}

	public void onWordChanged(int index) {
		TextView worddesc = (TextView)findViewById(R.id.worddesc);
		worddesc.setText(ListFragmentTest.DESC[index]);
	}
}