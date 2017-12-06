package com.warivirtualpos.wari;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.warivirtualpos.wari.model.WithdrawalData;

import java.util.List;

/**
 * Created by hilary on 11/19/17.
 */

public class WithdrawalRequestAdapter extends RecyclerView.Adapter<WithdrawalRequestAdapter.MyViewHolder>  {
    private Context context;
    private List<WithdrawalData> withdrawalDataList;

        public WithdrawalRequestAdapter(Context context, List<WithdrawalData> withdrawalDataList){
            this.context = context;
            this.withdrawalDataList = withdrawalDataList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.withdraw_item_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            WithdrawalData withdrawalData = withdrawalDataList.get(position);
            holder.lastnameTv.setText(withdrawalData.getLastname());
            holder.firstnameTv.setText(withdrawalData.getFirstname());
            holder.phoneTv.setText(withdrawalData.getPhone());
            holder.confirmationTv.setText(withdrawalData.getConfirmation());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WithdrawalData item = withdrawalDataList.get(position);
                    int id = item.getSqliteId(); // pass the sqlite Id to be used to identify the object
                    Intent i = new Intent(context, WithdrawalRequestDetailActivity.class);
                    i.putExtra("sqliteId", id);
                    context.startActivity(i);

                }
            });

        }

        @Override
        public int getItemCount() {
            return withdrawalDataList.size();
        }
        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView lastnameTv, firstnameTv, phoneTv, confirmationTv;
            public MyViewHolder(View view) {
                super(view);
                lastnameTv = (TextView) view.findViewById(R.id.lastname_text);
                firstnameTv = (TextView) view.findViewById(R.id.firstname_text);
                phoneTv = (TextView) view.findViewById(R.id.phone_text);
                confirmationTv = (TextView) view.findViewById(R.id.confirmation_text);

            }
        }

}
