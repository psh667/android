package andexam.ver4_1.c22_graphic;

import android.app.*;
import android.os.*;

public class AccelTest2 extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AccelTestView vw = new AccelTestView(this);
		setContentView(vw);
	}
}

