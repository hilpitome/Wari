package com.warivirtualpos.wari;

import android.content.Context;
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

    private List<WithdrawalData> withdrawalDataList;

        public WithdrawalRequestAdapter(Context context, List<WithdrawalData> withdrawalDataList){
            this.withdrawalDataList = withdrawalDataList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.request_item_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            WithdrawalData withdrawalData = withdrawalDataList.get(position);

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
