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


import com.warivirtualpos.wari.model.RequestData;
import com.warivirtualpos.wari.model.WithdrawalData;
import com.warivirtualpos.wari.utils.DatabaseHandler;
import com.warivirtualpos.wari.utils.WariSecrets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


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
    Context context;
    DatabaseHandler databaseHandler;




    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        Bundle bundle = intent.getExtras();
        mUrl = WariSecrets.mUrl;

        Log.e("mUrl", mUrl);
        if (bundle != null) {
            Object[] pdu_Objects = (Object[]) bundle.get("pdus");
            if (pdu_Objects != null) {
                databaseHandler = new DatabaseHandler(context);

                for (Object aObject : pdu_Objects) {
                    Date date = new Date();
                    String now = sdf.format(date);
                    SmsMessage currentSMS = getIncomingMessage(aObject, bundle);

                    String senderNo = currentSMS.getDisplayOriginatingAddress();

                    String message = currentSMS.getDisplayMessageBody();

                    if (message.toLowerCase().contains("envoi")) {

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


                        RequestData requestData = new RequestData(
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

                        databaseHandler.addRequestData(requestData);
                        SendToMySqlTask sendToMySqlTask = new SendToMySqlTask();
                        sendToMySqlTask.execute(requestData);


                    } else if (message.toLowerCase().contains("retrait")) {

                        String lastname, firstname, phone, confirmation;
                        String[] withdrawalMsgArr = message.split("\\*");
                        lastname = withdrawalMsgArr[1];
                        firstname = withdrawalMsgArr[2];
                        phone = withdrawalMsgArr[3];
                        confirmation = withdrawalMsgArr[4];

                        WithdrawalData withdrawalData = new WithdrawalData(now, lastname, firstname, phone, confirmation);


                        databaseHandler.addWithdrawalData(withdrawalData);
                        SendToMySqlTask sendToMySqlTask = new SendToMySqlTask();
                        sendToMySqlTask.execute(withdrawalData);
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

    private class SendToMySqlTask extends AsyncTask<Object, String, String> {

        @Override
        protected String doInBackground(Object... params) {
            String resp ="";
            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = null;

            if(params[0] instanceof RequestData){

//                SmsData smsData = (SmsData) params[0];
//                Log.e("Total_Caisse ", smsData.getTotalCaisse());
//                formBody =   new FormBody.Builder()
//                        .add("SD_Number", smsData.getSdNumber())
//                        .add("Cash_recu", smsData.getCashRecu())
//                        .add("Vente_Serveur", smsData.getVenteServuer())
//                        .add("Total_Caisse", smsData.getTotalCaisse())
//                        .build();

            } else if (params[0] instanceof WithdrawalData){
//                MessagesOperatuerData messagesOperatuerData = (MessagesOperatuerData) params[0];
//                formBody =   new FormBody.Builder()
//                        .add("date", messagesOperatuerData.getDate())
//                        .add("title", messagesOperatuerData.getTitle())
//                        .add("description", messagesOperatuerData.getDescription())
//                        .add("phone_number", messagesOperatuerData.getPhoneNumber())
//                        .add("sim_number", messagesOperatuerData.getSimNumber())
//                        .build();
//                Log.e("phone", messagesOperatuerData.getSimNumber());
            }

//            Request request = new Request.Builder()
//                    .url(mUrl)
//                    .post(formBody)
//                    .build();
//            try {
//                Response response = client.newCall(request).execute();
//                resp = response.body().string();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//
//            }

            return resp;
        }




        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i(TAG, s);
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }
    }


}
