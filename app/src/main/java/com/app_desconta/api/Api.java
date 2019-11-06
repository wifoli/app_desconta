package com.app_desconta.api;

import com.google.gson.JsonObject;

import java.util.ArrayList;

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

    @GET("get_usuarioComUid/{uid}")
    Call<User> getUsuario(@Path("uid") String uid);

    @GET("get_usuarioComCpf/{cpf}")
    Call<User> getUsuarioComCpf(@Path("cpf") String cpf);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("set_usuario/{email}/{uid}")
    Call<User> criarUsuario(@Path("email") String email,
                            @Path("uid") String uid,
                            @Body JsonObject jsonPessoa);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PATCH("update_usuario/{id}/{email}/{uid}")
    Call<User> alterarUsuario(@Path("id") String id,
                              @Path("email") String email,
                              @Path("uid") String uid,
                              @Body JsonObject jsonPessoa);

    @GET("get_empresas/{id}")
    Call<ArrayList<Empresa>> getEmpresas(@Path("id") String id);

    @GET("get_compras/{idUsuario}/{idEmpresa}")
    Call<ArrayList<Compras>> getCompras(@Path("idUsuario") String idUsuario, @Path("idEmpresa") String idEmpresa);

    @GET("get_parcelas/{idCompra}")
    Call<ArrayList<Parcela>> getParcela(@Path("idCompra") String idCompra);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PATCH("pagar_parcela/{idParcela}")
    Call<Void> pagarParcela(@Path("idParcela") String idParcela,
                            @Body JsonObject jsonPessoa);

    @GET("get_cidade/{idCidade}")
    Call<Cidade> getCidade(@Path("idCidade") String idCidade);

    @GET("get_cidades/{idEstado}")
    Call<ArrayList<Cidade>> getCidades(@Path("idEstado") String idEstado);

    @GET("get_cidadeEstado/{idPessoa}")
    Call<CidadeEstado> getCidadeEstado(@Path("idPessoa") String idPessoa);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PATCH("up_pessoa/{idPessoa}")
    Call<Pessoa> atualizarPessoa(@Path("idPessoa") String idPessoa,
                                 @Body JsonObject jsonPessoa);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @PATCH("up_endereco/{idPessoa}")
    Call<Pessoa> atualizarEndereco(@Path("idPessoa") String idPessoa,
                                   @Body JsonObject jsonPessoa);

    @PATCH("gerar_boleto/{idParcela}")
    Call<Parcela> gerarBoleto(@Path("idParcela") String idParcela);

    @GET("get_compras_pagas/{idPessoa}/{idEmpresa}")
    Call<ArrayList<Compras>> getComprasPagas(@Path("idPessoa") String idPessoa,
                                  @Path("idEmpresa") String idEmpresa);

}
