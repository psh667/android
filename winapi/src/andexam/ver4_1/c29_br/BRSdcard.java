package andexam.ver4_1.c29_br;

import andexam.ver4_1.*;
import android.content.*;
import android.widget.*;

public class BRSdcard extends BroadcastReceiver {
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
			Toast.makeText(context, "SDCard가 장착되었습니다.", 
					Toast.LENGTH_LONG).show();
		}
	}
}