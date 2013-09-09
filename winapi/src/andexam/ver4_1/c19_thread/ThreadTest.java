package andexam.ver4_1.c19_thread;

import andexam.ver4_1.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

//* Thread 객체 사용
public class ThreadTest extends Activity {
	int mMainValue = 0;
	int mBackValue = 0;
	TextView mMainText;
	TextView mBackText;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.threadtest);

		mMainText = (TextView)findViewById(R.id.mainvalue);
		mBackText = (TextView)findViewById(R.id.backvalue);
		
		BackThread thread = new BackThread();
		thread.setDaemon(true);
		thread.start();
	}
	
	public void mOnClick(View v) {
		mMainValue++;
		mMainText.setText("MainValue : " + mMainValue);
		mBackText.setText("BackValue : " + mBackValue);
	}
	
	class BackThread extends Thread {
		public void run() {
			while (true) {
				mBackValue++;
				//mBackText.setText("BackValue : " + mBackValue);
				try { Thread.sleep(1000); } catch (InterruptedException e) {;}
			}
		}
	}
}
//*/

/* Runnable 객체 사용하기
public class ThreadTest extends Activity {
	int mMainValue = 0;
	int mBackValue = 0;
	TextView mMainText;
	TextView mBackText;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.threadtest);

		mMainText = (TextView)findViewById(R.id.mainvalue);
		mBackText = (TextView)findViewById(R.id.backvalue);
		
		BackRunnable runnable = new BackRunnable();
		Thread thread = new Thread(runnable);
		thread.setDaemon(true);
		thread.start();
	}
	
	public void mOnClick(View v) {
		mMainValue++;
		mMainText.setText("MainValue : " + mMainValue);
		mBackText.setText("BackValue : " + mBackValue);
	}

	class BackRunnable implements Runnable {
		public void run() {
			while (true) {
				mBackValue++;
				try { Thread.sleep(1000); } catch (InterruptedException e) {;}
			}
		}
	}
}
//*/


