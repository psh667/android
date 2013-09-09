package com.appstudio.android.sample.ch_23_6;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras == null)
            return;

        Object[] pdus = (Object[]) extras.get("pdus");

        for (int i = 0; i < pdus.length; i++) {
            SmsMessage message = 
                SmsMessage.createFromPdu((byte[]) pdus[i]);
            String fromAddress = 
                message.getOriginatingAddress();
            String fromDisplayName = fromAddress;
            Uri uri;
            String[] projection;
            uri = Uri.withAppendedPath(
              ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
              Uri.encode(fromAddress));
            projection = new String[] 
                { ContactsContract.PhoneLookup.DISPLAY_NAME };
            Cursor cursor = context.getContentResolver().query(
                    uri, projection, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst())  {
                    fromDisplayName = cursor.getString(0);
                }
                cursor.close();
            }

            // 문자를 화면에 출력하기 위한 액티비티를 출력
            Intent di = new Intent();
            di.setClass(context, SmsReceivedDialog.class);
            di.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | 
                        Intent.FLAG_ACTIVITY_SINGLE_TOP);
            di.putExtra(
                    SmsReceivedDialog.SMS_FROM_ADDRESS_EXTRA,
                    fromAddress);
            di.putExtra(
                    SmsReceivedDialog.SMS_FROM_DISPLAY_NAME_EXTRA, 
                    fromDisplayName);
            di.putExtra(SmsReceivedDialog.SMS_MESSAGE_EXTRA, 
                    message.getMessageBody().toString());
            context.startActivity(di);
            // 데모용이기 때문에 첫번째 데이터만 처리하였다.
            break;
        }
    }
}
