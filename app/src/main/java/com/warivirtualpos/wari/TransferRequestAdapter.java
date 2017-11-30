package com.warivirtualpos.wari;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.warivirtualpos.wari.model.RequestData;
import com.warivirtualpos.wari.model.WithdrawalData;
import com.warivirtualpos.wari.utils.DatabaseHandler;

import java.util.List;

/**
 * Created by hilary on 11/19/17.
 */

public class TransferRequestAdapter extends RecyclerView.Adapter<TransferRequestAdapter.MyViewHolder>  {

    private List<RequestData> requestDataList;
    private DatabaseHandler databaseHandler;
    private Context context;

        public TransferRequestAdapter(Context context, List<RequestData> requestDataList){
            this.requestDataList = requestDataList;
            this.context = context;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.request_item_row, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final RequestData requestData = requestDataList.get(position);
            holder.senderLastnameTv.setText(requestData.getSenderLastName());
            holder.senderFirstnameTv.setText(requestData.getSenderFirstname());
            holder.senderPhoneTv.setText(requestData.getSenderPhone());
            holder.amountTv.setText(String.valueOf(requestData.getAmount()));
            holder.beneficiaryLastnameTv.setText(requestData.getBeneficiaryFirstname());
            holder.beneficiaryFirstnameTv.setText(requestData.getBeneficiaryLastname());
            holder.beneficiaryPhoneTv.setText(requestData.getBeneficiaryPhone());
            holder.confirmationEt.setText(requestData.getConfirmation());
            holder.statusTv.setText(requestData.getStatus());

            holder.confirmBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseHandler = new DatabaseHandler(context);
                    int id = requestData.getSqliteId();
                    String confirmation = holder.confirmationEt.getText().toString().trim();
                    if(databaseHandler.updateTransferRequestConfirmation(id, confirmation)){
                        holder.statusTv.setText("OK");
                        requestData.setStatus("OK");
                        requestData.setConfirmation(confirmation);



                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return requestDataList.size();
        }
        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView senderLastnameTv, senderFirstnameTv, senderPhoneTv, amountTv,
                    beneficiaryLastnameTv, beneficiaryFirstnameTv, beneficiaryPhoneTv, statusTv;
            public EditText confirmationEt;
            public Button confirmBtn;
            public MyViewHolder(View view) {
                super(view);
                senderLastnameTv = (TextView) view.findViewById(R.id.sender_lastname_text);
                senderFirstnameTv = (TextView) view.findViewById(R.id.sender_firstname_text);
                senderPhoneTv = (TextView) view.findViewById(R.id.sender_phone_text);
                amountTv = (TextView) view.findViewById(R.id.amount_text);
                beneficiaryLastnameTv = (TextView) view.findViewById(R.id.beneficiary_lastname_text);
                beneficiaryFirstnameTv = (TextView) view.findViewById(R.id.beneficiary_firstname_text);
                beneficiaryPhoneTv = (TextView) view.findViewById(R.id.beneficiary_phone_text);
                confirmationEt = (EditText) view.findViewById(R.id.confirmation_text);
                confirmBtn = (Button) view.findViewById(R.id.confirm_btn);
                statusTv = (TextView) view.findViewById(R.id.status_text);

            }
        }

}
