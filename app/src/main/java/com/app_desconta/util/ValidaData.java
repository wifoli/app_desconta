package com.app_desconta.util;

import android.content.Context;
import android.widget.Toast;

import com.app_desconta.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidaData {

    public static boolean isData(Context context, String data) {
        SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
        Date data_atual = new Date();
        String dataAtualFormatada = formataData.format(data_atual).replaceAll("[-]","");

        Integer dia_aniversario = Integer.parseInt(data.substring(0, 2));
        Integer mes_aniversario = Integer.parseInt(data.substring(2, 4));
        Integer ano_aniversario = Integer.parseInt(data.substring(4));

        Integer dia_atual = Integer.parseInt(dataAtualFormatada.substring(0, 2));
        Integer mes_atual = Integer.parseInt(dataAtualFormatada.substring(2, 4));
        Integer ano_atual = Integer.parseInt(dataAtualFormatada.substring(4));

        if (!dataValida(dia_aniversario, mes_aniversario, ano_aniversario, ano_atual)) return false;


        if (ano_atual - 18 > ano_aniversario) return true;
        else if (ano_atual - 18 == ano_aniversario) {
            if (mes_atual - mes_aniversario > 0) return true;
            else if (mes_atual - mes_aniversario == 0) {
                if (dia_atual - dia_aniversario >= 0) return true;
            }
        }

        Toast.makeText(context, context.getString(R.string.menorIdade), Toast.LENGTH_LONG).show();
        return false;
    }

    private static boolean dataValida(Integer dia, Integer mes, Integer ano, Integer ano_atual) {
        if (ano > ano_atual) return false;
        if (ano < 1920) return false;
        if (mes > 12) return false;
        if ((mes == 1) || (mes == 3) || (mes == 5) || (mes == 7) || (mes == 8) || (mes == 10) || (mes == 12)) {
            if (dia > 31) return false;
        } else if ((mes == 2) && (ano % 4 == 0) && (dia >29)) return false;
        else if ((mes == 2) && (ano % 4 != 0) && (dia > 28)) return false;
        else if (dia > 30) return false;
        return true;
    }
}
