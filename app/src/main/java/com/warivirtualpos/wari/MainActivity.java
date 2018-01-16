package com.warivirtualpos.wari;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.warivirtualpos.wari.model.Agent;
import com.warivirtualpos.wari.model.AgentsResponse;
import com.warivirtualpos.wari.model.MainObject;
import com.warivirtualpos.wari.model.TransferRequestData;
import com.warivirtualpos.wari.model.WithdrawalData;
import com.warivirtualpos.wari.utils.DatabaseHandler;
import com.warivirtualpos.wari.utils.WariSecrets;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private DatabaseHandler databaseHandler;
    private RecyclerView recyclerView;
    private List<MainObject> items = new ArrayList<>();
    private List<TransferRequestData> transferRequestDataList = new ArrayList<>();
    private List<WithdrawalData> withdrawalDataList = new ArrayList<>();
    private ComplexRecyclerViewAdapter complexRecyclerViewAdapter;
    private TextView noTextsTv;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private String agentsUrl = WariSecrets.agentsUrl;
    private String mUrl = WariSecrets.mUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        databaseHandler = new DatabaseHandler(this);

//        List<Agent> agentList = databaseHandler.getAgentsData();
//
//        Log.e("agents", String.valueOf(agentList.size()));
//
//        if(agentList.size()<1){
//            // download from online database
//            AgentsTask agentsTask = new AgentsTask();
//            agentsTask.execute(agentsUrl);
//
//        }
//
//        Log.e("west", "london");
//
//       if(!checkPhoneNumber("+254729054248")){
//           Log.e("east", "london");
//           Agent agent = new Agent();
//           agent.setSdNumber("+254729054248");
//           agent.setSdBalance("700");
//           databaseHandler.addAgentDetails(agent);
//       }


        Date date = new Date();
        String now = sdf.format(date);

//        Resources resources = getResources();
//
//        InputStream inputStream = resources.openRawResource(R.raw.example_objects);
//
//        try {
//            int size = inputStream.available();
//            byte[] buffer = new byte[size];
//            inputStream.read(buffer);
//            inputStream.close();
//            String json = new String(buffer,"UTF-8");
//
//            JSONObject withdarawalDataString = new JSONObject(json);
//            JSONObject requestdataString = new JSONObject(json);
//            // create transfer request
//            JSONObject transferRequestData = requestdataString.getJSONObject("transfer_request_data");
//            String sender_lastname = transferRequestData.getString("sender_lastname");
//            String sender_firstname = transferRequestData.getString("sender_firstname");
//            String sender_phone = transferRequestData.getString("sender_phone");
//            String amount = transferRequestData.getString("amount");
//            String beneficiary_lastname = transferRequestData.getString("beneficiary_lastname");
//            String beneficiary_firstname = transferRequestData.getString("beneficiary_firstname");
//            String beneficiary_phone = transferRequestData.getString("beneficiary_phone");
//            TransferRequestData requestData = new TransferRequestData();
//            requestData.setSenderLastName(sender_lastname);
//            requestData.setSenderFirstname(sender_firstname);
//            requestData.setSenderPhone(sender_phone);
//            requestData.setAmount(Integer.valueOf(amount));
//            requestData.setBeneficiaryLastname(beneficiary_lastname);
//            requestData.setBeneficiaryPhone(beneficiary_phone);
//            requestData.setBeneficiaryFirstname(beneficiary_firstname);
//            requestData.setStatus("PENDING");
//            requestData.setConfirmation("987-098-987");
//            requestData.setDate(now);
//            requestData.setAgentNumber("+254729054248");
//            requestData.setAgentName("manu");
//
//            databaseHandler.addRequestData(requestData);
//
//            // create withdrawal data
//            JSONObject withDrawalData = withdarawalDataString.getJSONObject("withdrawal_request_data");
//            String lastname = withDrawalData.getString("lastname");
//            String firstname = withDrawalData.getString("firstname");
////            String date = withDrawalData.getString("date");
//            String confirmation = withDrawalData.getString("confirm");
//            String phone = withDrawalData.getString("phone");
//            String status = withDrawalData.getString("status");
//            Log.e("stat", status);
//            WithdrawalData data = new WithdrawalData();
//            data.setDate(now);
//            data.setLastname(lastname);
//            data.setFirstname(firstname);
//            data.setConfirmation(confirmation);
//            data.setPhone(phone);
//            data.setStatus(status);
//            data.setAgentNumber("+254729054248");
//            data.setAgentName("manu");
//
//            Log.e("test", phone);
//
//            databaseHandler.addWithdrawalData(data);
//
//
//        } catch (IOException | JSONException e) {
//            e.printStackTrace();
//        }

        noTextsTv = (TextView) findViewById(R.id.no_sms_tv);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh_layout);


        initSwipeRefreshLayout();
        setRecyclerView();
        setItemList();
    }

    private void initSwipeRefreshLayout(){
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setItemList();
            }
        });
    }

    private void setRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        complexRecyclerViewAdapter = new ComplexRecyclerViewAdapter(this);
        recyclerView.setAdapter(complexRecyclerViewAdapter);
    }
    private void setItemList() {

        transferRequestDataList = databaseHandler.getTransferRequestData();
        withdrawalDataList = databaseHandler.getWithdrawalData();
        Log.e("transfer", String.valueOf(transferRequestDataList.size()));
        Log.e("withdrawal", String.valueOf(withdrawalDataList.size()));
        this.items.clear();
        for (TransferRequestData transferRequestData : transferRequestDataList) {
            items.add(transferRequestData);
        }
        for (WithdrawalData withdrawalData : withdrawalDataList) {
            items.add(withdrawalData);
        }
        if(items.size()>0){
            noTextsTv.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            complexRecyclerViewAdapter.setItemList(items);
            swipeRefreshLayout.setEnabled(true);
        } if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }

    }
    private class AgentsTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String resp = "";
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            Request request = new Request.Builder()
                    .url(agentsUrl)
                    .build();
            Response response = null;
            try {
                response= client.newCall(request).execute();
                resp = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return  resp;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Gson gson = new GsonBuilder().create();
            AgentsResponse agentsResponse = gson.fromJson(s, AgentsResponse.class);
            if(agentsResponse.getStatus().equals("SUCCESS")){
               for(Agent agent:agentsResponse.getResponseBody()){
                   databaseHandler.addAgentDetails(agent);
               }
            }

        }
    }
    private boolean checkPhoneNumber(String phoneNumber){
        if(databaseHandler.checkIfAgent(phoneNumber)!=null){
            return  true;
        } else {
            return false;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.add_agent:
                startActivity(new Intent(MainActivity.this, AddAgentActivity.class));
                break;


            default:
                break;
        }

        return true;
    }


}

