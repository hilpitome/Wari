package com.warivirtualpos.wari;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.warivirtualpos.wari.model.TransferRequestData;
import com.warivirtualpos.wari.utils.DatabaseHandler;

import java.util.List;

/**
 * Created by hilary on 11/19/17.
 */

public class TransferRequestsFragment extends Fragment {
    DatabaseHandler databaseHandler;
    TextView noTextsTv;
    RecyclerView recyclerView;
    List<TransferRequestData> records;
    TransferRequestAdapter transferRequestAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHandler = new DatabaseHandler(getActivity());
        records = databaseHandler.getRequestData();
        Log.e("withdrawalsize", String.valueOf(records.size()));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transfers_layout, container, false);
        intitalizeView(view);
        return view;
    }

    private void intitalizeView(View view) {
        noTextsTv = (TextView) view.findViewById(R.id.no_sms_tv);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        if(records.size()<1){
            // do nothing
        } else {
            noTextsTv.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

            recyclerView.setLayoutManager(layoutManager);

            transferRequestAdapter = new TransferRequestAdapter(getActivity(), records);

            recyclerView.setAdapter(transferRequestAdapter);

        }
    }

}
