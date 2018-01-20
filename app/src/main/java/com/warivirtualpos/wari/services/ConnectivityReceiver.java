package com.warivirtualpos.wari.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.warivirtualpos.wari.model.Agent;
import com.warivirtualpos.wari.utils.DatabaseHandler;
import com.warivirtualpos.wari.utils.NetworkHelper;
import com.warivirtualpos.wari.utils.WariSecrets;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hilary on 1/13/18.
 */

public class ConnectivityReceiver extends BroadcastReceiver {
    private Context context;
    private DatabaseHandler databaseHandler;
    private String mUrl = WariSecrets.mUrl;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        databaseHandler = new DatabaseHandler(context);



        //Log.d("onReceive", NetworkHelper.getNetworkType(context));
        //Log.d("onReceive", String.valueOf(NetworkHelper.isNetworkConnected(context)));



        String networkType = NetworkHelper.getNetworkType(context);
        if (networkType == "WIFI" || networkType== "MOBILE")  {
            System.out.println("network is on");

            UpdateOnlineDatabaseTask updateOnlineDatabaseTask = new UpdateOnlineDatabaseTask();
            List<Agent> agentList = databaseHandler.getAgentsData();
            if(agentList.size()>0){

                for (Agent agent:agentList) {

                    updateOnlineDatabaseTask.execute(agent);

                }

            }

        }  else {
            System.out.println(networkType);
        }

    }

    private class UpdateOnlineDatabaseTask extends AsyncTask<Agent, String, String> {

        @Override
        protected String doInBackground(Agent... params) {
            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = null;

            String resp = "";
            Agent agent = params[0];

            formBody = new FormBody.Builder()
                    .add("sd_number", agent.getSdNumber())
                    .add("last_balance", String.valueOf(agent.getSdBalance()))
                    .add("update", "1")
                    .build();

            Request request = new Request.Builder()
                    .url(mUrl)
                    .post(formBody)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                resp = response.body().string();

            } catch (IOException e) {
                e.printStackTrace();

            }

            return resp;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println(s);

//            if(s.equals("Agent balance updated successfully")){
//                databaseHandler.updateTransferRequestConfirmation(sqliteId, confirmString, "true");
//            } else {
//                databaseHandler.updateTransferRequestConfirmation(sqliteId, confirmString, "false");
//            }


        }

    }

}
