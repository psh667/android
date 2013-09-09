package com.msi.manning.telephonyexplorer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.TextView;

public class TelephonyManagerExample extends Activity {

    private TextView telMgrOutput;

    @Override
    public void onCreate(final Bundle icicle) {
        Log.d(Constants.LOGTAG, "TelephonyManagerExample onCreate");

        super.onCreate(icicle);
        this.setContentView(R.layout.telmgrexample);

        this.telMgrOutput = (TextView) findViewById(R.id.telmgroutput);
    }

    @Override
    public void onStart() {
        super.onStart();

        // TelephonyManager
        final TelephonyManager telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        this.telMgrOutput.setText(telMgr.toString());

        // PhoneStateListener
        PhoneStateListener phoneStateListener = new PhoneStateListener() {

            @Override
            public void onCallStateChanged(final int state, final String incomingNumber) {
                telMgrOutput.setText(getTelephonyOverview(telMgr));
                Log.d(Constants.LOGTAG, "phoneState updated - incoming number - " + incomingNumber);
            }
        };
        telMgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        String telephonyOverview = getTelephonyOverview(telMgr);
        this.telMgrOutput.setText(telephonyOverview);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * Parse TelephonyManager values into a human readable String.
     * 
     * @param telMgr
     * @return
     */
    public String getTelephonyOverview(final TelephonyManager telMgr) {
        int callState = telMgr.getCallState();
        String callStateString = "NA";
        switch (callState) {
            case TelephonyManager.CALL_STATE_IDLE:
                callStateString = "IDLE";
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                callStateString = "OFFHOOK";
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                callStateString = "RINGING";
                break;
        }
        GsmCellLocation cellLocation = (GsmCellLocation) telMgr.getCellLocation();
        String cellLocationString = cellLocation.getLac() + " " + cellLocation.getCid();

        String deviceId = telMgr.getDeviceId();
        String deviceSoftwareVersion = telMgr.getDeviceSoftwareVersion();

        String line1Number = telMgr.getLine1Number();

        String networkCountryIso = telMgr.getNetworkCountryIso();
        String networkOperator = telMgr.getNetworkOperator();
        String networkOperatorName = telMgr.getNetworkOperatorName();

        int phoneType = telMgr.getPhoneType();
        String phoneTypeString = "NA";
        switch (phoneType) {
            case TelephonyManager.PHONE_TYPE_GSM:
                phoneTypeString = "GSM";
                break;
            case TelephonyManager.PHONE_TYPE_NONE:
                phoneTypeString = "NONE";
                break;
        }

        String simCountryIso = telMgr.getSimCountryIso();
        String simOperator = telMgr.getSimOperator();
        String simOperatorName = telMgr.getSimOperatorName();
        String simSerialNumber = telMgr.getSimSerialNumber();
        String simSubscriberId = telMgr.getSubscriberId();
        int simState = telMgr.getSimState();
        String simStateString = "NA";
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                simStateString = "ABSENT";
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                simStateString = "NETWORK_LOCKED";
                break;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                simStateString = "PIN_REQUIRED";
                break;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                simStateString = "PUK_REQUIRED";
                break;
            case TelephonyManager.SIM_STATE_READY:
                simStateString = "STATE_READY";
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                simStateString = "STATE_UNKNOWN";
                break;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("telMgr - ");
        sb.append("  \ncallState = " + callStateString);
        sb.append("  \ncellLocationString = " + cellLocationString);
        sb.append("  \ndeviceId = " + deviceId);
        sb.append("  \ndeviceSoftwareVersion = " + deviceSoftwareVersion);
        sb.append("  \nline1Number = " + line1Number);
        sb.append("  \nnetworkCountryIso = " + networkCountryIso);
        sb.append("  \nnetworkOperator = " + networkOperator);
        sb.append("  \nnetworkOperatorName = " + networkOperatorName);
        sb.append("  \nphoneTypeString = " + phoneTypeString);
        sb.append("  \nsimCountryIso = " + simCountryIso);
        sb.append("  \nsimOperator = " + simOperator);
        sb.append("  \nsimOperatorName = " + simOperatorName);
        sb.append("  \nsimSerialNumber = " + simSerialNumber);
        sb.append("  \nsimSubscriberId = " + simSubscriberId);
        sb.append("  \nsimStateString = " + simStateString);

        return sb.toString();
    }
}
