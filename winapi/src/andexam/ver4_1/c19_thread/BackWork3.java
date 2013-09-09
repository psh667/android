package andexam.ver4_1.c19_thread;

import andexam.ver4_1.*;
import andexam.ver4_1.c19_thread.BackWork2.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class BackWork3 extends Activity {
	TextView mResult;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.backwork);

		mResult = (TextView)findViewById(R.id.result);
	}

	public void mOnClick(View v) {
		int start = 0, end = 100;
		int result;

		WaitDlg dlg = new WaitDlg(BackWork3.this, "WaitTest", "Now calculating");
		dlg.start();

		result = Accumulate(start, end);
		mResult.setText("" + result);

		WaitDlg.stop(dlg);
	}

	int Accumulate(int start, int end) {
		int sum = 0;
		for (int i = start; i <= end; i++) {
			sum += i;
			try { Thread.sleep(20); } catch (InterruptedException e) {;}
		}
		return sum;
	}
}

// 대기 대화상자 스레드. 백그라운드에서 애니메이션만 보여준다.
/* Thread로부터 상속
class WaitDlg extends Thread {
	Context mContext;
	String mTitle;
	String mMsg;
	ProgressDialog mProgress;
	Looper mLoop;
	
	WaitDlg(Context context, String title, String msg) {
		mContext = context;
		mTitle = title;
		mMsg = msg;
		
		setDaemon(true);
	}
	
	public void run() {
		Looper.prepare();
		mProgress = new ProgressDialog(mContext);
		mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgress.setTitle(mTitle);
		mProgress.setMessage(mMsg);
		mProgress.setCancelable(false);
		mProgress.show();

		mLoop = Looper.myLooper();
		Looper.loop();
	}
	
	// 스레드 외부에서 종료를 위해 호출된다.
	static void stop(WaitDlg dlg) {
		dlg.mProgress.dismiss();
		// 대화상자가 사라지기 전까지 대기해 줘야 함
		try { Thread.sleep(100); } catch (InterruptedException e) {;}
		// 메시지 루프 종료해야 함
		dlg.mLoop.quit();
	}
}
//*/

//* HendlerThread로부터 상속
class WaitDlg extends HandlerThread {
	Context mContext;
	String mTitle;
	String mMsg;
	ProgressDialog mProgress;
	
	WaitDlg(Context context, String title, String msg) {
		super("waitdlg");
		mContext = context;
		mTitle = title;
		mMsg = msg;
		
		setDaemon(true);
	}
	
	protected void onLooperPrepared() {
		mProgress = new ProgressDialog(mContext);
		mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgress.setTitle(mTitle);
		mProgress.setMessage(mMsg);
		mProgress.setCancelable(false);
		mProgress.show();
	}
	
	// 스레드 외부에서 종료를 위해 호출된다.
	static void stop(WaitDlg dlg) {
		dlg.mProgress.dismiss();
		// 대화상자가 사라지기 전까지 대기해 줘야 함
		try { Thread.sleep(100); } catch (InterruptedException e) {;}
		// 메시지 루프 종료해야 함
		dlg.getLooper().quit();
	}
}
//*/
