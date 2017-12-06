package com.warivirtualpos.wari;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.warivirtualpos.wari.model.RequestData;
import com.warivirtualpos.wari.model.WithdrawalData;
import com.warivirtualpos.wari.utils.DatabaseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import static com.warivirtualpos.wari.R.string.transfers_title;
import static com.warivirtualpos.wari.R.string.withdrawals_title;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private int navItemId;
    private static final String NAV_ITEM_ID = "nav_index";
    DatabaseHandler databaseHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SmsManager smsManager = SmsManager.getDefault();


        Resources resources = getResources();

        InputStream inputStream = resources.openRawResource(R.raw.example_objects);

        try {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer,"UTF-8");
//            JSONObject withdarawalDataString = new JSONObject(json);
            JSONObject requestdataString = new JSONObject(json);
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

            databaseHandler = new DatabaseHandler(this);

            databaseHandler.addRequestData(requestData);

//            JSONObject withDrawalData = withdarawalDataString.getJSONObject("withdrawal_request_data");
//
//
//            String lastname = withDrawalData.getString("lastname");
//
//            String firstname = withDrawalData.getString("firstname");
//
//            String date = withDrawalData.getString("date");
//
//            String confirmation = withDrawalData.getString("confirm");
//            Log.e("before",confirmation);
//            String phone = withDrawalData.getString("phone");
//
//            WithdrawalData data = new WithdrawalData();
//            data.setDate(date);
//            data.setLastname(lastname);
//            data.setFirstname(firstname);
//            data.setConfirmation(confirmation);
//            data.setPhone(phone);
//
//            databaseHandler = new DatabaseHandler(this);
//            databaseHandler.addWithdrawalData(data);
//            Log.e("after", "after");
//

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // default to msgsOperateur

        if (null != savedInstanceState) {
            navItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        } else {
            navItemId =R.id.nav_withdrawals;
        }

        displayView(navItemId);
        navigationView.setCheckedItem(navItemId);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        displayView(item.getItemId());
        navItemId = item.getItemId();

        return true;
    }
    public void displayView(int viewId) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (viewId) {
            case R.id.nav_transfers:
                fragment = new TransferRequestsFragment();
                title = getResources().getString(transfers_title);
                break;

            case R.id.nav_withdrawals:
                fragment = new WithdrawRequestFragment();
                title = getResources().getString(withdrawals_title);
                break;
            default:
                break;
        }


        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame, fragment);
            ft.commit();
        }

        // set the toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, navItemId);

    }
}
