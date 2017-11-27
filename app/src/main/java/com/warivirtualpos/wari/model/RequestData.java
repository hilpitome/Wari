package com.warivirtualpos.wari.model;

/**
 * Created by hilary on 11/26/17.
 */

public class RequestData {
    String date, senderLastName, senderFirstname, senderPhone, beneficiaryLastname, beneficiaryFirstname, beneficiaryPhone;
    int amount;
    public RequestData(){}
    public RequestData(String date, String senderLastName, String senderFirstname, String senderPhone, int amount, String beneficiaryLastname,
                        String beneficiaryFirstname, String beneficiaryPhone){
        this.date = date;
        this.senderLastName = senderLastName;
        this.senderFirstname = senderFirstname;
        this.senderPhone = senderPhone;
        this.amount = amount;
        this.beneficiaryLastname = beneficiaryLastname;
        this.beneficiaryFirstname = beneficiaryFirstname;
        this.beneficiaryPhone = beneficiaryPhone;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSenderLastName() {
        return senderLastName;
    }

    public void setSenderLastName(String senderLastName) {
        this.senderLastName = senderLastName;
    }

    public String getSenderFirstname() {
        return senderFirstname;
    }

    public void setSenderFirstname(String senderFirstname) {
        this.senderFirstname = senderFirstname;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getBeneficiaryLastname() {
        return beneficiaryLastname;
    }

    public void setBeneficiaryLastname(String beneficiaryLastname) {
        this.beneficiaryLastname = beneficiaryLastname;
    }

    public String getBeneficiaryFirstname() {
        return beneficiaryFirstname;
    }

    public void setBeneficiaryFirstname(String beneficiaryFirstname) {
        this.beneficiaryFirstname = beneficiaryFirstname;
    }

    public String getBeneficiaryPhone() {
        return beneficiaryPhone;
    }

    public void setBeneficiaryPhone(String beneficiaryPhone) {
        this.beneficiaryPhone = beneficiaryPhone;
    }
}
