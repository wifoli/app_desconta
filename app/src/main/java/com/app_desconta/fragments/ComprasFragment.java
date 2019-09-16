package com.app_desconta.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.app_desconta.api.Api;
import com.app_desconta.api.PojoCompra;
import com.app_desconta.R;
import com.app_desconta.cardView.RecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ComprasFragment extends Fragment {

    private RecyclerView rv;
    private FrameLayout fl;
    private RecycleViewAdapter rvAdpt;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<PojoCompra> listaCampras = new ArrayList<>();

    public ComprasFragment() {
        retrofit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_compras, container, false);

        rv = (RecyclerView) v.findViewById(R.id.rv);
        fl = (FrameLayout) v.findViewById(R.id.frameCompras);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(v.getContext());
        rvAdpt = new RecycleViewAdapter(listaCampras);

        rv.setAdapter(rvAdpt);
        rv.setLayoutManager(layoutManager);
        rvAdpt.setOnItemClickListenet(new RecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("String", listaCampras.get(position).getValorTotal());
                Fragment fr = new DetalhesCompraFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frameCompras, fr);
                fragmentTransaction.commit();
                interacaoEntreView(true);
            }
        });

        return v;
    }

    private void interacaoEntreView(boolean frameDetailsVisible) {
        if(frameDetailsVisible){
            rv.setVisibility(View.INVISIBLE);
            fl.setVisibility(View.VISIBLE);
        }else{
            rv.setVisibility(View.VISIBLE);
            fl.setVisibility(View.INVISIBLE);
        }
    }

    private void retrofit() {
        Retrofit client = new Retrofit.Builder()
                .baseUrl("http://192.168.0.129/public/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api httpRequest = client.create(Api.class);

        Call<List<PojoCompra>> call = httpRequest.getInfCompra();

        call.enqueue(callback);
    }

    private Callback<List<PojoCompra>> callback = new Callback<List<PojoCompra>>() {
        @Override
        public void onResponse(Call<List<PojoCompra>> call, Response<List<PojoCompra>> response) {

            for (int i = 0; i < response.body().size(); i++) {
                listaCampras.add(response.body().get(i));
            }

        }

        @Override
        public void onFailure(Call<List<PojoCompra>> call, Throwable t) {
            Log.e("Retrofit Compras", "Falha no Retrofit: " + t.toString());
        }
    };

}
