package com.warivirtualpos.wari.model;

import com.google.gson.annotations.SerializedName;

import java.security.PrivilegedAction;

/**
 * Created by hilary on 12/16/17.
 */

public class Agent {

    private int sqliteId;
    @SerializedName("sd_balance")
    private int sdBalance;
    @SerializedName("sd_number")
    private String sdNumber;

    private String sdName;

    public Agent() {

    }

    public Agent(String sdNumber, int sdBalance) {
        this.sdBalance = sdBalance;
        this.sdNumber = sdNumber;
    }

    public int getSqliteId() {
        return sqliteId;
    }

    public void setSqliteId(int sqliteId) {
        this.sqliteId = sqliteId;
    }

    public int getSdBalance() {
        return sdBalance;
    }

    public void setSdBalance(int sdBalance) {
        this.sdBalance = sdBalance;
    }

    public String getSdNumber() {
        return sdNumber;
    }

    public void setSdNumber(String sdNumber) {
        this.sdNumber = sdNumber;
    }

    public String getSdName() {
        return sdName;
    }

    public void setSdName(String sdName) {
        this.sdName = sdName;
    }
}
