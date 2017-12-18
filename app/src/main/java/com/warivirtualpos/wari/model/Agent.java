package com.warivirtualpos.wari.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hilary on 12/16/17.
 */

public class Agent {

    private int sqliteId;
    @SerializedName("sd_balance")
    private String sdBalance;
    @SerializedName("sd_number")
    private String sdNumber;

    public Agent() {

    }

    public Agent(String sdNumber, String sdBalance) {
        this.sdBalance = sdBalance;
        this.sdNumber = sdNumber;
    }

    public int getSqliteId() {
        return sqliteId;
    }

    public void setSqliteId(int sqliteId) {
        this.sqliteId = sqliteId;
    }

    public String getSdBalance() {
        return sdBalance;
    }

    public void setSdBalance(String sdBalance) {
        this.sdBalance = sdBalance;
    }

    public String getSdNumber() {
        return sdNumber;
    }

    public void setSdNumber(String sdNumber) {
        this.sdNumber = sdNumber;
    }
}
