package com.warivirtualpos.wari.model;

/**
 * Created by hilary on 11/26/17.
 */

public class RequestData extends MainObject{
    private String date, senderLastName, senderFirstname, senderPhone, beneficiaryLastname,
            beneficiaryFirstname, beneficiaryPhone, status, confirmation ="";
    int amount, sqliteId;
    public RequestData(){}
    public RequestData(String date, String senderLastName, String senderFirstname, String senderPhone, int amount, String beneficiaryLastname,
                        String beneficiaryFirstname, String beneficiaryPhone, String status){
        this.date = date;
        this.senderLastName = senderLastName;
        this.senderFirstname = senderFirstname;
        this.senderPhone = senderPhone;
        this.amount = amount;
        this.beneficiaryLastname = beneficiaryLastname;
        this.beneficiaryFirstname = beneficiaryFirstname;
        this.beneficiaryPhone = beneficiaryPhone;
        this.status = status;

    }

    public int getSqliteId() {
        return sqliteId;
    }

    public void setSqliteId(int sqliteId) {
        this.sqliteId = sqliteId;
    }
    @Override
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }
}
