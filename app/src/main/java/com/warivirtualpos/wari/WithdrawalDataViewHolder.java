package com.warivirtualpos.wari;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by hilary on 12/6/17.
 */

public class WithdrawalDataViewHolder extends RecyclerView.ViewHolder {
    private TextView agentNumberTv,lastnameTv, firstnameTv, phoneTv, confirmationTv, statusTv;
    public WithdrawalDataViewHolder(View view) {
        super(view);
        agentNumberTv = (TextView) view.findViewById(R.id.agent_number_text);
        lastnameTv = (TextView) view.findViewById(R.id.lastname_text);
        firstnameTv = (TextView) view.findViewById(R.id.firstname_text);
        phoneTv = (TextView) view.findViewById(R.id.phone_text);
        confirmationTv = (TextView) view.findViewById(R.id.confirmation_text);
        statusTv = (TextView) view.findViewById(R.id.validation_txt);
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

    public TextView getStatusTv() {
        return statusTv;
    }

    public void setStatusTv(TextView statusTv) {
        this.statusTv = statusTv;
    }

    public TextView getAgentNumberTv() {
        return agentNumberTv;
    }

    public void setAgentNumberTv(TextView agentNumberTv) {
        this.agentNumberTv = agentNumberTv;
    }
}
