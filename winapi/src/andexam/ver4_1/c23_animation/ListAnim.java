package andexam.ver4_1.c23_animation;

import andexam.ver4_1.*;
import java.util.*;

import android.app.*;
import android.os.*;
import android.view.animation.*;
import android.widget.*;

public class ListAnim extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listanim);

		ArrayAdapter<CharSequence> Adapter;
		Adapter = ArrayAdapter.createFromResource(this, R.array.listanim,
				android.R.layout.simple_list_item_1);
		ListView list = (ListView)findViewById(R.id.list);
		list.setAdapter(Adapter);
		
		AnimationSet set = new AnimationSet(true);
		Animation rtl = new TranslateAnimation(
		    Animation.RELATIVE_TO_SELF, 1.0f,Animation.RELATIVE_TO_SELF, 0.0f,
		    Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF, 0.0f
		);
		rtl.setDuration(1000);
		set.addAnimation(rtl);

		Animation alpha = new AlphaAnimation(0.0f, 1.0f);
		alpha.setDuration(1000);
		set.addAnimation(alpha);

		LayoutAnimationController controller =
	        new LayoutAnimationController(set, 0.5f);
		list.setLayoutAnimation(controller);
	}
}
