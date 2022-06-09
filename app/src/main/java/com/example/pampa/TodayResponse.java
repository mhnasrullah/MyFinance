package com.example.pampa;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TodayResponse {
    @SerializedName("data")
    private ArrayList<TodayModel> data;
    @SerializedName("status")
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public ArrayList<TodayModel> getData() {
        return data;
    }
}
