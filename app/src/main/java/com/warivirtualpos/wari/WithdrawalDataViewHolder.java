package com.warivirtualpos.wari;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by hilary on 12/6/17.
 */

public class WithdrawalDataViewHolder extends RecyclerView.ViewHolder {
    private TextView lastnameTv, firstnameTv, phoneTv, confirmationTv;
    public WithdrawalDataViewHolder(View view) {
        super(view);
        lastnameTv = (TextView) view.findViewById(R.id.lastname_text);
        firstnameTv = (TextView) view.findViewById(R.id.firstname_text);
        phoneTv = (TextView) view.findViewById(R.id.phone_text);
        confirmationTv = (TextView) view.findViewById(R.id.confirmation_text);
    }

    public TextView getLastnameTv() {
        return lastnameTv;
    }

    public void setLastnameTv(TextView lastnameTv) {
        this.lastnameTv = lastnameTv;
    }

    public TextView getFirstnameTv() {
        return firstnameTv;
    }

    public void setFirstnameTv(TextView firstnameTv) {
        this.firstnameTv = firstnameTv;
    }

    public TextView getPhoneTv() {
        return phoneTv;
    }

    public void setPhoneTv(TextView phoneTv) {
        this.phoneTv = phoneTv;
    }

    public TextView getConfirmationTv() {
        return confirmationTv;
    }

    public void setConfirmationTv(TextView confirmationTv) {
        this.confirmationTv = confirmationTv;
    }
}
