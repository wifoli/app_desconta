package com.app_desconta.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import com.app_desconta.R;

public class verificaConexao {

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
}
