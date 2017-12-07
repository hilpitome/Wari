package com.warivirtualpos.wari;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.warivirtualpos.wari.model.RequestData;
import com.warivirtualpos.wari.model.WithdrawalData;
import com.warivirtualpos.wari.utils.DatabaseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;




public class MainActivity extends AppCompatActivity {

    DatabaseHandler databaseHandler;
    RecyclerView recyclerView;
    List<Object> items = new ArrayList<>();
    List<RequestData> requestDataList = new ArrayList<>();
    List<WithdrawalData> withdrawalDataList = new ArrayList<>();
    ComplexRecyclerViewAdapter complexRecyclerViewAdapter;
    TextView noTextsTv;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        databaseHandler = new DatabaseHandler(this);

        Resources resources = getResources();

        InputStream inputStream = resources.openRawResource(R.raw.example_objects);

        try {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer,"UTF-8");

            JSONObject withdarawalDataString = new JSONObject(json);
            JSONObject requestdataString = new JSONObject(json);
            // create transfer request
            JSONObject transferRequestData = requestdataString.getJSONObject("transfer_request_data");
            String sender_lastname = transferRequestData.getString("sender_lastname");
            String sender_firstname = transferRequestData.getString("sender_firstname");
            String sender_phone = transferRequestData.getString("sender_phone");
            String amount = transferRequestData.getString("amount");
            String beneficiary_lastname = transferRequestData.getString("beneficiary_lastname");
            String beneficiary_firstname = transferRequestData.getString("beneficiary_firstname");
            String beneficiary_phone = transferRequestData.getString("beneficiary_phone");
            RequestData requestData = new RequestData();
            requestData.setSenderLastName(sender_lastname);
            requestData.setSenderFirstname(sender_firstname);
            requestData.setSenderPhone(sender_phone);
            requestData.setAmount(Integer.valueOf(amount));
            requestData.setBeneficiaryLastname(beneficiary_lastname);
            requestData.setBeneficiaryPhone(beneficiary_phone);
            requestData.setBeneficiaryFirstname(beneficiary_firstname);
            requestData.setStatus("PENDING");
            requestData.setConfirmation("987-098-987");

            databaseHandler.addRequestData(requestData);

            // create withdrawal data
            JSONObject withDrawalData = withdarawalDataString.getJSONObject("withdrawal_request_data");
            String lastname = withDrawalData.getString("lastname");
            String firstname = withDrawalData.getString("firstname");
            String date = withDrawalData.getString("date");
            String confirmation = withDrawalData.getString("confirm");
            String phone = withDrawalData.getString("phone");
            String status = withDrawalData.getString("status");
            Log.e("stat", status);
            WithdrawalData data = new WithdrawalData();
            data.setDate(date);
            data.setLastname(lastname);
            data.setFirstname(firstname);
            data.setConfirmation(confirmation);
            data.setPhone(phone);
            data.setStatus(status);

            databaseHandler.addWithdrawalData(data);


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


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
        requestDataList = databaseHandler.getRequestData();
        withdrawalDataList = databaseHandler.getWithdrawalData();
        this.items.clear();
        for (RequestData requestData : requestDataList) {
            items.add(requestData);
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



}

