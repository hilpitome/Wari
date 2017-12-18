package com.warivirtualpos.wari;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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

public class TransferRequestAdapter extends RecyclerView.Adapter<TransferRequestAdapter.MyViewHolder>  {

    private List<TransferRequestData> transferRequestDataList;
    private DatabaseHandler databaseHandler;
    private Context context;

        public TransferRequestAdapter(Context context, List<TransferRequestData> transferRequestDataList){
            this.transferRequestDataList = transferRequestDataList;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.transfer_request_item_row, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            final TransferRequestData transferRequestData = transferRequestDataList.get(position);
            holder.senderLastnameTv.setText(transferRequestData.getSenderLastName());
            holder.senderFirstnameTv.setText(transferRequestData.getSenderFirstname());
            holder.senderPhoneTv.setText(transferRequestData.getSenderPhone());
    //            Log.e("phone", transferRequestData.getSenderPhone());
            holder.amountTv.setText(String.valueOf(transferRequestData.getAmount()));
            holder.beneficiaryLastnameTv.setText(transferRequestData.getBeneficiaryFirstname());
            holder.beneficiaryFirstnameTv.setText(transferRequestData.getBeneficiaryLastname());
            holder.beneficiaryPhoneTv.setText(transferRequestData.getBeneficiaryPhone());

            if(transferRequestData.getConfirmation().length()>0){
                holder.confirmTv.setText(transferRequestData.getConfirmation());
                holder.confirmTv.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }

            holder.statusTv.setText(transferRequestData.getStatus());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   TransferRequestData item = transferRequestDataList.get(position);
                   int id = item.getSqliteId(); // pass the sqlite Id to be used to identify the object
                   Intent i = new Intent(context, TransferRequestDetailActivity.class);
                   i.putExtra("sqliteId", id);
                   context.startActivity(i);

               }
            });

        }

        @Override
        public int getItemCount() {
            return transferRequestDataList.size();
        }
        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView senderLastnameTv, senderFirstnameTv, senderPhoneTv, amountTv,
                    beneficiaryLastnameTv, beneficiaryFirstnameTv, beneficiaryPhoneTv, statusTv,
                    confirmTv;
            public MyViewHolder(View view) {
                super(view);
                senderLastnameTv = (TextView) view.findViewById(R.id.sender_lastname_text);
                senderFirstnameTv = (TextView) view.findViewById(R.id.sender_firstname_text);
                senderPhoneTv = (TextView) view.findViewById(R.id.sender_phone_text);
                amountTv = (TextView) view.findViewById(R.id.amount_text);
                beneficiaryLastnameTv = (TextView) view.findViewById(R.id.beneficiary_lastname_text);
                beneficiaryFirstnameTv = (TextView) view.findViewById(R.id.beneficiary_firstname_text);
                beneficiaryPhoneTv = (TextView) view.findViewById(R.id.beneficiary_phone_text);
                confirmTv = (TextView) view.findViewById(R.id.confirmation_text);
                statusTv = (TextView) view.findViewById(R.id.status_text);

            }
        }

}
