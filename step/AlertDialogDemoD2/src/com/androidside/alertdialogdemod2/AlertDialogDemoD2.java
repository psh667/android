package com.androidside.alertdialogdemod2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AlertDialogDemoD2 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button = (Button) findViewById(R.id.alert);
        button.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                final View login = getLoginView();
                new AlertDialog.Builder(AlertDialogDemoD2.this)
                .setTitle("로그인")        
                .setView(login)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText id = (EditText) login.findViewWithTag("id");
                        EditText pw = (EditText) login.findViewWithTag("pw");
                        
                        Toast.makeText(AlertDialogDemoD2.this, "id: "+id.getText()+"\npw: "+pw.getText(), Toast.LENGTH_LONG).show();
                    }
                })        
                .show();
            }
        });
    }

    private View getLoginView() {
       LinearLayout idLayout = new LinearLayout(this);
       idLayout.setOrientation(LinearLayout.HORIZONTAL);
       TextView idText = new TextView(this);
       idText.setWidth(100);
       idText.setGravity(Gravity.CENTER);
       idText.setText("ID");
       EditText idEdit = new EditText(this);
       idEdit.setTag("id");
       idEdit.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
       idLayout.addView(idText);
       idLayout.addView(idEdit);

       LinearLayout pwLayout = new LinearLayout(this);
       idLayout.setOrientation(LinearLayout.HORIZONTAL);
       TextView pwText = new TextView(this);
       pwText.setWidth(100);
       pwText.setGravity(Gravity.CENTER);
       pwText.setText("PW");
       EditText pwEdit = new EditText(this);   
       pwEdit.setTag("pw");
       pwEdit.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
       pwLayout.addView(pwText);
       pwLayout.addView(pwEdit);
       
       LinearLayout layout = new LinearLayout(this);
       layout.setOrientation(LinearLayout.VERTICAL);
       layout.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
       layout.addView(idLayout);
       layout.addView(pwLayout);

       return layout;
    }
}
