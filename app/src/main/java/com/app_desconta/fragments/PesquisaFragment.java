package com.app_desconta.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app_desconta.R;
import com.app_desconta.adapters.EmpresaFiltroAdapter;
import com.app_desconta.api.Api;
import com.app_desconta.api.Cidade;
import com.app_desconta.api.Empresa;
import com.app_desconta.util.RetrofitCliente;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PesquisaFragment extends Fragment implements View.OnClickListener {

    private Spinner spinnerFiltro;
    private Spinner spinnerEstado;
    private Spinner spinnerCidade;
    private LinearLayout linearDescricao;
    private LinearLayout linearDescricaoCiadde;
    private ImageView btnPesquisar;
    private ImageView btnPesquisarComDescricao;
    private EditText editTextDescricao;

    private Map posicaoEstado;


    private RecyclerView mRecyclerView;

    private ArrayList<Empresa> listaEmpresa;
    private ArrayList<Cidade> arrayCidade;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pesquisa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinnerFiltro = (Spinner) view.findViewById(R.id.sp_filtro);
        spinnerEstado = (Spinner) view.findViewById(R.id.sp_pesquisa_estado);
        spinnerCidade = (Spinner) view.findViewById(R.id.sp_pesquisa_cidade);
        linearDescricao = (LinearLayout) view.findViewById(R.id.linearDescrcao);
        linearDescricaoCiadde = (LinearLayout) view.findViewById(R.id.linearFiltroCidade);
        btnPesquisar = (ImageView) view.findViewById(R.id.imageViewPesquisar);
        btnPesquisarComDescricao = (ImageView) view.findViewById(R.id.imageViewPesquisarComDescrcao);
        editTextDescricao = (EditText) view.findViewById(R.id.et_descricao);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_filtro_empresa);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        btnPesquisar.setOnClickListener(this);
        btnPesquisarComDescricao.setOnClickListener(this);

        spinnerFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    linearDescricao.setVisibility(LinearLayout.GONE);
                    btnPesquisar.setVisibility(ImageView.VISIBLE);
                    linearDescricaoCiadde.setVisibility(LinearLayout.VISIBLE);
                } else if (position == 1 || position == 2) {
                    linearDescricao.setVisibility(LinearLayout.VISIBLE);
                    btnPesquisar.setVisibility(ImageView.INVISIBLE);
                    linearDescricaoCiadde.setVisibility(LinearLayout.GONE);
                } else {
                    linearDescricaoCiadde.setVisibility(LinearLayout.GONE);
                    linearDescricao.setVisibility(LinearLayout.GONE);
                    btnPesquisar.setVisibility(ImageView.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                preencherCidades(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        retrofitGetlistaEmpresa(4, "vazio");
        setarArrayAdapterFiltro();
        setarArrayAdapterEstado();
        povoarMapPosicaoEstado();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewPesquisar:
                if (spinnerFiltro.getSelectedItemId() == 0) {
                    if (spinnerCidade.getSelectedItemPosition() != -1){
                        retrofitGetlistaEmpresa(1, "" + arrayCidade.get(spinnerCidade.getSelectedItemPosition()).getId());
                    }else {
                        Toast.makeText(getActivity(), getString(R.string.selecione_uma_cidade), Toast.LENGTH_LONG).show();
                    }
                } else if (spinnerFiltro.getSelectedItemId() == 3) {
                    retrofitGetlistaEmpresa(4, "vazio");
                }
                break;
            case R.id.imageViewPesquisarComDescrcao:
                if (spinnerFiltro.getSelectedItemId() == 1) {
                    retrofitGetlistaEmpresa(2, editTextDescricao.getText().toString().trim());
                } else if (spinnerFiltro.getSelectedItemId() == 2) {
                    retrofitGetlistaEmpresa(3, editTextDescricao.getText().toString().trim());
                }
                break;
        }
    }

    private void setarAdapter() {
        EmpresaFiltroAdapter adapter = new EmpresaFiltroAdapter(getActivity(), listaEmpresa);
        mRecyclerView.setAdapter(adapter);

    }

    private void setarArrayAdapterEstado() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.estados, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapter);
    }

    private void setarArrayAdapterFiltro() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.filtro_pesquisa, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFiltro.setAdapter(adapter);
    }

    private void setarArrayAdapterCidade() {
        ArrayList<String> arrayList = new ArrayList<>();

        for (Cidade cidade : arrayCidade) {
            arrayList.add(cidade.getNome());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
        spinnerCidade.setAdapter(adapter);

    }

    private void retrofitGetlistaEmpresa(int idFiltro, String idValor) {
        Api httpRequest = RetrofitCliente.getCliente().create(Api.class);

        Call<ArrayList<Empresa>> call = httpRequest.getEmpresasFiltro(idFiltro, idValor);

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

    private void preencherCidades(int idEstado) {

        Api httpRequest = RetrofitCliente.getCliente().create(Api.class);

        Call<ArrayList<Cidade>> call = httpRequest.getCidades("" + idEstado);
        call.enqueue(new Callback<ArrayList<Cidade>>() {
            @Override
            public void onResponse(Call<ArrayList<Cidade>> call, Response<ArrayList<Cidade>> response) {
                arrayCidade = response.body();
                setarArrayAdapterCidade();
            }

            @Override
            public void onFailure(Call<ArrayList<Cidade>> call, Throwable t) {
                Log.e("Retrofit get_cidades", "Falha no Retrofit: " + t.toString());
            }
        });
    }

    private void povoarMapPosicaoEstado() {
        posicaoEstado = new HashMap<String, Integer>();
        posicaoEstado.put("", 0);
        posicaoEstado.put("AC", 1);
        posicaoEstado.put("AL", 2);
        posicaoEstado.put("AP", 3);
        posicaoEstado.put("AM", 4);
        posicaoEstado.put("BA", 5);
        posicaoEstado.put("CE", 6);
        posicaoEstado.put("DF", 7);
        posicaoEstado.put("ES", 8);
        posicaoEstado.put("GO", 9);
        posicaoEstado.put("MA", 10);
        posicaoEstado.put("MT", 11);
        posicaoEstado.put("MS", 12);
        posicaoEstado.put("MG", 13);
        posicaoEstado.put("PA", 14);
        posicaoEstado.put("PB", 15);
        posicaoEstado.put("PR", 16);
        posicaoEstado.put("PE", 17);
        posicaoEstado.put("PI", 18);
        posicaoEstado.put("RJ", 19);
        posicaoEstado.put("RN", 20);
        posicaoEstado.put("RS", 21);
        posicaoEstado.put("RO", 22);
        posicaoEstado.put("RR", 23);
        posicaoEstado.put("SC", 24);
        posicaoEstado.put("SP", 25);
        posicaoEstado.put("SE", 26);
        posicaoEstado.put("TO", 27);
    }
}
