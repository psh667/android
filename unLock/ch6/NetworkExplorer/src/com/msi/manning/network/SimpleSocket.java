package com.msi.manning.network;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Android direct to Socket example.
 * 
 * For this to work you need a server listening on the IP address and port specified. See the
 * NetworkSocketServer project for an example.
 * 
 * 
 * @author charliecollins
 * 
 */
public class SimpleSocket extends Activity {

    private static final String CLASSTAG = SimpleSocket.class.getSimpleName();

    private EditText ipAddress;
    private EditText port;
    private EditText socketInput;
    private TextView socketOutput;
    private Button socketButton;

    @Override
    public void onCreate(final Bundle icicle) {
        super.onCreate(icicle);
        this.setContentView(R.layout.simple_socket);

        this.ipAddress = (EditText) findViewById(R.id.socket_ip);
        this.port = (EditText) findViewById(R.id.socket_port);
        this.socketInput = (EditText) findViewById(R.id.socket_input);
        this.socketOutput = (TextView) findViewById(R.id.socket_output);
        this.socketButton = (Button) findViewById(R.id.socket_button);

        this.socketButton.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {
                socketOutput.setText("");
                String output = callSocket(ipAddress.getText().toString(), port.getText().toString(), socketInput.getText().toString());
                socketOutput.setText(output);
            }
        });
    }

    private String callSocket(final String ip, final String port, final String socketData) {

        Socket socket = null;
        BufferedWriter writer = null;
        BufferedReader reader = null;
        String output = null;

        try {
            socket = new Socket(ip, Integer.parseInt(port));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // send input terminated with \n
            String input = socketData;
            writer.write(input + "\n", 0, input.length() + 1);
            writer.flush();

            // read back output
            output = reader.readLine();
            Log.d(Constants.LOGTAG, " " + SimpleSocket.CLASSTAG + " output - " + output);

            // send EXIT and close
            writer.write("EXIT\n", 0, 5);
            writer.flush();

        } catch (IOException e) {
            Log.e(Constants.LOGTAG, " " + SimpleSocket.CLASSTAG + " IOException calling socket", e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                // swallow
            }
            try {
                reader.close();
            } catch (IOException e) {
                // swallow
            }
            try {
                socket.close();
            } catch (IOException e) {
                // swallow
            }
        }
        
        return output;
    };
}
