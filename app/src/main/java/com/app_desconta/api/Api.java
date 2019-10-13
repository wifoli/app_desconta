package com.app_desconta.api;


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    @GET("cep/find/{cep}/json")
    Call<CEP> buscarCEP(@Path("cep") String cep);

    @GET("api/get_usuarioComUid/{uid}")
    Call<User> getUsuario(@Path("uid") String uid);

    @GET("api/get_usuarioComCpf/{cpf}")
    Call<User> getUsuarioComCpf(@Path("cpf") String cpf);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("api/set_usuario")
    Call<User> criarPessoa(@Body JsonObject  JsonPessoa);
}
