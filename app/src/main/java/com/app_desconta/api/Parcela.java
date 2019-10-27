package com.app_desconta.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parcela {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nr_parcela")
    @Expose
    private int nrParcela;
    @SerializedName("nr_boleto")
    @Expose
    private String nrBoleto;
    @SerializedName("valor_parcela")
    @Expose
    private String valorParcela;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNrParcela() {
        return nrParcela;
    }

    public void setNrParcela(int nrParcela) {
        this.nrParcela = nrParcela;
    }

    public String getNrBoleto() {
        return nrBoleto;
    }

    public void setNrBoleto(String nrBoleto) {
        this.nrBoleto = nrBoleto;
    }

    public String getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(String valorParcela) {
        this.valorParcela = valorParcela;
    }

    @Override
    public String toString() {
        return "Parcela{" +
                "id='" + id + '\'' +
                ", nrParcela=" + nrParcela +
                ", nrBoleto='" + nrBoleto + '\'' +
                ", valorParcela='" + valorParcela + '\'' +
                '}';
    }
}