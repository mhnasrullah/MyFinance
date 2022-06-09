package com.example.pampa;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DayResponse {
    @SerializedName("data")
    private ArrayList<DaysModel> data;
    @SerializedName("status")
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public ArrayList<DaysModel> getData() {
        return data;
    }
}
