package com.app_desconta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app_desconta.api.Api;
import com.app_desconta.api.Compras;
import com.app_desconta.util.RetrofitCliente;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalhesCompraActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewDetalhesCompraToolbar;
    private TextView nome_fantasia;
    private TextView data_compra;
    private TextView valor_compra;

    private Button botaoVoltar;

    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_compra);

        textViewDetalhesCompraToolbar = (TextView) findViewById(R.id.tv_detalhes_compra);
        nome_fantasia= (TextView) findViewById(R.id.nome_fantasia_detalhe);
        data_compra = (TextView) findViewById(R.id.data_compra_detalhes);
        valor_compra = (TextView) findViewById(R.id.valor_detalhes);

        botaoVoltar = (Button) findViewById(R.id.bt_voltar);

        textViewDetalhesCompraToolbar.setText(getString(R.string.detalhes_compra));

        Intent intent = getIntent();
        extras = intent.getExtras();

        setarCompra();

        botaoVoltar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        onBackPressed();
    }

    private void setarCompra(){
        nome_fantasia.setText(" " + extras.getString("nomeEmpresa"));
        data_compra.setText(" " + extras.getString("data"));
        valor_compra.setText(" " + extras.getString("valor"));
    }

    private String idCompra(){
        String id = extras.getString("id");
        return id;
    }

   /* private void retrofitGetCompra() {
        Api httpRequest = RetrofitCliente.getCliente().create(Api.class);

        Call<Compra> call = httpRequest.getCompra(idCompra());

        call.enqueue(new Callback<ArrayList<Compras>>() {
            @Override
            public void onResponse(Call<ArrayList<Compras>> call, Response<ArrayList<Compras>> response) {
                if( response.isSuccessful()){
                    listaCampras = response.body();
                    setarAdapter();
                }else Log.e("Retrofit get_compras", "Falha no Retrofit Code: " + response.code());
            }

            @Override
            public void onFailure(Call<ArrayList<Compras>> call, Throwable t) {
                Log.e("Retrofit get_compras", "Falha no Retrofit: " + t.toString());
            }
        });
    }  */
}
