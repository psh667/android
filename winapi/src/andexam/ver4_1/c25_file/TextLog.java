package andexam.ver4_1.c25_file;

import java.io.*;
import java.util.*;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;

// 텍스트 로그 작성 유틸리티
// 1.액티비티의 onCreate에서 init(this) 호출하고 속성 설정
// 2.로그를 남길 때 TextLog.o("~") 메서드 호출
// 3.로그 확인시 TextLog.ViewLog() 메서드 호출
public class TextLog {
	static Context mMain;
	static final int LOG_FILE = 1;
	static final int LOG_SYSTEM = 2;
	static int mWhere = LOG_FILE | LOG_SYSTEM;
	// 기록 경로. 디폴트는 SD 루트의 andlog.txt이나 절대 경로로 지정 가능
	// SD 카드가 없는 경우 "/data/data/패키지/files/파일" 경로로 지정할 것
	static String mPath = "";
	static String mTag = "textlog";
	static boolean mAppendTime = false;
	static float mViewTextSize = 6.0f;
	static int mMaxFileSize = 100;			// KB
	static boolean mReverseReport = false;
	static long mStartTime;
	static long mLastTime;

	// mPath는 SD카드의 루트로 기본 초기화한다. SD 카드가 없으면 빈 문자열이다.
	static {
		boolean HaveSD = Environment.getExternalStorageState()
		.equals(Environment.MEDIA_MOUNTED);
		if (HaveSD) {
			String SDPath = Environment.getExternalStorageDirectory()
			.getAbsolutePath();
			mPath = SDPath + "/andlog.txt";
		}
	}

	// 디폴트 설정대로 초기화한다.
	public static void init(Context main) {
		mMain = main;
	
		// 일정 크기 이상이면 로그 파일의 앞부분을 잘라낸다.
		if (mMaxFileSize != 0 && (mWhere & LOG_FILE) != 0) {
			File file = new File(mPath);
			if (file.length() > mMaxFileSize * 1024) {
				String log = "";
				try {
					FileInputStream fis = new FileInputStream(mPath);
					int avail = fis.available();
					byte[] data = new byte[avail];
					while (fis.read(data) != -1) {;}
					fis.close();
					log = new String(data);
				}
				catch (Exception e) {;}
				
				// 앞쪽 90%를 잘라낸다.
				log = log.substring(log.length() * 9 / 10);
				
				try {
					FileOutputStream fos = new FileOutputStream(file);
					fos.write(log.getBytes());
					fos.close();
				} 
				catch (Exception e) {;}
				
			}
		}

		o("---------- start time : " + getNowTime());
	}

	// 로그 파일을 삭제하여 초기화한다.
	public static void reset() {
		if ((mWhere & LOG_FILE) != 0) {
			File file = new File(mPath);
			file.delete();
		}
		o("---------- reset time : " + getNowTime());
	}
	
	static String getNowTime() {
		Calendar calendar = Calendar.getInstance();
		String Time = String.format("%d-%d %d:%d:%d",calendar.get(Calendar.MONTH)+1, 
				calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY),
				calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
		return Time;
	}

	// write string to log.
	public static void o(String text, Object ... args) {
		// 릴리즈에서 로그 기록문을 제거했으면 바로 리턴한다.
		if (mWhere == 0) {
			return;
		}
		
		// 예외의 getMessage가 null을 리턴하는 경우가 있어 널 점검 필요하다.
		if (text == null) {
			return;
		}
		
		if (args.length != 0) {
			text = String.format(text, args);
		}

		if (mAppendTime) {
			Calendar calendar = Calendar.getInstance();
			String Time = String.format("%d:%d:%02d.%04d = ", 
					calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), 
					calendar.get(Calendar.SECOND), calendar.get(Calendar.MILLISECOND));
			text = Time + text;
		}

		if ((mWhere & LOG_FILE) != 0 && mPath.length() != 0) {
			File file = new File(mPath);
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file, true);
				if (fos != null) {
					fos.write(text.getBytes());
					fos.write("\n".getBytes());
				}
			} 
			catch (Exception e) {
				// silent fail
			}
			finally {
				try {
					if(fos != null) fos.close();
				}
				catch (Exception e) { ; }
			}
		}

		if ((mWhere & LOG_SYSTEM) != 0) {
			Log.d(mTag, text);
		}
	}
	
	public static void lapstart(String text) {
		mStartTime = System.currentTimeMillis();
		mLastTime = mStartTime;
		o("St=0000,gap=0000 " + text);
	}
	
	public static void lap(String text) {
		long now = System.currentTimeMillis();
		String sText = String.format("St=%4d,gap=%4d " + text, 
			now - mStartTime, now - mLastTime);
		mLastTime = now;
		o(sText);
	}

	// 로그 파일 보기
	public static void ViewLog() {
		String path;
		int ch;

		StringBuilder Result = new StringBuilder();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(mPath));
			if (in != null) {
				for (;;) {
					ch = in.read();
					if (ch == -1) break;
					Result.append((char)ch);
				}
			}
		}
		catch (Exception e) {
			Result.append("log file not found");
		}
		finally {
			try {
				if(in != null) in.close();
			}
			catch (Exception e) { ; }
		}

		String sResult = Result.toString();
		if (mReverseReport) {
			String[] lines = sResult.split("\n");
			Result.delete(0, Result.length());
			for (int i = lines.length - 1;i >= 0; i--) {
				Result.append(lines[i]);
				Result.append("\n");
			}
			sResult = Result.toString();
		}
		
		ScrollView scroll = new ScrollView(mMain); 
		TextView text = new TextView(mMain);
		text.setTextSize(TypedValue.COMPLEX_UNIT_PT, mViewTextSize);
		text.setTextColor(Color.WHITE);
		text.setText("length = " + sResult.length() + "\n" + sResult);
		scroll.addView(text);

		new AlertDialog.Builder(mMain)
		.setTitle("Log")
		.setView(scroll)
		.setPositiveButton("OK", null)
		.show();        
	}

	public static void addMenu(Menu menu) {
		menu.add(0,101092+1,0,"ViewLog");
		menu.add(0,101092+2,0,"ResetLog");
	}

	public static boolean execMenu(MenuItem item) {
		switch (item.getItemId()) {
		case 101092+1:
			ViewLog();
			return true;
		case 101092+2:
			reset();
			return true;
		}
		return false;
	}
}

//내부 패키지에서 간단하게 호출할 수 있는 래퍼 클래스
//TextLog.o() 대신 lg.o()로 호출 가능하다.
class lg {
	public static void o(String text, Object ... args) {
		TextLog.o(text, args);
	}
}

