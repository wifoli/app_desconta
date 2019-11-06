package com.app_desconta.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app_desconta.R;
import com.app_desconta.Usuario;
import com.app_desconta.adapters.EmpresaDescontoAdapter;
import com.app_desconta.api.Api;
import com.app_desconta.api.Empresa;
import com.app_desconta.util.RetrofitCliente;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DescontoFragment extends Fragment {

    private TextView totalDesconto;
    private TextView nomeEmpresa;
    private TextView porcentagemDesconto;

    private RecyclerView mRecyclerViewEmpresa;
    private ArrayList<Empresa> listaEmpresa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_desconto, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        totalDesconto = (TextView) view.findViewById(R.id.descricao_valor_desconto);
        nomeEmpresa = (TextView) view.findViewById(R.id.nome_empresa_desconto);
        porcentagemDesconto = (TextView) view.findViewById(R.id.porcentagem_desconto) ;

        mRecyclerViewEmpresa = (RecyclerView) view.findViewById(R.id.rv_empresa_desconto);
        mRecyclerViewEmpresa.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        mRecyclerViewEmpresa.setLayoutManager(llm);

        retrofitGetlistaEmpresa();
    }

    private void setarAdapter() {
        EmpresaDescontoAdapter adapter = new EmpresaDescontoAdapter(getActivity(), listaEmpresa);
        mRecyclerViewEmpresa.setAdapter(adapter);

        adapter.setOnItemClickListenet(new EmpresaDescontoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                nomeEmpresa.setText(listaEmpresa.get(position).getNomeFantasia());
                porcentagemDesconto.setText("" + listaEmpresa.get(position).getPorcentagemDesc() + "%");
            //    retrofitGetCompras();
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
