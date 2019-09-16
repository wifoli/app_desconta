package com.app_desconta.api;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("compras")
    Call<List<PojoCompra>> getInfCompra();


}