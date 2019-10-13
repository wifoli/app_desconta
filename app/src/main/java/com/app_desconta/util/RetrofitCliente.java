package com.app_desconta.util;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCliente {

    public static Retrofit getCliente(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit client = new Retrofit.Builder()
                .baseUrl("http://192.168.0.129/public/").client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return client;
    }
}
