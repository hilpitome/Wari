package com.warivirtualpos.wari;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.warivirtualpos.wari.model.Agent;
import com.warivirtualpos.wari.utils.DatabaseHandler;
import com.warivirtualpos.wari.utils.WariSecrets;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hilary on 12/29/17.
 */

public class AddAgentActivity extends AppCompatActivity implements View.OnClickListener{
    EditText name, agentNumber, sdBalanceEt;
    DatabaseHandler databaseHandler;
    Button addAgentButton;
    private String mUrl = WariSecrets.addAgentUrl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addagent);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Agent");
        databaseHandler = new DatabaseHandler(this);
        intitalizeView();

    }

    private void intitalizeView() {
        name = (EditText) findViewById(R.id.name_et);
        agentNumber = (EditText) findViewById(R.id.sd_number_et);
        sdBalanceEt = (EditText) findViewById(R.id.sd_balance_et);
        addAgentButton = (Button) findViewById(R.id.add_agent_btn);
        addAgentButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        String agentName, phone, sdBalanceString;
        int sdBalance;

        switch (id){
            case R.id.add_agent_btn:
                agentName = name.getText().toString().trim();
                phone = agentNumber.getText().toString().trim();
                sdBalanceString =  sdBalanceEt.getText().toString().trim();
                if(NumberUtils.isNumber(sdBalanceString)){
                    sdBalance = Integer.parseInt(sdBalanceString);
                } else {
                    sdBalance = 0;
                };
                Agent agent = new Agent();
                agent.setSdBalance(sdBalance);
                agent.setSdName(agentName);
                agent.setSdNumber(phone);
                databaseHandler.addAgentDetails(agent);

                UpdateOnlineDatabaseTask updateOnlineDatabaseTask = new UpdateOnlineDatabaseTask();
                updateOnlineDatabaseTask.execute(agent);

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
                    .add("sd_name", agent.getSdName())
                    .add("create", "1")
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
            startActivity(new Intent(AddAgentActivity.this, MainActivity.class));

        }
    }
}
