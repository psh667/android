package com.msi.manning.telephonyexplorer;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PhoneNumberUtilsExample extends Activity {

    private TextView pnOutput;
    private EditText pnInput;
    private EditText pnInPlaceInput;
    private Button pnFormat;

    @Override
    public void onCreate(final Bundle icicle) {
        Log.d(Constants.LOGTAG, "PhoneNumberUtilsExample onCreate");

        super.onCreate(icicle);
        this.setContentView(R.layout.phonenumberutilsexample);

        this.pnOutput = (TextView) findViewById(R.id.pnoutput);
        this.pnInput = (EditText) findViewById(R.id.pninput);
        this.pnInPlaceInput = (EditText) findViewById(R.id.pninplaceinput);
        this.pnFormat = (Button) findViewById(R.id.pnformat);

        this.pnFormat.setOnClickListener(new OnClickListener() {

            public void onClick(final View v) {
                Log.d(Constants.LOGTAG, "PhoneNumberUtilsExample format TextView input - "
                    + pnInput.getText().toString());

                // format as a phone number FIRST
                String phoneNumber = PhoneNumberUtils.formatNumber(pnInput.getText().toString());
                // then convert phone number keypad alpha to numeric
                phoneNumber = PhoneNumberUtils.convertKeypadLettersToDigits(pnInput.getText().toString());

                StringBuilder result = new StringBuilder();
                result.append(phoneNumber);
                result.append("\nisGlobal - " + PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber));
                result.append("\nisEmergency - " + PhoneNumberUtils.isEmergencyNumber(phoneNumber));

                pnOutput.setText(result.toString());

                pnInput.setText("");
            }
        });

        this.pnInPlaceInput.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(final View v, final boolean b) {
                if (v.equals(pnInPlaceInput) && (b == false)) {
                    Log.d(Constants.LOGTAG, "PhoneNumberUtilsExample formatInPlace TextView input - "
                        + pnInPlaceInput.getText().toString());
                    PhoneNumberUtils.formatNumber(pnInPlaceInput.getText(), PhoneNumberUtils.FORMAT_NANP);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
