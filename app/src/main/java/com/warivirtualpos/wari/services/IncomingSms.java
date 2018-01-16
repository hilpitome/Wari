package com.warivirtualpos.wari.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;


import com.warivirtualpos.wari.model.TransferRequestData;
import com.warivirtualpos.wari.model.WithdrawalData;
import com.warivirtualpos.wari.utils.DatabaseHandler;
import com.warivirtualpos.wari.utils.WariSecrets;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;


/**
 * Created by hilary on 11/15/17.
 */

public class IncomingSms extends BroadcastReceiver {
    /**
     * Constant TAG for logging key.
     */
    private static final String TAG = IncomingSms.class.getSimpleName();

    private String mUrl = "";


    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private  Context context;
    private DatabaseHandler databaseHandler;
    private static int WITHDRAWAL = 1;
    private static int TRANSFER = 0;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        Bundle bundle = intent.getExtras();
        mUrl = WariSecrets.mUrl;

        if (bundle != null) {
            Object[] pdu_Objects = (Object[]) bundle.get("pdus");
            Log.e("test", "check 123");
            if (pdu_Objects != null) {
                databaseHandler = new DatabaseHandler(context);

                for (Object aObject : pdu_Objects) {
                    Date date = new Date();
                    String now = sdf.format(date);
                    SmsMessage currentSMS = getIncomingMessage(aObject, bundle);

                    String senderNo = currentSMS.getDisplayOriginatingAddress();

                    String message = currentSMS.getDisplayMessageBody();


                    if (message.toLowerCase().contains("envoi")) {

                        // check if the sms is from a valid agent as per sqlite database
                       if(checkPhoneNumber(senderNo)){

                           String[] messageArray = message.split("\\#");
                           String[] senderInfoArr = messageArray[0].split("\\*");
                           String[] beneficiaryInfoArr = messageArray[1].split("\\*");

                           String senderLastName = senderInfoArr[1];
                           String senderFirstName = senderInfoArr[2];
                           String senderPhoneNo = senderInfoArr[3];
                           int amount = Integer.valueOf(senderInfoArr[4]);

                           String beneficiaryLastName = beneficiaryInfoArr[0];
                           String beneficiaryFirstName = beneficiaryInfoArr[1];
                           String beneficiaryPhone = beneficiaryInfoArr[2];


                           TransferRequestData transferRequestData = new TransferRequestData(
                                   now,
                                   senderLastName,
                                   senderFirstName,
                                   senderPhoneNo,
                                   amount,
                                   beneficiaryLastName,
                                   beneficiaryFirstName,
                                   beneficiaryPhone,
                                   "PENDING"
                           );
                           transferRequestData.setAgentNumber(senderNo);
                           databaseHandler.addRequestData(transferRequestData);


                       }


                    } else if (message.toLowerCase().contains("retrait")) {
                        // check if the sms is from a valid agent as per sqlite database
                        Log.e("test", senderNo);

                        if(checkPhoneNumber(senderNo)){
                            String lastname, firstname, phone, confirmation;
                            String[] withdrawalMsgArr = message.split("\\*");
                            lastname = withdrawalMsgArr[1];
                            firstname = withdrawalMsgArr[2];
                            phone = withdrawalMsgArr[3];
                            confirmation = withdrawalMsgArr[4];

                            WithdrawalData withdrawalData = new WithdrawalData(now, lastname, firstname, phone, confirmation);
                            withdrawalData.setStatus("PENDING");
                            withdrawalData.setAgentNumber(senderNo);

                            databaseHandler.addWithdrawalData(withdrawalData);

                        }

                        //
                    }

                }


            }
        }
    }


    private SmsMessage getIncomingMessage(Object aObject, Bundle bundle) {
        SmsMessage currentSMS;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String format = bundle.getString("format");
            currentSMS = SmsMessage.createFromPdu((byte[]) aObject, format);
        } else {
            currentSMS = SmsMessage.createFromPdu((byte[]) aObject);
        }
        return currentSMS;

    }



    private boolean checkPhoneNumber(String phoneNumber){
        if(databaseHandler.checkIfAgent(phoneNumber)!=null){
            return  true;
        } else {
            return false;
        }
    }


}
