package com.appstudio.android.sample.ch_23_1;

import com.appstudio.android.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PhoneStateActivity extends Activity {

    private TextView mCallStateTextView;
    private TextView mCellLocationTextView;
    private TextView mServiceStateTextView;
    private ProgressBar mSignalLevelProgressBar;
    private TextView mSignalLevelTextView;
    private TextView mDeviceInfoTextView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_state_activity);
        mCallStateTextView = 
            (TextView) findViewById(R.id.callState_info);
        mCellLocationTextView = 
            (TextView) findViewById(R.id.cellLocation_info);
        mServiceStateTextView = 
            (TextView) findViewById(R.id.serviceState_info);
        mSignalLevelProgressBar = 
            (ProgressBar) findViewById(R.id.signalLevel);
        mSignalLevelTextView = 
            (TextView) findViewById(R.id.signalLevel_info);
        mDeviceInfoTextView = 
            (TextView) findViewById(R.id.device_info);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopPhoneStateListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startPhoneStateListener();
        displayTelephonyInfo();
    }

    private void startPhoneStateListener() {
        TelephonyManager tm = 
        (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        int events = PhoneStateListener.LISTEN_SIGNAL_STRENGTHS
                | PhoneStateListener.LISTEN_CELL_LOCATION
                | PhoneStateListener.LISTEN_CALL_STATE
                | PhoneStateListener.LISTEN_SERVICE_STATE;
        tm.listen(mPhoneStateListener, events);
    }

    private void stopPhoneStateListener() {
        TelephonyManager tm = 
        (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        tm.listen(mPhoneStateListener, 
                PhoneStateListener.LISTEN_NONE);
    }

    private void displayTelephonyInfo() {
        TelephonyManager tm = 
        (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String deviceid = tm.getDeviceId();
        String phonenumber = tm.getLine1Number();
        String operatorname = tm.getNetworkOperatorName();
        String simcountrycode = tm.getSimCountryIso();
        String simoperator = tm.getSimOperatorName();
        String simserialno = tm.getSimSerialNumber();
        String subscriberid = tm.getSubscriberId();
        String networktype = getNetworkTypeString(
                tm.getNetworkType());
        String phonetype = getPhoneTypeString(
                tm.getPhoneType());
        String deviceinfo = "";
        deviceinfo += ("<<Device INFO>> \n");
        deviceinfo += ("Device ID: " + deviceid + "\n");
        deviceinfo += ("Phone Number: " + phonenumber + "\n");
        deviceinfo += ("Operator Name: "+ operatorname + "\n");
        deviceinfo += 
            ("SIM Country Code: " + simcountrycode + "\n");
        deviceinfo += ("SIM Operator: " + simoperator + "\n");
        deviceinfo += ("SIM Serial No.: " +simserialno + "\n");
        deviceinfo += ("Subscriber ID: " +subscriberid + "\n");
        deviceinfo += ("Network Type: " + networktype + "\n");
        deviceinfo += ("Phone Type: " + phonetype + "\n");
        mDeviceInfoTextView.setText(deviceinfo);
    }

    private String getNetworkTypeString(int type) {
        String typeString = "Unknown";
        switch (type) {
        case TelephonyManager.NETWORK_TYPE_EDGE:
            typeString = "EDGE";
            break;
        case TelephonyManager.NETWORK_TYPE_GPRS:
            typeString = "GPRS";
            break;
        case TelephonyManager.NETWORK_TYPE_UMTS:
            typeString = "UMTS";
            break;
        default:
            typeString = "UNKNOWN";
            break;
        }
        return typeString;
    }

    private String getPhoneTypeString(int type) {
        String typeString = "Unknown";
        switch (type) {
        case TelephonyManager.PHONE_TYPE_GSM:
            typeString = "GSM";
            break;
        case TelephonyManager.PHONE_TYPE_NONE:
            typeString = "UNKNOWN";
            break;
        default:
            typeString = "UNKNOWN";
            break;
        }
        return typeString;
    }

    private final PhoneStateListener mPhoneStateListener = 
        new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state,
                String incomingNumber) {
            String callState = "UNKNOWN";
            switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                callState = "IDLE";
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                callState = "Ringing (" + incomingNumber + ")";
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                callState = "Offhook";
                break;
            }
            mCallStateTextView.setText(callState);
            super.onCallStateChanged(state, incomingNumber);
        }

        @Override
        public void
                onCellLocationChanged(CellLocation location) {
            String locationString = location.toString();
            mCellLocationTextView.setText(locationString);
            super.onCellLocationChanged(location);
        }

        @Override
        public void onServiceStateChanged(
                ServiceState serviceState) {
            String serviceStateString = "UNKNOWN";

            switch (serviceState.getState()) {
            case ServiceState.STATE_IN_SERVICE:
                serviceStateString = "IN SERVICE";
                break;
            case ServiceState.STATE_EMERGENCY_ONLY:
                serviceStateString = "EMERGENCY ONLY";
                break;
            case ServiceState.STATE_OUT_OF_SERVICE:
                serviceStateString = "OUT OF SERVICE";
                break;
            case ServiceState.STATE_POWER_OFF:
                serviceStateString = "POWER OFF";
                break;
            default:
                serviceStateString = "UNKNOWN";
                break;
            }

            mServiceStateTextView.setText(serviceStateString);

            super.onServiceStateChanged(serviceState);
        }

        @Override
        public void onSignalStrengthsChanged(
                SignalStrength signal) {
            int level = signal.getGsmSignalStrength(); 
            int progress = 
                (int) ((((float) level) / 31.0) * 100);
            String signalLevelString = 
                getSignalLevelString(progress);

            mSignalLevelProgressBar.setProgress(progress);
            mSignalLevelTextView.setText(signalLevelString);
            super.onSignalStrengthChanged(level);
        }

        private String getSignalLevelString(int level) { 
            String signalLevelString = "Weak";
            if (level > 75)
                signalLevelString = "Excellent";
            else if (level > 50)
                signalLevelString = "Good";
            else if (level > 25)
                signalLevelString = "Moderate";
            else if (level > 0)
                signalLevelString = "Weak";
            return signalLevelString;
        }
    };
}