package com.app_desconta.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import com.app_desconta.R;

public class Util {

    public static boolean verificaConexao(Context contexto) {
        boolean status = false;
        ConnectivityManager conexao = (ConnectivityManager) contexto.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conexao != null) {
            // PARA DISPOSTIVOS NOVOS
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NetworkCapabilities recursosRede = conexao.getNetworkCapabilities(conexao.getActiveNetwork());

                if (recursosRede != null) {//VERIFICAMOS SE TEM ALGO
                    if (recursosRede.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        //VERIFICAMOS SE DISPOSITIVO TEM 3G
                        return true;
                    } else if (recursosRede.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        //VERIFICAMOS SE DISPOSITIVO TEM WIFFI
                        return true;
                    }
                    //NÃO POSSUI UMA CONEXAO DE REDE VÁLIDA
                    Toast.makeText(contexto, contexto.getString(R.string.redeInvalida), Toast.LENGTH_LONG).show();
                    return false;
                }

            } else {
                // PARA DISPOSTIVOS ANTIGOS  (PRECAUÇÃO)
                NetworkInfo informacao = conexao.getActiveNetworkInfo();

                if (informacao != null && informacao.isConnected()) status = true;
                else {
                    Toast.makeText(contexto, contexto.getString(R.string.semConexaoComInternet), Toast.LENGTH_LONG).show();
                    status = false;
                }

                return status;
            }
        }
        Toast.makeText(contexto, contexto.getString(R.string.semConexaoComInternet), Toast.LENGTH_LONG).show();
        return false;
    }

    public static void errosFirebase(Context contexto, String excessao) {
        if (excessao.contains("There is no user"))
            Toast.makeText(contexto, contexto.getString(R.string.emailNaoCadastrado), Toast.LENGTH_LONG).show();
        else if (excessao.contains("least 6 characters"))
            Toast.makeText(contexto, contexto.getString(R.string.senhaComMenosDeSeisDigitos), Toast.LENGTH_LONG).show();
        else if (excessao.contains("address is already"))
            Toast.makeText(contexto, contexto.getString(R.string.emailJaCadastrado), Toast.LENGTH_LONG).show();
        else if (excessao.contains("address is badly"))
            Toast.makeText(contexto, contexto.getString(R.string.emailInvalido), Toast.LENGTH_LONG).show();
        else if (excessao.contains("password is invalid"))
            Toast.makeText(contexto, contexto.getString(R.string.senhaInvalida), Toast.LENGTH_LONG).show();
        else if (excessao.contains("interrupted connection"))
            Toast.makeText(contexto, contexto.getString(R.string.semConexaoComFirebase), Toast.LENGTH_LONG).show();
        else if (excessao.contains("INVALID_EMAIL"))
            Toast.makeText(contexto, contexto.getString(R.string.emailInvalido), Toast.LENGTH_LONG).show();
        else if (excessao.contains("EMAIL_NOT_FOUND"))
            Toast.makeText(contexto, contexto.getString(R.string.emailNaoCadastrado), Toast.LENGTH_LONG).show();
        else Toast.makeText(contexto, excessao, Toast.LENGTH_LONG).show();
    }
}