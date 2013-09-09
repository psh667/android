package ex.FileTest01;

import java.io.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class FileTest01 extends Activity {
	String FILENAME = "test.txt";
	EditText edit;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		edit = (EditText) findViewById(R.id.EditText01);
		Button readButton = (Button) findViewById(R.id.read);
		readButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					FileInputStream fis = openFileInput(FILENAME);
					byte[] buffer = new byte[fis.available()];
					fis.read(buffer);
					edit.setText(new String(buffer));
					fis.close();
				} catch (IOException e) {
				}
			}
		});
		Button writeButton = (Button) findViewById(R.id.write);
		writeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				try {
					FileOutputStream fos = openFileOutput(FILENAME,
							Context.MODE_PRIVATE);
					fos.write(edit.getText().toString().getBytes());
					fos.close();
				} catch (IOException e) {
				}
			}
		});
	}
}