package com.app_desconta.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app_desconta.R;
import com.app_desconta.Usuario;
import com.app_desconta.api.Api;
import com.app_desconta.api.Compra;
import com.app_desconta.cardView.RecycleViewAdapter;
import com.app_desconta.util.RetrofitCliente;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ComprasFragment extends Fragment {

    private RecyclerView rv;
    private FrameLayout fl;
    private RecycleViewAdapter rvAdpt;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Compra> listaCampras;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_compras, container, false);

        retrofitGetCompras();

        rv = (RecyclerView) v.findViewById(R.id.rv);
        fl = (FrameLayout) v.findViewById(R.id.frameCompras);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(v.getContext());
        rv.setLayoutManager(layoutManager);

        rvAdpt = new RecycleViewAdapter(listaCampras);


        rv.setAdapter(rvAdpt);

        rvAdpt.setOnItemClickListenet(new RecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });

        return v;
    }


    private void retrofitGetCompras() {
        Api httpRequest = RetrofitCliente.getCliente().create(Api.class);

        Call<List<Compra>> call = httpRequest.getCompras(Usuario.getInsance().getUsuario().getPessoa().getId());

        call.enqueue(new Callback<List<Compra>>() {
            @Override
            public void onResponse(Call<List<Compra>> call, Response<List<Compra>> response) {
                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().size(); i++) {

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Compra>> call, Throwable t) {
                Log.e("Retrofit get_compras", "Falha no Retrofit: " + t.toString());
            }
        });
    }

}
