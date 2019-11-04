package com.app_desconta.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app_desconta.R;
import com.app_desconta.Usuario;
import com.app_desconta.api.Api;
import com.app_desconta.api.CEP;
import com.app_desconta.api.Cidade;
import com.app_desconta.api.CidadeEstado;
import com.app_desconta.api.Pessoa;
import com.app_desconta.util.RetrofitCliente;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AtualizarEnderecoFragment extends Fragment implements View.OnClickListener {

    private EditText editTextCep;
    private EditText editTextBairro;
    private EditText editTextRua;
    private EditText editTextNumero;
    private EditText editTextComplemento;

    private TextView botaoAtualizar;

    private Spinner spinnerCidade;
    private Spinner spinnerEstado;

    private String cep;
    private String estado;
    private String cidade;
    private String logradouro;
    private String bairro;
    private String numero;
    private String complemento;

    private ImageView procurarCep;

    private ArrayList<Cidade> arrayCidade;

    private CEP objectCep;
    private CidadeEstado objectCidadeEstado;

    private Map posicaoEstado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alterar_endereco, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setarVeiws(view);
        setarMascaraCep();
        setarenderecoAtual();
        povoarMapPosicaoEstado();
        setarArrayAdapterEstado();

        procurarCep.setOnClickListener(this);
        botaoAtualizar.setOnClickListener(this);
        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                preencherCidades(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        cep = editTextCep.getText().toString();
        estado = spinnerEstado.getSelectedItem().toString();
        cidade = spinnerCidade.getSelectedItem().toString();
        logradouro = editTextRua.getText().toString();
        bairro = editTextBairro.getText().toString();
        numero = editTextNumero.getText().toString();
        complemento = editTextComplemento.getText().toString();
        switch (view.getId()) {
            case R.id.iv_alterar_encontrar_cep:
                if (verificaCep())
                    retrofitGetEndereco(cep.replaceAll("[.]", "").replaceAll("[-]", ""));
                break;
            case R.id.btn_atualizar_endereco:
                if (!estaVazio())
                    confirmarDados();
                break;
        }
    }


    private void setarVeiws(View view) {
        editTextCep = (EditText) view.findViewById(R.id.et_alterar_cep);
        editTextBairro = (EditText) view.findViewById(R.id.et_alterar_bairro);
        editTextRua = (EditText) view.findViewById(R.id.et_alterar_rua);
        editTextNumero = (EditText) view.findViewById(R.id.et_alterar_numero);
        editTextComplemento = (EditText) view.findViewById(R.id.et_alterar_complemento);
        botaoAtualizar = (TextView) view.findViewById(R.id.btn_atualizar_endereco);
        procurarCep = (ImageView) view.findViewById(R.id.iv_alterar_encontrar_cep);
        spinnerEstado = (Spinner) view.findViewById(R.id.sp_alterar_estado);
        spinnerCidade = (Spinner) view.findViewById(R.id.sp_alterar_cidade);
    }

    private void setarenderecoAtual() {
        Pessoa pessoa = Usuario.getInsance().getUsuario().getPessoa();

        editTextCep.setText(pessoa.getCep());
        editTextBairro.setText(pessoa.getBairro());
        editTextRua.setText(pessoa.getRua());
        editTextNumero.setText(pessoa.getNumero());
        editTextComplemento.setText(pessoa.getComplemento());
        retrofitCidadeEstado();
    }

    private boolean estaVazio() {
        String erro = "";
        if (cep.isEmpty()) erro = getActivity().getString(R.string.cep_obrigatorio);
        if (erro.contains(getActivity().getString(R.string.cep_obrigatorio))) erro += "\n";
        if (estado.isEmpty()) erro += getActivity().getString(R.string.estadoObrigatorio);
        if (erro.contains(getActivity().getString(R.string.estadoObrigatorio))) erro += "\n";
        if (cidade.isEmpty()) erro += getActivity().getString(R.string.cidadeObrigatorio);
        if (erro.contains(getActivity().getString(R.string.cidadeObrigatorio))) erro += "\n";
        if (bairro.isEmpty()) erro += getActivity().getString(R.string.bairroObrigatorio);
        if (erro.contains(getActivity().getString(R.string.bairroObrigatorio))) erro += "\n";
        if (logradouro.isEmpty()) erro += getActivity().getString(R.string.ruaObrigatorio);
        if (erro.contains(getActivity().getString(R.string.ruaObrigatorio))) erro += "\n";
        if (numero.isEmpty()) erro += getActivity().getString(R.string.numeroObrigatorio);
        if (erro.isEmpty()) return false;
        Toast.makeText(getActivity(), erro, Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean verificaCep() {
        if (cep.length() == 10) return true;
        Toast.makeText(getActivity(), getString(R.string.cepInvalido), Toast.LENGTH_LONG).show();
        return false;
    }

    private void confirmarDados() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(getActivity().getString(R.string.confirmarDados))
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("CEP: " + cep + "\n" +
                        "Estado: " + estado + "\n" +
                        "Cidade: " + cidade + "\n" +
                        "Logradouro: " + logradouro + "\n" +
                        "Bairro: " + bairro + "\n" +
                        "Número: " + numero + "\n" +
                        "Complemento: " + complemento)
                .setPositiveButton(getActivity().getString(R.string.sim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        retrofitUpEndereco();
                    }
                })
                .setNegativeButton(getActivity().getString(R.string.nao), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    private void setarArrayAdapterEstado() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.estados, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapter);
    }

    private void setarArrayAdapterCidade() {
        ArrayList<String> arrayList = new ArrayList<>();

        for (Cidade cidade : arrayCidade) {
            arrayList.add(cidade.getNome());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
        spinnerCidade.setAdapter(adapter);

        if (objectCep != null) {
            for (int i = 0; i < arrayCidade.size(); i++) {
                if (arrayCidade.get(i).getNome().equals(objectCep.getCidade())) {
                    spinnerCidade.setSelection(i);
                    objectCep = null;
                    return;
                }
            }
        }

        if (objectCidadeEstado != null) {
            for (int i = 0; i < arrayCidade.size(); i++) {
                if (arrayCidade.get(i).getNome().equals(objectCidadeEstado.getNome())) {
                    spinnerCidade.setSelection(i);
                    objectCidadeEstado = null;
                    return;
                }
            }
        }
    }

    private void retrofitGetEndereco(String cep) {
        Retrofit client = new Retrofit.Builder()
                .baseUrl("http://ws.matheuscastiglioni.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api httpRequest = client.create(Api.class);

        Call<CEP> call = httpRequest.buscarCEP(cep);
        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {
                objectCep = response.body();
                spinnerEstado.setSelection(posicaoEstado.get(response.body().getEstado()) != null ? (Integer) posicaoEstado.get(response.body().getEstado()) : 0);
                editTextBairro.setText(response.body().getBairro());
                editTextRua.setText(response.body().getLogradouro());
                editTextComplemento.setText(response.body().getComplemento());
                setarArrayAdapterCidade();
            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {
                Log.e("Retrofit getEndereço", "Falha no Retrofit: " + t.toString());
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

    private void retrofitCidadeEstado() {

        Api httpRequest = RetrofitCliente.getCliente().create(Api.class);

        Call<CidadeEstado> call = httpRequest.getCidadeEstado(Usuario.getInsance().getUsuario().getPessoa().getId());
        call.enqueue(new Callback<CidadeEstado>() {
            @Override
            public void onResponse(Call<CidadeEstado> call, Response<CidadeEstado> response) {
                objectCidadeEstado = response.body();
                spinnerEstado.setSelection(posicaoEstado.get(response.body().getSigla()) != null ? (Integer) posicaoEstado.get(response.body().getSigla()) : 0);
            }

            @Override
            public void onFailure(Call<CidadeEstado> call, Throwable t) {
                Log.e("Retrofit get_cidade", "Falha no Retrofit: " + t.toString());
            }
        });
    }

    private void retrofitUpEndereco() {

        Api httpRequest = RetrofitCliente.getCliente().create(Api.class);

        Call<Pessoa> call = httpRequest.atualizarEndereco(Usuario.getInsance().getUsuario().getPessoa().getId(), criarJsonEndereco());
        call.enqueue(new Callback<Pessoa>() {
            @Override
            public void onResponse(Call<Pessoa> call, Response<Pessoa> response) {
                if (response.isSuccessful()) {
                    Usuario.getInsance().getUsuario().setPessoa(response.body());
                    Toast.makeText(getActivity(), getActivity().getString(R.string.endereco_atualizado_sucesso), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Pessoa> call, Throwable t) {
                Log.e("Retrofit get_cidade", "Falha no Retrofit: " + t.toString());
            }
        });
    }

    private JsonObject criarJsonEndereco() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("rua", logradouro.trim());
        jsonObject.addProperty("bairro", bairro.trim());
        jsonObject.addProperty("numero", numero.trim());
        jsonObject.addProperty("cep", cep.trim());
        jsonObject.addProperty("complemento", complemento.trim());
        jsonObject.addProperty("cidade_id", arrayCidade.get(spinnerCidade.getSelectedItemPosition()).getId());

        return jsonObject;
    }

    private void setarMascaraCep() {
        editTextCep.addTextChangedListener(new TextWatcher() {
            boolean isUpdating;

            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int after) {

                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                boolean hasMask =
                        s.toString().indexOf('/') > -1;

                String str = s.toString()
                        .replaceAll("[.]", "")
                        .replaceAll("[-]", "");

                if (after > before) {

                    if (str.length() > 5) {
                        str =
                                str.substring(0, 2) + '.' +
                                        str.substring(2, 5) + '-' +
                                        str.substring(5);

                    } else if (str.length() > 2) {
                        str =
                                str.substring(0, 2) + '.' +
                                        str.substring(2);
                    }
                    isUpdating = true;
                    editTextCep.setText(str);
                    editTextCep.setSelection(editTextCep.getText().length());

                } else {
                    isUpdating = true;
                    editTextCep.setText(str);
                    editTextCep.setSelection(
                            Math.max(0, Math.min(
                                    hasMask ? start - before : start,
                                    str.length())));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
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
