package com.app_desconta.api;


import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
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
    @POST("api/set_usuario/{email}/{uid}")
    Call<User> criarUsuario(@Path("email") String email,
                            @Path("uid") String uid,
                            @Body JsonObject jsonPessoa);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PATCH("api/update_usuario/{id}/{email}/{uid}")
    Call<User> alterarUsuario(@Path("id") String id,
                              @Path("email") String email,
                              @Path("uid") String uid,
                              @Body JsonObject jsonPessoa);

    @GET("api/get_compras/{id}")
    Call<List<Compra>> getCompras(@Path("id") String id);
}
