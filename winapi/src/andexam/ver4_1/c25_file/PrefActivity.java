package andexam.ver4_1.c25_file;

import andexam.ver4_1.*;
import android.os.*;
import android.preference.*;

public class PrefActivity extends PreferenceActivity {
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.layout.prefactivity);
	}
}