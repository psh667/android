package com.androidside.alertdialogdemob1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AlertDialogDemoB1 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button alert1 = (Button) findViewById(R.id.alert1);
        alert1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog1();
            }
        });

        Button alert2 = (Button) findViewById(R.id.alert2);
        alert2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog2();
            }
        });

        Button alert3 = (Button) findViewById(R.id.alert3);
        alert3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog3();
            }
        });
    }
    
    private void showAlertDialog1() {
        final String[] cars = new String[] { "SM3", "SM5", "SM7", "SONATA", "AVANTE" };
    
        new AlertDialog.Builder(this)
            .setTitle("∞Ê∞Ì√¢")
            .setItems(cars, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(AlertDialogDemoB1.this,
                        "cars : " + cars[which], 
                        Toast.LENGTH_SHORT)
                        .show();
                }
            }).setNeutralButton("¥›±‚", null).show();    
    }
    
    private void showAlertDialog2() {
        final String[] cars = new String[] { "SM3", "SM5", "SM7", "SONATA", "AVANTE" };
    
        new AlertDialog.Builder(this)
            .setTitle("∞Ê∞Ì√¢")
            .setSingleChoiceItems(cars, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(AlertDialogDemoB1.this,
                        "cars : " + cars[which], 
                        Toast.LENGTH_SHORT)
                        .show();
                }
            }).setNeutralButton("¥›±‚", null).show();
    }
    
    private void showAlertDialog3() {
        final String[] cars = new String[] { "SM3", "SM5", "SM7", "SONATA", "AVANTE" };
    
        new AlertDialog.Builder(this)
            .setTitle("∞Ê∞Ì√¢")
            .setMultiChoiceItems(cars, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Toast.makeText(AlertDialogDemoB1.this,
                            "cars : " + cars[which], 
                            Toast.LENGTH_SHORT)
                            .show();
                    }
            }).setNeutralButton("¥›±‚", null).show();
    }
}