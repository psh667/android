package andexam.ver4_1.c25_file;

import andexam.ver4_1.*;
import java.io.*;
import java.util.*;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class FileExplorer extends Activity {
	String mCurrent;
	String mRoot;
	TextView mCurrentTxt;
	ListView mFileList;
	ArrayAdapter<String> mAdapter;
	ArrayList<String> arFiles;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fileexplorer);

		mCurrentTxt = (TextView)findViewById(R.id.current);
		mFileList = (ListView)findViewById(R.id.filelist);

		arFiles = new ArrayList<String>();
		mRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
		mCurrent = mRoot;

		mAdapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_1, arFiles);
		mFileList.setAdapter(mAdapter);
		mFileList.setOnItemClickListener(mItemClickListener);

		refreshFiles();
	}

	AdapterView.OnItemClickListener mItemClickListener = 
			new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			String Name = arFiles.get(position);
			if (Name.startsWith("[") && Name.endsWith("]")) {
				Name = Name.substring(1, Name.length()-1);
			}
			String Path = mCurrent + "/" + Name;
			File f = new File(Path);
			if (f.isDirectory()) {
				mCurrent = Path;
				refreshFiles();
			} else {
				Toast.makeText(FileExplorer.this, arFiles.get(position), 
						Toast.LENGTH_SHORT).show();
			}
		}
	}; 

	public void mOnClick(View v) {
		switch (v.getId()) {
		case R.id.btnroot:
			if (mCurrent.compareTo(mRoot) != 0) {
				mCurrent = mRoot;
				refreshFiles();
			}
			break;
		case R.id.btnup:
			if (mCurrent.compareTo(mRoot) != 0) {
				int end = mCurrent.lastIndexOf("/");
				String uppath = mCurrent.substring(0, end);
				mCurrent = uppath;
				refreshFiles();
			}
			break;
		}
	}

	void refreshFiles() {
		mCurrentTxt.setText(mCurrent);
		arFiles.clear();
		File current = new File(mCurrent);
		String[] files = current.list();
		if (files != null) {
			for (int i = 0; i < files.length;i++) {
				String Path = mCurrent + "/" + files[i];
				String Name = "";
				File f = new File(Path);
				if (f.isDirectory()) {
					Name = "[" + files[i] + "]";
				} else {
					Name = files[i];
				}

				arFiles.add(Name);
			}
		}
		mAdapter.notifyDataSetChanged();
	}
}
