package com.app_desconta.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app_desconta.api.ApiInfoCompras;
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
    private RecyclerView.Adapter rvAdpt;
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
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(v.getContext());
        rvAdpt = new RecycleViewAdapter(listaCampras);

        rv.setAdapter(rvAdpt);
        rv.setLayoutManager(layoutManager);


        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Test", "onResume");
    }

    public void retrofit(){
        Retrofit client = new Retrofit.Builder()
                .baseUrl("http://192.168.0.129/public/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInfoCompras httpRequest = client.create(ApiInfoCompras.class);

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
