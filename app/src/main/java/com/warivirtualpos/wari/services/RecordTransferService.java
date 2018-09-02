package com.warivirtualpos.wari.services;

import android.os.AsyncTask;

import com.warivirtualpos.wari.model.MainObject;
import com.warivirtualpos.wari.model.TransferRequestData;
import com.warivirtualpos.wari.model.WithdrawalData;
import com.warivirtualpos.wari.utils.WariSecrets;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RecordTransferService {
    private RecordTransferService service;
    private RecordTransferService(){}
    public RecordTransferService getRecordTransferService(){
        if(service == null){
            service = new RecordTransferService();
        }

        return service;
    }



    private class sendRecordTask extends AsyncTask<MainObject,String, String>{

        @Override
        protected String doInBackground(MainObject... mainObjects) {

            RequestBody body = null;
            OkHttpClient client = new OkHttpClient();
            if(mainObjects[0] instanceof WithdrawalData){
                WithdrawalData data = (WithdrawalData) mainObjects[0];
                body = new FormBody.Builder()
                        .add("date", data.getDate())
                        .add("confirmation_code", data.getConfirmation())
                        .add("agent_number", data.getAgentNumber())
                        .add("agent_name", data.getAgentName())
                        .add("withdrawer_firstname", data.getFirstname())
                        .add("withdrawer_lastname", data.getLastname())
                        .add("withdrawal_phone", data.getPhone())
                        .build();

            } else if ( mainObjects[0] instanceof TransferRequestData){
                TransferRequestData data = (TransferRequestData) mainObjects[0];
                body = new FormBody.Builder()
                        .add("date", data.getDate())
                        .add("confirmation_code", data.getConfirmation())
                        .add("agent_number", data.getAgentNumber())
                        .add("agent_name", data.getAgentName())
                        .add("sender_lastname", data.getSenderLastName())
                        .add("sender_firstname", data.getSenderFirstname())
                        .add("sender_phone", data.getSenderPhone())
                        .add("beneficiary_lastname", data.getBeneficiaryFirstname())
                        .add("beneficiary_firstname", data.getBeneficiaryFirstname())
                        .add("beneficiary_phone", data.getBeneficiaryPhone())
                        .build();
            }

            String url = WariSecrets.sendRecordUrl;

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            String resp = "";
            try {
                Response response= client.newCall(request).execute();
                resp = response.body().toString();
            } catch (Exception e){
                e.printStackTrace();
            }

            return resp;
        }
    }
}
