package com.app_desconta.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Compra {


    @SerializedName("data_venda")
    @Expose
    private String dataVenda;
    @SerializedName("valor_total")
    @Expose
    private String valorTotal;
    @SerializedName("nome_fantasia")
    @Expose
    private String nomeFantasia;

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

}