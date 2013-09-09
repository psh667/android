package ex.FileTest02;

import java.io.*;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class FileTest02 extends Activity {
	String FILENAME = "test.txt";
	EditText edit;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)==false){
	    	  Toast.makeText(this,"외부 스토리지 실패",Toast.LENGTH_SHORT).show();
		}
		edit = (EditText) findViewById(R.id.EditText01);
		Button readButton = (Button) findViewById(R.id.read);
		readButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				File file = new File(getExternalFilesDir(null), FILENAME);
					try {
						InputStream is;
						is = new FileInputStream(file);
						byte[] buffer = new byte[is.available()];
						is.read(buffer);
						edit.setText(new String(buffer));
						is.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
		Button writeButton = (Button) findViewById(R.id.write);
		writeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				File file = new File(getExternalFilesDir(null), FILENAME);
			    try {
			        OutputStream os = new FileOutputStream(file);
					os.write(edit.getText().toString().getBytes());
			        os.close();
			    } catch (Exception e) {
					e.printStackTrace();
			    }
			}
		});
	}
}