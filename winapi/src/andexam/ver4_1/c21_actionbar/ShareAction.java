package andexam.ver4_1.c21_actionbar;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import andexam.ver4_1.*;

public class ShareAction extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView text = new TextView(this);
		text.setText("공유 액션 프로바이더를 테스트합니다.");
		setContentView(text);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.shareactionmenu, menu);

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TEXT, "sharing text");

		MenuItem share = menu.findItem(R.id.share);
		ShareActionProvider provider = (ShareActionProvider) share.getActionProvider();
		provider.setShareHistoryFileName(ShareActionProvider.
				DEFAULT_SHARE_HISTORY_FILE_NAME);
		provider.setShareIntent(intent);

		MenuItem sharemenu = menu.findItem(R.id.sharemenu);
		ShareActionProvider providermenu = (ShareActionProvider) share.getActionProvider();
		providermenu.setShareHistoryFileName(ShareActionProvider.
				DEFAULT_SHARE_HISTORY_FILE_NAME);
		providermenu.setShareIntent(intent);

		return true;
	}
}
