package com.example.pampa;

import com.google.gson.annotations.SerializedName;

public class AddTodayResponse {

    @SerializedName("msg")
    private String msg;
    @SerializedName("status")
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
