package com.app_desconta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app_desconta.api.Api;
import com.app_desconta.api.Compras;
import com.app_desconta.cardView.RecycleViewAdapter;
import com.app_desconta.util.RetrofitCliente;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComprasActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewDetalhesCompraToolbar;

    private Button botaoVoltar;

    private RecyclerView rv;
    private RecycleViewAdapter rvAdpt;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Compras> listaCampras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras);

        textViewDetalhesCompraToolbar = (TextView) findViewById(R.id.tv_detalhes_compra);
        textViewDetalhesCompraToolbar.setText(getString(R.string.compras));
        botaoVoltar = (Button) findViewById(R.id.bt_voltar);

        botaoVoltar.setOnClickListener(this);

        retrofitGetCompras();

        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getBaseContext());
        rv.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View view) {
        onBackPressed();
    }

    private void setarAdapter(){
        rvAdpt = new RecycleViewAdapter(getBaseContext(), listaCampras);


        rv.setAdapter(rvAdpt);

        rvAdpt.setOnItemClickListenet(new RecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("test", listaCampras.get(position).getValorTotal());
                Intent intent = new Intent(getBaseContext(), DetalhesCompraActivity.class);
                Bundle extras = new Bundle();
                extras.putString("id", listaCampras.get(position).getId());
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    private void retrofitGetCompras() {
        Api httpRequest = RetrofitCliente.getCliente().create(Api.class);

        Call<ArrayList<Compras>> call = httpRequest.getCompras(Usuario.getInsance().getUsuario().getPessoa().getId());

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
    }
}
