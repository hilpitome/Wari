package com.warivirtualpos.wari;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.warivirtualpos.wari.model.Agent;
import com.warivirtualpos.wari.model.WithdrawalData;
import com.warivirtualpos.wari.utils.DatabaseHandler;
import com.warivirtualpos.wari.utils.WariSecrets;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WithdrawalRequestDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView lastNameTv, firstNameTv, phoneNumberTv, confirmationTv;
    private EditText amountEt;
    private Button validateBtn, btnCancel;
    private DatabaseHandler databaseHandler;
    private WithdrawalData withdrawalData;
    private int sqliteId;
    private String mUrl = WariSecrets.mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal_request_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Withdrawal Detail");
        Bundle bundle = getIntent().getExtras();
        sqliteId = bundle.getInt("sqliteId");
        databaseHandler = new DatabaseHandler(this);

        withdrawalData = databaseHandler.getSingleWithdrawalRecord(sqliteId);

        initializeViewComponents();

        setViewComponents();

    }

    private void initializeViewComponents() {
        lastNameTv = (TextView) findViewById(R.id.beneficiary_lastname_text);
        firstNameTv = (TextView) findViewById(R.id.beneficiary_firstname_text);
        phoneNumberTv = (TextView) findViewById(R.id.beneficiary_phone_text);
        confirmationTv = (TextView) findViewById(R.id.confirmation_tv);
        amountEt = (EditText) findViewById(R.id.amount_et);
        validateBtn = (Button) findViewById(R.id.validate_btn);
        btnCancel = (Button) findViewById(R.id.cancel_btn);
    }

    private void setViewComponents() {
        Log.e("withdrawal", "this: "+withdrawalData.getConfirmation());
        lastNameTv.setText(withdrawalData.getLastname());
        firstNameTv.setText(withdrawalData.getFirstname());
        phoneNumberTv.setText(withdrawalData.getPhone());
        confirmationTv.setText(withdrawalData.getConfirmation());
        validateBtn.setOnClickListener(this);
        btnCancel.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.validate_btn:
                SmsManager smsManager = SmsManager.getDefault();

                String amount = amountEt.getText().toString().trim();
                withdrawalData.getAgentNumber();
                Agent agent = databaseHandler.checkIfAgent(withdrawalData.getAgentNumber());

                if(agent!=null) {
                    int balance = agent.getSdBalance() - Integer.valueOf(amount);
                    agent.setSdBalance(balance);
                    // update agent online balance
                    UpdateOnlineDatabaseTask updateOnlineDatabase = new UpdateOnlineDatabaseTask();
                    updateOnlineDatabase.execute(agent);
                    // update agent local balance
                    databaseHandler.updateSqliteBalance(balance, withdrawalData.getAgentNumber());

                    String smsMessage = "Remettre " + amount + " au Client " + withdrawalData.getLastname() + " " + withdrawalData.getPhone() + " " + "suite Confirmation " + withdrawalData.getConfirmation();
                    smsManager.sendTextMessage(withdrawalData.getAgentNumber(), null, smsMessage, null, null);
                    withdrawalData.setAmount(amountEt.getText().toString().trim());
                    databaseHandler.updateWithdrawalDataAmount(sqliteId, amount);
                } else {
                    Toast.makeText(this, "agent does not exist", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(WithdrawalRequestDetailActivity.this, MainActivity.class));

                break;
            case R.id.cancel_btn:
//                if(confirmString.length()>0 && !confirmString.equals(transferRequestData.getConfirmation())){
//                    DialogFragment cancelConfirmationDialog = CancelConfirmationDialog.newInstance();
//                    cancelConfirmationDialog.show(getFragmentManager(), "dialogue");
//                }
//                break;
                startActivity(new Intent(WithdrawalRequestDetailActivity.this, MainActivity.class));
                break;

            default:
                break;
        }

    }

    private class UpdateOnlineDatabaseTask extends AsyncTask<Agent, String, String> {

        @Override
        protected String doInBackground(Agent... params) {
            OkHttpClient client = new OkHttpClient();
            String resp = "";
            Agent agent = params[0];
            RequestBody formBody = new FormBody.Builder()
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
            Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
            if(s.equals("Agent balance updated successfully")){

                databaseHandler.updateWithdrawalConfirmation(sqliteId);
            }
        }
    }

}
