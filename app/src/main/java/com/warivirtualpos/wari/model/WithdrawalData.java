package com.warivirtualpos.wari.model;

/**
 * Created by hilary on 11/26/17.
 */

public class WithdrawalData {
    String date, lastname, firstname, phone, confirmation;
    int sqliteId;
    public WithdrawalData(){
    }
    public WithdrawalData(String date, String lastname, String firstname, String phone, String confirmation){
        this.sqliteId = sqliteId;
        this.date = date;
        this.lastname = lastname;
        this.firstname = firstname;
        this.phone = phone;
        this.confirmation = confirmation;

    }

    public int getSqliteId() {
        return sqliteId;
    }

    public void setSqliteId(int sqliteId) {
        this.sqliteId = sqliteId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }
}
