package com.app_desconta.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app_desconta.ComprasActivity;
import com.app_desconta.R;
import com.app_desconta.Usuario;
import com.app_desconta.adapters.EmpresaAdapter;
import com.app_desconta.api.Api;
import com.app_desconta.api.Empresa;
import com.app_desconta.util.RetrofitCliente;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmpresaFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ArrayList<Empresa> listaEmpresa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_empresa, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list_empresa);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        retrofitGetlistaEmpresa();
    }

    private void setarAdapter() {
        EmpresaAdapter adapter = new EmpresaAdapter(getActivity(), listaEmpresa);
        mRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListenet(new EmpresaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("test", listaEmpresa.get(position).getTel());
                Intent intent = new Intent(getActivity(), ComprasActivity.class);
                Bundle extras = new Bundle();
                extras.putString("idEmpresa", listaEmpresa.get(position).getId());
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    private void retrofitGetlistaEmpresa() {
        Api httpRequest = RetrofitCliente.getCliente().create(Api.class);

        Call<ArrayList<Empresa>> call = httpRequest.getEmpresas(Usuario.getInsance().getUsuario().getPessoa().getId());

        call.enqueue(new Callback<ArrayList<Empresa>>() {
            @Override
            public void onResponse(Call<ArrayList<Empresa>> call, Response<ArrayList<Empresa>> response) {
                if (response.isSuccessful()) {
                    listaEmpresa = response.body();
                    setarAdapter();
                } else Log.e("Retrofit get_Empresa", "Falha no Retrofit Code: " + response.code());
            }

            @Override
            public void onFailure(Call<ArrayList<Empresa>> call, Throwable t) {
                Log.e("Retrofit get_Empresa", "Falha no Retrofit: " + t.toString());
            }
        });
    }
}