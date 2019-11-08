package com.app_desconta.util;

public class FormataValorEmReal {

    public static String formataValorEmReal(String valor) {
        valor = valor.replaceAll(",", ".");
        String valorFormatado = "";

        if (valor.contains(".")) {
            int posicao = valor.indexOf(".");
            if(posicao + 3 > valor.length()) valor+= "0";
            valorFormatado = valor.substring(0, posicao) + "," + valor.substring(posicao + 1, posicao + 3);
        } else {
            valorFormatado = valor + ",00";
        }
        int tamanhoAntesDaVirgula = valorFormatado.indexOf(",");

        while (tamanhoAntesDaVirgula > 3) {
            valorFormatado = valorFormatado.substring(0, tamanhoAntesDaVirgula - 3) + "." + valorFormatado.substring(tamanhoAntesDaVirgula - 3);
            tamanhoAntesDaVirgula = tamanhoAntesDaVirgula - 3;
        }
        return valorFormatado;
    }
}