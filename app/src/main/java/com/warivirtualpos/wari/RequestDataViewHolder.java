package com.warivirtualpos.wari;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by hilary on 12/6/17.
 */

public class RequestDataViewHolder extends RecyclerView.ViewHolder {
    private TextView senderLastnameTv, senderFirstnameTv, senderPhoneTv, amountTv,
            beneficiaryLastnameTv, beneficiaryFirstnameTv, beneficiaryPhoneTv, statusTv,
            confirmTv;
    public RequestDataViewHolder(View view) {
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

    public TextView getSenderLastnameTv() {
        return senderLastnameTv;
    }

    public void setSenderLastnameTv(TextView senderLastnameTv) {
        this.senderLastnameTv = senderLastnameTv;
    }

    public TextView getSenderFirstnameTv() {
        return senderFirstnameTv;
    }

    public void setSenderFirstnameTv(TextView senderFirstnameTv) {
        this.senderFirstnameTv = senderFirstnameTv;
    }

    public TextView getSenderPhoneTv() {
        return senderPhoneTv;
    }

    public void setSenderPhoneTv(TextView senderPhoneTv) {
        this.senderPhoneTv = senderPhoneTv;
    }

    public TextView getAmountTv() {
        return amountTv;
    }

    public void setAmountTv(TextView amountTv) {
        this.amountTv = amountTv;
    }

    public TextView getBeneficiaryLastnameTv() {
        return beneficiaryLastnameTv;
    }

    public void setBeneficiaryLastnameTv(TextView beneficiaryLastnameTv) {
        this.beneficiaryLastnameTv = beneficiaryLastnameTv;
    }

    public TextView getBeneficiaryFirstnameTv() {
        return beneficiaryFirstnameTv;
    }

    public void setBeneficiaryFirstnameTv(TextView beneficiaryFirstnameTv) {
        this.beneficiaryFirstnameTv = beneficiaryFirstnameTv;
    }

    public TextView getBeneficiaryPhoneTv() {
        return beneficiaryPhoneTv;
    }

    public void setBeneficiaryPhoneTv(TextView beneficiaryPhoneTv) {
        this.beneficiaryPhoneTv = beneficiaryPhoneTv;
    }

    public TextView getStatusTv() {
        return statusTv;
    }

    public void setStatusTv(TextView statusTv) {
        this.statusTv = statusTv;
    }

    public TextView getConfirmTv() {
        return confirmTv;
    }

    public void setConfirmTv(TextView confirmTv) {
        this.confirmTv = confirmTv;
    }
}
