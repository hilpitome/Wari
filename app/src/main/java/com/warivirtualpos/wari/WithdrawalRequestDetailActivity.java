package com.warivirtualpos.wari;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.warivirtualpos.wari.model.WithdrawalData;
import com.warivirtualpos.wari.utils.DatabaseHandler;

import static com.warivirtualpos.wari.R.string.app_name;

public class WithdrawalRequestDetailActivity extends AppCompatActivity implements View.OnClickListener{
    TextView lastNameTv, firstNameTv, phoneNumberTv, confirmationTv;
    Button validateBtn, btnCancel;
    DatabaseHandler databaseHandler;
    WithdrawalData withdrawalData;
    int sqliteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal_request_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Withdrawal Detail Version 3");
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
        confirmationTv = (TextView) findViewById(R.id.confirmation_et);
        validateBtn = (Button) findViewById(R.id.validate_btn);
        btnCancel = (Button) findViewById(R.id.cancel_btn);
    }
    private void setViewComponents() {
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

        switch (id){
            case R.id.validate_btn:
                SmsManager smsManager = SmsManager.getDefault();
//                smsManager.sendTextMessage("agentPhone", null, "sms message", null, null);
                String confirmString = confirmationTv.getText().toString().trim();
                databaseHandler.updateWithdrawalConfirmation(sqliteId);
                Toast.makeText(this, "validation sent to  agent", Toast.LENGTH_SHORT).show();

                break;
            case  R.id.cancel_btn:
                // do nothing
                Toast.makeText(this, "Nothing to confirm", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

    }

}
