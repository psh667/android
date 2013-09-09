package andexam.ver4_1.c23_animation;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;

public class ActAnim extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actanim);

		overridePendingTransition(0, 0);
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btnquit:
			finish();
			overridePendingTransition(0, R.anim.exitanim);
			break;
		}
	}
}

