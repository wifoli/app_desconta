package com.app_desconta.util;

import com.app_desconta.api.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCliente {

    public static Retrofit getCliente(){
        Retrofit client = new Retrofit.Builder()
                .baseUrl("http://192.168.0.129/public/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return client;
    }
}
