package com.warivirtualpos.wari;

import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.warivirtualpos.wari.model.Agent;
import com.warivirtualpos.wari.model.TransferRequestData;
import com.warivirtualpos.wari.utils.DatabaseHandler;

public class TransferRequestDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private  DatabaseHandler databaseHandler;
    private TextView senderLastName, senderFirstName, senderPhoneNumber,amountTv, beneficiaryFirstName, beneficiaryLastName,
        beneficiaryPhone;
    private EditText confirmationEt;

    private Button btnOk, btnCancel;

    private TransferRequestData transferRequestData;

    private int sqliteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_request_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TransferRequest Detail Version 3");
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
        String confirmString = confirmationEt.getText().toString().trim();
        switch (id){
            case R.id.ok_btn:

                if(confirmString.length()<1){
                    confirmString = "";
                }
                databaseHandler.updateTransferRequestConfirmation(sqliteId, confirmString);
                Agent agent  = databaseHandler.checkIfAgent(transferRequestData.getAgentNumber());
                int balance = transferRequestData.getAmount() + Integer.valueOf(agent.getSdBalance());
                databaseHandler.updateSqliteBalance(balance, transferRequestData.getAgentNumber());
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
}
