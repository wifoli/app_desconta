package com.app_desconta.util;

import java.text.SimpleDateFormat;

public class FormataData {

    public static String formataData(String data){

        String dataFormatada = data.substring(8,10) + "/" + data.substring(5,7) + "/" + data.substring(0,4);

        return dataFormatada;
    }
}
