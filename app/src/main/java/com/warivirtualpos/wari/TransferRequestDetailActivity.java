package com.warivirtualpos.wari;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.warivirtualpos.wari.model.RequestData;
import com.warivirtualpos.wari.utils.DatabaseHandler;

import static com.warivirtualpos.wari.R.string.app_name;

public class TransferRequestDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private  DatabaseHandler databaseHandler;
    private TextView senderLastName, senderFirstName, senderPhoneNumber,amountTv, beneficiaryFirstName, beneficiaryLastName,
        beneficiaryPhone;
    private EditText confirmationEt;

    private Button btnOk, btnCancel;

    private RequestData requestData;

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

        requestData = databaseHandler.getSingleRequestRecord(sqliteId);

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
        senderLastName.setText(requestData.getSenderLastName());
        senderFirstName.setText(requestData.getSenderFirstname());
        senderPhoneNumber.setText(requestData.getSenderPhone());
        amountTv.setText(String.valueOf(requestData.getAmount()));
        beneficiaryLastName.setText(requestData.getBeneficiaryLastname());
        beneficiaryFirstName.setText(requestData.getBeneficiaryFirstname());
        beneficiaryPhone.setText(requestData.getBeneficiaryPhone());

        confirmationEt.setText(requestData.getConfirmation());
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
                   startActivity(new Intent(TransferRequestDetailActivity.this, MainActivity.class));
                break;
            case  R.id.cancel_btn:
                if(confirmString.length()>0 && !confirmString.equals(requestData.getConfirmation())){
                    DialogFragment cancelConfirmationDialog = CancelConfirmationDialog.newInstance();
                    cancelConfirmationDialog.show(getFragmentManager(), "dialogue");
                }
                break;
            default:
                break;
        }

    }
}
