package com.example.pampa;

import com.google.gson.annotations.SerializedName;

public class TodayModel {

    @SerializedName("title")
    private String title;
    @SerializedName("amount")
    private int amount;
    @SerializedName("status")
    private boolean status;

    public int getAmount() {
        return amount;
    }

    public String getTitle() {
        return title;
    }

    public boolean isStatus() {
        return status;
    }
}
