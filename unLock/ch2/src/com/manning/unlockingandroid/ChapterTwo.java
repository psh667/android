package com.manning.unlockingandroid;

import java.text.NumberFormat;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChapterTwo extends Activity {

    public static final String tag = "Chapter2";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.main);

        final EditText mealpricefield = (EditText) findViewById(R.id.mealprice);
        final TextView answerfield = (TextView) findViewById(R.id.answer);

        final Button button = (Button) findViewById(R.id.calculate);
        button.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                try {
                    // Perform action on click
                    Log.i(ChapterTwo.tag, "onClick invoked.");

                    // grab the meal price from the UI
                    String mealprice = mealpricefield.getText().toString();

                    Log.i(ChapterTwo.tag, "mealprice is [" + mealprice + "]");
                    String answer = "";

                    // check to see if the meal price includes a "$"
                    if (mealprice.indexOf("$") == -1) {
                        mealprice = "$" + mealprice;
                    }

                    float fmp = 0.0F;

                    // get currency formatter
                    NumberFormat nf = java.text.NumberFormat.getCurrencyInstance();

                    if (nf == null) {
                        Log.i(ChapterTwo.tag, "punt - NumberFormat is null");
                    }

                    // grab the input meal price
                    fmp = nf.parse(mealprice).floatValue();

                    // let's give a nice tip -> 20%
                    fmp *= 1.2;

                    Log.i(ChapterTwo.tag, "Total Meal price (unformatted) is [" + fmp + "]");
                    // format our result
                    answer = "Full Price, Including 20% Tip: " + nf.format(fmp);

                    // display the answer
                    answerfield.setText(answer);

                    Log.i(ChapterTwo.tag, "onClick complete.");
                } catch (java.text.ParseException pe) {
                    Log.i(ChapterTwo.tag, "Parse exception caught");
                    answerfield.setText("Failed to parse amount?");
                } catch (Exception e) {
                    Log.e(ChapterTwo.tag, "Failed to Calculate Tip:" + e.getMessage());
                    e.printStackTrace();
                    answerfield.setText(e.getMessage());
                }
            }
        });
    }
}
