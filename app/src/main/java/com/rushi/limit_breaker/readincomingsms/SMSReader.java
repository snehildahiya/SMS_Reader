package com.rushi.limit_breaker.readincomingsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SMSReader extends BroadcastReceiver {

    // SmsManager class is responsible for all SMS related actions
    final SmsManager sms = SmsManager.getDefault();
    public static final String TAG = "pui";
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "onReceive: ");

        // Get the SMS message received
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                // A PDU is a "protocol data unit". This is the industrial standard for SMS message
                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {
                    // This will create an SmsMessage object from the received pdu
                    SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdusObj[i]);

                    // Get sender phone number
                    String phoneNumber = sms.getDisplayOriginatingAddress();

                    String sender = phoneNumber;
                    String message = sms.getDisplayMessageBody();

                    String formattedText = sender+":"+message;

                    // Display the SMS message in a Toast
                    Toast.makeText(context, formattedText, Toast.LENGTH_LONG).show();


                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}