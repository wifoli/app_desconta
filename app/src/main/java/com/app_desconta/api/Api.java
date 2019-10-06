package com.app_desconta.api;


import com.app_desconta.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Api {

    @GET("compras")
    Call<List<PojoCompra>> getInfCompra();

    @GET("get_usuarioComUid/{uid}")
    Call<User> getUsuario(@Path("uid") String uid);

    @GET("get_usuarioComCpf/{cpf}")
    Call<User> getUsuarioComCpf(@Path("cpf") String cpf);
}