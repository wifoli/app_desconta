package com.app_desconta.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app_desconta.R;
import com.app_desconta.Usuario;
import com.app_desconta.adapters.ParcelaAdapter;
import com.app_desconta.api.Api;
import com.app_desconta.api.Parcela;
import com.app_desconta.util.RetrofitCliente;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ParcelasFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ArrayList<Parcela> listaParcelas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parcelas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_lista_parcelas);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        llm.setSmoothScrollbarEnabled(true);
        mRecyclerView.setLayoutManager(llm);

        retrofitGetlistaEmpresa();
    }

    private void setarAdapter() {
        ParcelaAdapter adapter = new ParcelaAdapter(getActivity(), listaParcelas);
        mRecyclerView.setAdapter(adapter);
    }

    private String getIdCompra(){
        Bundle extra = getArguments();
        String idCompra = extra.getString("idCompra");
        return idCompra;
    }

    private void retrofitGetlistaEmpresa() {
        Api httpRequest = RetrofitCliente.getCliente().create(Api.class);

        Call<ArrayList<Parcela>> call = httpRequest.getParcela(getIdCompra());

        call.enqueue(new Callback<ArrayList<Parcela>>() {
            @Override
            public void onResponse(Call<ArrayList<Parcela>> call, Response<ArrayList<Parcela>> response) {
                if (response.isSuccessful()) {
                    listaParcelas= response.body();
                    setarAdapter();
                } else Log.e("Retrofit get_Empresa", "Falha no Retrofit Code: " + response.code());
            }

            @Override
            public void onFailure(Call<ArrayList<Parcela>> call, Throwable t) {
                Log.e("Retrofit get_Empresa", "Falha no Retrofit: " + t.toString());
            }
        });
    }

}
