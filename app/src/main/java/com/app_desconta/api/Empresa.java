package com.app_desconta.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Empresa {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("razao_social")
    @Expose
    private String razaoSocial;
    @SerializedName("nome_fantasia")
    @Expose
    private String nomeFantasia;
    @SerializedName("cnpj")
    @Expose
    private String cnpj;
    @SerializedName("inscricao_est")
    @Expose
    private String inscricaoEst;
    @SerializedName("porcentagem_desc")
    @Expose
    private int porcentagemDesc;
    @SerializedName("tel")
    @Expose
    private String tel;
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
    @SerializedName("cidade_id")
    @Expose
    private int cidadeId;
    @SerializedName("status")
    @Expose
    private boolean status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEst() {
        return inscricaoEst;
    }

    public void setInscricaoEst(String inscricaoEst) {
        this.inscricaoEst = inscricaoEst;
    }

    public int getPorcentagemDesc() {
        return porcentagemDesc;
    }

    public void setPorcentagemDesc(int porcentagemDesc) {
        this.porcentagemDesc = porcentagemDesc;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public int getCidadeId() {
        return cidadeId;
    }

    public void setCidadeId(int cidadeId) {
        this.cidadeId = cidadeId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "id='" + id + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", inscricaoEst='" + inscricaoEst + '\'' +
                ", porcentagemDesc=" + porcentagemDesc +
                ", tel='" + tel + '\'' +
                ", rua='" + rua + '\'' +
                ", bairro='" + bairro + '\'' +
                ", numero='" + numero + '\'' +
                ", cep='" + cep + '\'' +
                ", complemento='" + complemento + '\'' +
                ", cidadeId=" + cidadeId +
                ", status=" + status +
                '}';
    }
}