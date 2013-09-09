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
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SimpleGet extends Activity {

    private static final String CLASSTAG = SimpleGet.class.getSimpleName();

    private EditText getInput;
    private TextView getOutput;
    private Button getButton;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.simple_get);

        this.getInput = (EditText) findViewById(R.id.get_input);
        this.getOutput = (TextView) findViewById(R.id.get_output);
        this.getButton = (Button) findViewById(R.id.get_button);

        this.getButton.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                getOutput.setText("");
                String output = getHttpResponse(getInput.getText().toString());
                if (output != null) {
                    getOutput.setText(output);
                }
            }
        });
    };

    /**
     * Perform an HTTP GET with HttpUrlConnection.
     * 
     * @param location
     * @return
     */
    private String getHttpResponse(String location) {
        String result = null;
        URL url = null;
        Log.d(Constants.LOGTAG, " " + SimpleGet.CLASSTAG + " location = " + location);

        try {
            url = new URL(location);
            Log.d(Constants.LOGTAG, " " + SimpleGet.CLASSTAG + " url = " + url);
        } catch (MalformedURLException e) {
            Log.e(Constants.LOGTAG, " " + SimpleGet.CLASSTAG + " " + e.getMessage());
        }

        if (url != null) {
            try {
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                String inputLine;

                int lineCount = 0; // limit the lines for the example
                while ((lineCount < 10) && ((inputLine = in.readLine()) != null)) {
                    lineCount++;
                    Log.v(Constants.LOGTAG, " " + SimpleGet.CLASSTAG + " inputLine = " + inputLine);
                    result += "\n" + inputLine;
                }

                in.close();
                urlConn.disconnect();

            } catch (IOException e) {
                Log.e(Constants.LOGTAG, " " + SimpleGet.CLASSTAG + " " + e.getMessage());
            }
        } else {
            Log.e(Constants.LOGTAG, " " + SimpleGet.CLASSTAG + " url NULL");
        }
        return result;
    }
}
