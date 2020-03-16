package com.example.devizavlt.api;

import com.example.devizavlt.api.model.Arfolyamok;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ValutaInterface {




    @GET("/")
    Call<Arfolyamok> getByBank(@Query("bank")String bankName);


    @GET("/")
    Call<Arfolyamok> getByValuta(@Query("valuta")String valutaName);
}


