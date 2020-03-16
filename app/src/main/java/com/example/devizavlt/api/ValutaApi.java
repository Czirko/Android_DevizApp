package com.example.devizavlt.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ValutaApi {

    public static final String API_URL= "http://api.napiarfolyam.hu";
    private static ValutaApi mIstance;
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;

    private ValutaApi(){

         retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(new OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

    }

    public static synchronized ValutaApi getInstance(){
        if (mIstance == null){
            mIstance = new ValutaApi();
        }
        return mIstance;
    }

    public ValutaInterface getApi(){
        return retrofit.create(ValutaInterface.class);
    }

}
