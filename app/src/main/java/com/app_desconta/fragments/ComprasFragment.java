package com.app_desconta.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app_desconta.R;
import com.app_desconta.Usuario;
import com.app_desconta.api.Api;
import com.app_desconta.api.Compra;
import com.app_desconta.cardView.RecycleViewAdapter;
import com.app_desconta.util.RetrofitCliente;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ComprasFragment extends Fragment {

    private FrameLayout frameLayoutDetalhes;

    private RecyclerView rv;
    private RecycleViewAdapter rvAdpt;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Compra> listaCampras;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_compras, container, false);
        retrofitGetCompras();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        frameLayoutDetalhes = (FrameLayout) v.findViewById(R.id.frameDetalhes);

        rv = (RecyclerView) v.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);

    }

    private void setarAdapter(){
        rvAdpt = new RecycleViewAdapter(getActivity(), listaCampras);


        rv.setAdapter(rvAdpt);

        rvAdpt.setOnItemClickListenet(new RecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("test", listaCampras.get(position).getValorTotal());
                Fragment fr = new DetalhesCompraFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frameDetalhes, fr);
                fragmentTransaction.commit();
                frameLayoutDetalhes.setVisibility(FrameLayout.VISIBLE);
            }
        });
    }

    private void retrofitGetCompras() {
        Api httpRequest = RetrofitCliente.getCliente().create(Api.class);

        Call<ArrayList<Compra>> call = httpRequest.getCompras(Usuario.getInsance().getUsuario().getPessoa().getId());

        call.enqueue(new Callback<ArrayList<Compra>>() {
            @Override
            public void onResponse(Call<ArrayList<Compra>> call, Response<ArrayList<Compra>> response) {
                if( response.isSuccessful()){
                    listaCampras = response.body();
                    setarAdapter();
                }else Log.e("Retrofit get_compras", "Falha no Retrofit Code: " + response.code());
            }

            @Override
            public void onFailure(Call<ArrayList<Compra>> call, Throwable t) {
                Log.e("Retrofit get_compras", "Falha no Retrofit: " + t.toString());
            }
        });
    }

}
