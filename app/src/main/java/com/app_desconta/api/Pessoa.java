package com.app_desconta.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pessoa {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nome")
    @Expose
    private String nome;
    @SerializedName("sobrenome")
    @Expose
    private String sobrenome;
    @SerializedName("cpf")
    @Expose
    private String cpf;
    @SerializedName("rg")
    @Expose
    private String rg;
    @SerializedName("data_nasc")
    @Expose
    private String dataNasc;
    @SerializedName("tel_1")
    @Expose
    private String tel1;
    @SerializedName("tel_2")
    @Expose
    private String tel2;
    @SerializedName("rua")
    @Expose
    private String rua;
    @SerializedName("bairro")
    @Expose
    private String bairro;
    @SerializedName("numero")
    @Expose
    private String numero;
    @SerializedName("cep")
    @Expose
    private String cep;
    @SerializedName("complemento")
    @Expose
    private String complemento;
    @SerializedName("cidade")
    @Expose
    private String cidade;
    @SerializedName("estado")
    @Expose
    private String estado;

    public Pessoa(String nome, String sobrenome, String cpf, String rg, String dataNasc, String tel1, String tel2, String rua,
                  String bairro, String numero, String cep, String complemento, String cidade, String estado) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.rg = rg;
        this.dataNasc = dataNasc;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
        this.cep = cep;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(String dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}