package com.example.pampa;

import com.google.gson.annotations.SerializedName;

public class DaysModel {
    @SerializedName("tanggal")
    private String tanggal;
    @SerializedName("balance")
    private String balance;
    @SerializedName("spend")
    private int spend;
    @SerializedName("income")
    private int income;
    @SerializedName("status")
    private boolean status;

    public String getTanggal() {
        return tanggal;
    }
    public String getBalance() {
        return balance;
    }
    public int getIncome() {
        return income;
    }

    public int getSpend() {
        return spend;
    }
    public boolean isStatus() {
        return status;
    }
}
