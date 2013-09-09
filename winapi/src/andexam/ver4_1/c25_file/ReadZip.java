package andexam.ver4_1.c25_file;

import java.io.*;
import java.util.*;
import java.util.zip.*;

import andexam.ver4_1.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class ReadZip extends Activity {
	TextView mResult;
	String mPath;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.readzip);

		mPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ZipTest.zip";
		mResult = (TextView)findViewById(R.id.result);

		// 애셋의 zip 파일 복사하기
		CopyAsset(this, "ZipTest.zip", "ZipTest.zip");
	}

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btnlist:
			ShowList();
			break;
		case R.id.btna:
			ShowA();
			break;
		case R.id.btnb:
			ShowB();
			break;
		}
	}

	void ShowList() {
		try {
			ZipFile zip = new ZipFile(mPath);
			String s = "";
			s = "size = " + zip.size() + "\n";
			ZipEntry e;

			Enumeration<? extends ZipEntry> ent = zip.entries();
			while (ent.hasMoreElements()) {
				e = (ZipEntry)ent.nextElement();
				s = s + "name = " + e.getName() + 
						" ,size = " + e.getSize() + 
						" ,Compsize = " + e.getCompressedSize() + "\n";
			}

			mResult.setText(s);
		} catch (Exception e) {
			return;
		}
	}

	void ShowA() {
		try {
			ZipFile zip;
			zip = new ZipFile(mPath);

			InputStream is = zip.getInputStream(zip.getEntry("a.txt"));
			ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
			byte[] buffer = new byte[1024];

			int len;
			for (;;) {
				len = is.read(buffer);
				if (len <= 0) break;
				baos.write(buffer, 0, len);
			}
			is.close();
			mResult.setText(baos.toString());
		} catch (IOException e) {
		}
	}

	void ShowB() {
		try {
			ZipInputStream zin = new ZipInputStream(new FileInputStream(mPath));
			for (;;) {
				ZipEntry ze = zin.getNextEntry();
				if (ze == null) break;
				if (ze.getName().equals("subdir/b.txt")) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
					byte[] buffer = new byte[1024];

					int len;
					for (;;) {
						len = zin.read(buffer);
						if (len <= 0) break;
						baos.write(buffer, 0, len);
					}
					mResult.setText(baos.toString());
					break;
				}
			}
			zin.close();
		} catch (Exception e) {
		}
	}
	
	public boolean CopyAsset(Context context, String src, String dest) {
		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return false;
		}
		String root = Environment.getExternalStorageDirectory().getAbsolutePath();

		String destpath = root + "/" + dest;
		File f = new File(destpath);
		if (f.exists()) {
			return true;
		}
		
		AssetManager am = context.getAssets();
		try {
			InputStream is = am.open(src);
			FileOutputStream os = new FileOutputStream(destpath);
			byte buffer[] = new byte[1024];
			for (;;) {
				int read = is.read(buffer);
				if (read <= 0) break;
				os.write(buffer,0,read);
			}
			is.close();
			os.close();
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}	
}
