package com.app_desconta.api;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    @GET("cep/find/{cep}/json")
    Call<CEP> buscarCEP(@Path("cep") String cep);

    @GET("get_usuarioComUid/{uid}")
    Call<User> getUsuario(@Path("uid") String uid);

    @GET("get_usuarioComCpf/{cpf}")
    Call<User> getUsuarioComCpf(@Path("cpf") String cpf);

    @FormUrlEncoded
    @POST("set_usuario")
    Call<Pessoa> criarUsuario(@Field("nome") String nome,
                            @Field("sobrenome") String sobrenome,
                            @Field("rg") String rg,
                            @Field("cpf") String cpf,
                            @Field("dataNasc") String dataNasc,
                            @Field("telefone1") String telefone1,
                            @Field("telefone2") String telefone2,
                            @Field("rua") String rua,
                            @Field("bairro") String bairro,
                            @Field("numero") String numero,
                            @Field("cep") String cep,
                            @Field("complemento") String complemento,
                            @Field("cidade") String cidade);
}