package com.warivirtualpos.wari;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warivirtualpos.wari.model.RequestData;
import com.warivirtualpos.wari.model.WithdrawalData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hilary on 12/6/17.
 */

public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    // The items to display in your RecyclerView
    private List<Object> items =  new ArrayList<>();
    private Context context;

    private final int REQUEST = 0, WITHDRAWAL = 1;

    public ComplexRecyclerViewAdapter(Context context) {

        this.context = context;
        
    }
    public void setItemList(List<Object> items){
        this.items.clear();
        this.items.addAll(items);
        this.notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof RequestData) {
            return REQUEST;
        } else if (items.get(position) instanceof WithdrawalData) {
            return WITHDRAWAL;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case REQUEST:
                View v1 = inflater.inflate(R.layout.transfer_request_item_row, parent, false);
                viewHolder = new RequestDataViewHolder(v1);
                break;
            case WITHDRAWAL:
                View v2 = inflater.inflate(R.layout.withdraw_item_row, parent, false);
                viewHolder = new WithdrawalDataViewHolder(v2);
                break;
     
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.e("count", String.valueOf(position));
        if (holder != null) {
            switch (holder.getItemViewType()) {
                case REQUEST:
                    RequestDataViewHolder vh1 = (RequestDataViewHolder) holder;
                    configureViewHolder1(vh1, position);
                    break;
                case WITHDRAWAL:
                    WithdrawalDataViewHolder vh2 = (WithdrawalDataViewHolder) holder;
                    configureViewHolder2(vh2, position);
                    break;
            }
        }

    }

    private void configureViewHolder2(WithdrawalDataViewHolder vh2, final int position) {
        final WithdrawalData withdrawalData = (WithdrawalData) items.get(position);
        vh2.getLastnameTv().setText(withdrawalData.getLastname());
        vh2.getFirstnameTv().setText(withdrawalData.getFirstname());
        vh2.getPhoneTv().setText(withdrawalData.getPhone());
        vh2.getConfirmationTv().setText(withdrawalData.getConfirmation());
        vh2.getStatusTv().setText(withdrawalData.getStatus());
        vh2.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WithdrawalData item = (WithdrawalData) items.get(position);
                int id = item.getSqliteId(); // pass the sqlite Id to be used to identify the object
                Intent i = new Intent(context, WithdrawalRequestDetailActivity.class);
                i.putExtra("sqliteId", id);
                context.startActivity(i);

            }
        });
    }

    private void configureViewHolder1(RequestDataViewHolder vh1, final int position) {
        final RequestData requestData = (RequestData) items.get(position);
        vh1.getSenderLastnameTv().setText(requestData.getSenderLastName());
        vh1.getSenderFirstnameTv().setText(requestData.getSenderFirstname());
        vh1.getSenderPhoneTv().setText(requestData.getSenderPhone());
        vh1.getAmountTv().setText(String.valueOf(requestData.getAmount()));
        vh1.getBeneficiaryFirstnameTv().setText(requestData.getBeneficiaryFirstname());
        vh1.getBeneficiaryLastnameTv().setText(requestData.getBeneficiaryLastname());

        if(requestData.getConfirmation().length()>0){
            vh1.getConfirmTv().setText(requestData.getConfirmation());
            vh1.getConfirmTv().setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        }

        vh1.getStatusTv().setText(requestData.getStatus());
        vh1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestData item = (RequestData) items.get(position);
                int id = item.getSqliteId(); // pass the sqlite Id to be used to identify the object
                Intent i = new Intent(context, TransferRequestDetailActivity.class);
                i.putExtra("sqliteId", id);
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
