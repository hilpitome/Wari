package com.warivirtualpos.wari;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.warivirtualpos.wari.model.Agent;
import com.warivirtualpos.wari.model.TransferRequestData;
import com.warivirtualpos.wari.utils.DatabaseHandler;
import com.warivirtualpos.wari.utils.WariSecrets;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TransferRequestDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private  DatabaseHandler databaseHandler;
    private TextView senderLastName, senderFirstName, senderPhoneNumber,amountTv, beneficiaryFirstName, beneficiaryLastName,
        beneficiaryPhone;
    private EditText confirmationEt;

    private Button btnOk, btnCancel;

    private TransferRequestData transferRequestData;

    private int sqliteId;

    private String mUrl = WariSecrets.mUrl;
    private String confirmString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_request_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TransferRequest Detail");
        Bundle bundle = getIntent().getExtras();
        sqliteId = bundle.getInt("sqliteId");
        databaseHandler = new DatabaseHandler(this);

        transferRequestData = databaseHandler.getSingleRequestRecord(sqliteId);

        initializeViewComponents();

        setViewComponents();

    }

    private void initializeViewComponents() {
        senderLastName = (TextView) findViewById(R.id.sender_lastname_text);
        senderFirstName = (TextView) findViewById(R.id.sender_firstname_text);
        senderPhoneNumber = (TextView) findViewById(R.id.sender_phone_text);
        amountTv = (TextView) findViewById(R.id.amount_text);
        beneficiaryLastName = (TextView) findViewById(R.id.beneficiary_lastname_text);
        beneficiaryFirstName = (TextView) findViewById(R.id.beneficiary_firstname_text);
        beneficiaryPhone = (TextView) findViewById(R.id.beneficiary_phone_text);

        confirmationEt = (EditText) findViewById(R.id.confirmation_et);
        btnOk = (Button) findViewById(R.id.ok_btn);
        btnCancel = (Button) findViewById(R.id.cancel_btn);
    }
    private void setViewComponents() {
        senderLastName.setText(transferRequestData.getSenderLastName());
        senderFirstName.setText(transferRequestData.getSenderFirstname());
        senderPhoneNumber.setText(transferRequestData.getSenderPhone());
        amountTv.setText(String.valueOf(transferRequestData.getAmount()));
        beneficiaryLastName.setText(transferRequestData.getBeneficiaryLastname());
        beneficiaryFirstName.setText(transferRequestData.getBeneficiaryFirstname());
        beneficiaryPhone.setText(transferRequestData.getBeneficiaryPhone());

        confirmationEt.setText(transferRequestData.getConfirmation());
        btnOk.setOnClickListener(this); ;
        btnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        confirmString = confirmationEt.getText().toString().trim();
        switch (id){
            case R.id.ok_btn:
                SmsManager smsManager = SmsManager.getDefault();
                if(confirmString.length()<1){
                    confirmString = "";
                    startActivity(new Intent(TransferRequestDetailActivity.this, MainActivity.class));
                }
                
                Agent agent  = databaseHandler.checkIfAgent(transferRequestData.getAgentNumber());
                if(agent!=null) {
                    int balance = transferRequestData.getAmount() + Integer.valueOf(agent.getSdBalance());
                    agent.setSdBalance(balance);
                    // update agent online balance
                    UpdateOnlineDatabaseTask updateOnlineDatabase = new UpdateOnlineDatabaseTask();
                    updateOnlineDatabase.execute(agent);
                    // update local sqlite database
                    databaseHandler.updateSqliteBalance(balance, transferRequestData.getAgentNumber());

                    String smsMessage = "Collecter " + transferRequestData.getAmount() + " de " + transferRequestData.getSenderLastName() + " avec Confirmation " + confirmString;
                    smsManager.sendTextMessage(transferRequestData.getAgentNumber(), null, smsMessage, null, null);
                } else {
                    Toast.makeText(this, "agent does not exist", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(TransferRequestDetailActivity.this, MainActivity.class));
                break;
            case  R.id.cancel_btn:
                if(confirmString.length()>0 && !confirmString.equals(transferRequestData.getConfirmation())){
                    DialogFragment cancelConfirmationDialog = CancelConfirmationDialog.newInstance();
                    cancelConfirmationDialog.show(getFragmentManager(), "dialogue");
                }
                break;
            default:
                break;
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
            Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
            if(s.equals("Agent balance updated successfully")){
                databaseHandler.updateTransferRequestConfirmation(sqliteId, confirmString, "true");
            } else {
                databaseHandler.updateTransferRequestConfirmation(sqliteId, confirmString, "false");
            }


        }

    }

}
