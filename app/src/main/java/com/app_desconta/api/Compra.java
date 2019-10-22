package com.app_desconta.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Compra {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("data_venda")
    @Expose
    private String dataVenda;
    @SerializedName("qtde_parcelas")
    @Expose
    private int qtdeParcelas;
    @SerializedName("valor_total")
    @Expose
    private String valorTotal;
    @SerializedName("nome_fantasia")
    @Expose
    private String nomeFantasia;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    public int getQtdeParcelas() {
        return qtdeParcelas;
    }

    public void setQtdeParcelas(int qtdeParcelas) {
        this.qtdeParcelas = qtdeParcelas;
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

    @Override
    public String toString() {
        return "Compra{" +
                "id='" + id + '\'' +
                ", dataVenda='" + dataVenda + '\'' +
                ", qtdeParcelas=" + qtdeParcelas +
                ", valorTotal='" + valorTotal + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                '}';
    }
}