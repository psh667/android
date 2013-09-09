package kr.co.company.chap15lab;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Chap15LabActivity extends Activity implements OnClickListener {
	private String filename = "sample.txt";
	private String filepath = "MyFile";
	File ExtFile;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ContextWrapper contextWrapper = new ContextWrapper(
				getApplicationContext());
		File directory = contextWrapper.getDir(filepath, Context.MODE_PRIVATE);
		Button save = (Button) findViewById(R.id.save);
		save.setOnClickListener(this);
		Button get = (Button) findViewById(R.id.get);
		get.setOnClickListener(this);
		if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
			save.setEnabled(false);
		} else {
			ExtFile = new File(getExternalFilesDir(filepath), filename);
		}
	}

	public void onClick(View v) {
		EditText myInputText = (EditText) findViewById(R.id.myInputText);
		String myData = "";
		switch (v.getId()) {
		case R.id.save:
			try {
				FileOutputStream fos = new FileOutputStream(ExtFile);
				fos.write(myInputText.getText().toString().getBytes());
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			myInputText.setText("");
			break;
		case R.id.get:
			try {
				FileInputStream fis = new FileInputStream(ExtFile);
				DataInputStream in = new DataInputStream(fis);
				BufferedReader br = new BufferedReader(
						new InputStreamReader(in));
				String strLine;
				while ((strLine = br.readLine()) != null) {
					myData = myData + strLine;
				}
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			myInputText.setText(myData);
			break;
		}
	}

	private static boolean isExternalStorageReadOnly() {
		String extStorageState = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
			return true;
		}
		return false;
	}

	private static boolean isExternalStorageAvailable() {
		String extStorageState = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
			return true;
		}
		return false;
	}
}
