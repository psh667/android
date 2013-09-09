package kr.co.infinity.ClientTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ClientTest extends Activity {
	private Socket socket;
	BufferedReader in;
	PrintWriter out;
	EditText input;
	Button button;
	TextView output;
	String data;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		try {
			// 소켓 주소를 서버의 ip주소로 변경하여 주세요!
			socket = new Socket("igchun.sch.ac.kr", 5555);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket
						.getInputStream()));

		} catch (IOException e) {
			e.printStackTrace();
		}
		input = (EditText) findViewById(R.id.input);
		button = (Button) findViewById(R.id.button);
		output = (TextView) findViewById(R.id.output);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String data = input.getText().toString();
		       Log.w("NETWORK", " " + data); 
				if (data != null) {
				   out.println(data);
				}
			}
		});

		Thread worker = new Thread() {
			public void run() {
				try {
					while (true) {
						data = in.readLine();
						output.post(new Runnable() {
							public void run() {
								output.setText(data);
							}
						});
					}
				} catch (Exception e) {
				}
			}
		};
		worker.start();

	}

	@Override
	protected void onStop() {
		super.onStop();
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
