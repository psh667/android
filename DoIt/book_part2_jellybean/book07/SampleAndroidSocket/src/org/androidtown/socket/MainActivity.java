package org.androidtown.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	EditText input01;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        input01 = (EditText) findViewById(R.id.input01);
        
        // 버튼 이벤트 처리
        Button button01 = (Button) findViewById(R.id.button01);
        button01.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String addr = input01.getText().toString().trim();
				
				ConnectThread thread = new ConnectThread(addr);
				thread.start();
			}
        });
        
    }

    
    /**
     * 소켓 연결할 스레드 정의
     */
    class ConnectThread extends Thread {
    	String hostname;
    	
    	public ConnectThread(String addr) {
    		hostname = addr;
    	}
    	
    	public void run() {

    		try {
    			int port = 11001;

    			Socket sock = new Socket(hostname, port);
    			ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());
    			outstream.writeObject("Hello AndroidTown on Android");
    			outstream.flush();

    			ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());
    			String obj = (String) instream.readObject();
    			
    			Log.d("MainActivity", "서버에서 받은 메시지 : " + obj);
    			
    			sock.close();

    		} catch(Exception ex) {
    			ex.printStackTrace();
    		}

    	}
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
