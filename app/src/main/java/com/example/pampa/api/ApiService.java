package com.example.pampa.api;

import com.example.pampa.AddTodayResponse;
import com.example.pampa.DayResponse;
import com.example.pampa.TodayModel;
import com.example.pampa.TodayResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("{uid}/today")
    Call<TodayResponse> getToday(@Path("uid") String uid);

    @GET("{uid}/days")
    Call<DayResponse> getDays(@Path("uid") String uid);

//    POST www.localhost.com/uid/today
    @POST("{uid}/today")
    @FormUrlEncoded
    Call<AddTodayResponse> addToday(
            @Path("uid") String uid,
            @Field("title") String title,
            @Field("amount") int amount,
            @Field("status") boolean status
//            @Body TodayModel model
            );
}
