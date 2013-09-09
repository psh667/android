package com.androidside.filedemob1;

import java.io.IOException;
import java.io.OutputStreamWriter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FileDemoB1 extends Activity implements View.OnClickListener {
    private String FILE_NAME = "text.txt";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button = (Button) findViewById(R.id.filesave);        
        button.setOnClickListener(this);
    }
    
    public void onClick(View v) {
        EditText editText = (EditText) findViewById(R.id.editarea);
        
        OutputStreamWriter osw = null;
        try {            
            osw = new OutputStreamWriter(openFileOutput(FILE_NAME, MODE_PRIVATE));
            osw.write(editText.getText().toString());
            Toast.makeText(this, "파일 저장 성공", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "파일 저장 실패", Toast.LENGTH_LONG).show();
        } finally {
            try {
                osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}