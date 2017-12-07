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
        noTextsTv = (TextView) findViewById(R.id.no_sms_tv);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh_layout);

        databaseHandler = new DatabaseHandler(this);

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

