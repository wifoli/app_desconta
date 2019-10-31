package com.app_desconta.api;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;
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

    @GET("api/get_empresas/{id}")
    Call<ArrayList<Empresa>> getEmpresas(@Path("id") String id);

    @GET("api/get_compras/{idUsuario}/{idEmpresa}")
    Call<ArrayList<Compras>> getCompras(@Path("idUsuario") String idUsuario, @Path("idEmpresa") String idEmpresa);

    @GET("api/get_parcelas/{idCompra}")
    Call<ArrayList<Parcela>> getParcela(@Path("idCompra") String idCompra);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PATCH("api/pagar_parcela/{idParcela}")
    Call<Void> pagarParcela(@Path("idParcela") String idParcela,
                                      @Body JsonObject jsonPessoa);
}
