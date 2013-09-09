package com.androidside.alertdialogdemod1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AlertDialogDemoD1 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button alert = (Button) findViewById(R.id.alert);
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }

    private void showAlertDialog() {
        LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout loginLayout = 
                (LinearLayout) vi.inflate(R.layout.logindialog, null);
        
        final EditText id = (EditText) loginLayout.findViewById(R.id.id);
        final EditText pw = (EditText) loginLayout.findViewById(R.id.pw);        
    
        new AlertDialog.Builder(this)
            .setTitle("로그인")
            .setView(loginLayout)
            .setNeutralButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(
                        AlertDialogDemoD1.this,
                            "ID : " + id.getText().toString() + 
                            "\nPW : " + pw.getText().toString(),
                            Toast.LENGTH_LONG).show();
                }
            }).show();
    }
}