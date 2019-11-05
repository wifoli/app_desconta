package com.app_desconta.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app_desconta.MainActivity;
import com.app_desconta.R;
import com.app_desconta.Usuario;
import com.app_desconta.api.Api;
import com.app_desconta.api.CEP;
import com.app_desconta.api.Cidade;
import com.app_desconta.api.Pessoa;
import com.app_desconta.api.User;
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

import static com.app_desconta.util.Util.verificaConexao;


public class TelaCadastroLocalizacao extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private EditText editTextCep;
    private EditText editTextBairro;
    private EditText editTextRua;
    private EditText editTextNumero;
    private EditText editTextComplemento;

    private Spinner spinnerCidade;
    private Spinner spinnerEstado;

    private ImageView procurarCep;

    private Button botaoCadastrar;

    private String cep;
    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private String numero;
    private String complemento;

    private CEP objectCep;

    private boolean temRegistro = false;

    private ArrayList<Cidade> arrayCidade;

    private Map posicaoEstado;

    public TelaCadastroLocalizacao() {
        povoarMapPosicaoEstado();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro_localizacao);
        setTitle("Localização");

        editTextCep = (EditText) findViewById(R.id.et_cadastro2_cep);
        editTextBairro = (EditText) findViewById(R.id.et_cadastrar2_bairro);
        editTextRua = (EditText) findViewById(R.id.et_cadastrar2_rua);
        editTextNumero = (EditText) findViewById(R.id.et_cadastrar2_numero);
        editTextComplemento = (EditText) findViewById(R.id.et_cadastrar2_complemento);
        spinnerCidade = (Spinner) findViewById(R.id.sp_cadastro2_cidade);
        spinnerEstado = (Spinner) findViewById(R.id.sp_cadastro2_estado);
        procurarCep = (ImageView) findViewById(R.id.iv_cadastrar2_encontrar_cep);
        botaoCadastrar = (Button) findViewById(R.id.bt_cadastrar2_proximo);

        botaoCadastrar.setOnClickListener(this);
        procurarCep.setOnClickListener(this);
        spinnerEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                preencherCidades(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setarMascaraCep();
        setarArrayAdapterEstado();
        povoarMapPosicaoEstado();
        setarCampos();
    }

    @Override
    public void onClick(View view) {
        cep = editTextCep.getText().toString().trim();
        estado = spinnerEstado.getSelectedItemId() != 0 ? spinnerEstado.getSelectedItem().toString() : "";
        cidade = spinnerCidade.getSelectedItemId() != 0 ? spinnerEstado.getSelectedItem().toString() : "";
        bairro = editTextBairro.getText().toString().trim();
        rua = editTextRua.getText().toString().trim();
        numero = editTextNumero.getText().toString().trim();
        complemento = editTextComplemento.getText().toString().trim();

        switch (view.getId()) {
            case R.id.iv_cadastrar2_encontrar_cep:
                if (verificaCep()) retrofitGetEndereco(cep
                        .replaceAll("[.]", "")
                        .replaceAll("[-]", ""));
                break;
            case R.id.bt_cadastrar2_proximo:
                if ((!estaVazio()) && (verificaCep()) && (verificaConexao(getBaseContext()))) {
                    setarPessoa();
                    finalizarCadastro();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean estaComFoco) {
        EditText et = (EditText) view;
        if (estaComFoco) view.setBackground(getDrawable(R.drawable.meu_edit_text));
        else if (et.getText().toString().trim().isEmpty())
            view.setBackground(getDrawable(R.drawable.meu_edit_text_error));
    }


    private boolean estaVazio() {
        String erro = "";
        if (cep.isEmpty()) erro = getString(R.string.cep_obrigatorio);
        if (erro.contains(getString(R.string.cep_obrigatorio))) erro += "\n";
        if (estado.isEmpty()) erro += getString(R.string.estadoObrigatorio);
        if (erro.contains(getString(R.string.estadoObrigatorio))) erro += "\n";
        if (cidade.isEmpty()) erro += getString(R.string.cidadeObrigatorio);
        if (erro.contains(getString(R.string.cidadeObrigatorio))) erro += "\n";
        if (bairro.isEmpty()) erro += getString(R.string.bairroObrigatorio);
        if (erro.contains(getString(R.string.bairroObrigatorio))) erro += "\n";
        if (rua.isEmpty()) erro += getString(R.string.ruaObrigatorio);
        if (erro.contains(getString(R.string.ruaObrigatorio))) erro += "\n";
        if (numero.isEmpty()) erro += getString(R.string.numeroObrigatorio);
        if (erro.isEmpty()) return false;
        corrigirCampos(erro);
        Toast.makeText(getBaseContext(), erro, Toast.LENGTH_LONG).show();
        return true;
    }

    private void corrigirCampos(String erro) {
        if (erro.contains(getString(R.string.numeroObrigatorio))) {
            editTextNumero.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            editTextCep.requestFocus();
        }
        if (erro.contains(getString(R.string.ruaObrigatorio))) {
            editTextRua.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            editTextRua.requestFocus();
        }
        if (erro.contains(getString(R.string.bairroObrigatorio))) {
            editTextBairro.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            editTextBairro.requestFocus();
        }
        if (erro.contains(getString(R.string.cidadeObrigatorio))) {
            spinnerCidade.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            spinnerCidade.requestFocus();
        }
        if (erro.contains(getString(R.string.cep_obrigatorio))) {
            editTextCep.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            editTextCep.requestFocus();
        }
    }

    private boolean verificaCep() {
        if (cep.length() == 10) return true;
        Toast.makeText(getBaseContext(), getString(R.string.cepInvalido), Toast.LENGTH_LONG).show();
        return false;
    }

    private void setarArrayAdapterEstado() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.estados, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEstado.setAdapter(adapter);
    }

    private void setarArrayAdapterCidade() {
        ArrayList<String> arrayList = new ArrayList<>();

        for (Cidade cidade : arrayCidade) {
            arrayList.add(cidade.getNome());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        spinnerCidade.setAdapter(adapter);

        if (objectCep != null) {
            for (int i = 0; i < arrayCidade.size(); i++) {
                if (arrayCidade.get(i).getNome().equals(objectCep.getCidade())) {
                    spinnerCidade.setSelection(i);
                    return;

                }
            }
        }
    }


    private void setarCampos() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        temRegistro = extras.getBoolean("Tem Registro");
        if (temRegistro) {
            Pessoa pessoa = Usuario.getInsance().getUsuario().getPessoa();
            editTextRua.setText(pessoa.getRua());
            editTextBairro.setText(pessoa.getBairro());
            editTextNumero.setText(pessoa.getNumero());
            editTextCep.setText(pessoa.getCep());
            editTextComplemento.setText(pessoa.getComplemento());
        }
    }

    private void setarPessoa() {
        Usuario.getInsance().getUsuario().getPessoa().setRua(rua);
        Usuario.getInsance().getUsuario().getPessoa().setBairro(bairro);
        Usuario.getInsance().getUsuario().getPessoa().setNumero(numero);
        Usuario.getInsance().getUsuario().getPessoa().setCep(cep);
        Usuario.getInsance().getUsuario().getPessoa().setComplemento(complemento);
        Usuario.getInsance().getUsuario().getPessoa().setCidade("" + arrayCidade.get(spinnerCidade.getSelectedItemPosition()).getId());
    }

    private void finalizarCadastro() {
        if (temRegistro) retrofitAlterarUsuario();
        else retrofitCadastro();

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

    private void retrofitCadastro() {

        Api httpRequest = RetrofitCliente.getCliente().create(Api.class);

        Call<User> call = httpRequest.criarUsuario(Usuario.getInsance().getEmail(), Usuario.getInsance().getUid(), criarJsonPessoa());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Usuario.getInsance().setUsuario(response.body());
                    loginSucesso();
                } else Log.e("Retrofit cadastrar", "Falha no Retrofit Code: " + response.code());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Retrofit cadastrar", "Falha no Retrofit: " + t.toString());
            }
        });
    }

    private void retrofitAlterarUsuario() {
        Api httpRequest = RetrofitCliente.getCliente().create(Api.class);

        Call<User> call = httpRequest.alterarUsuario(Usuario.getInsance().getUsuario().getPessoa().getId(), Usuario.getInsance().getEmail(), Usuario.getInsance().getUid(), criarJsonPessoaAlterar());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Usuario.getInsance().setUsuario(response.body());
                    loginSucesso();
                } else {
                    Log.e("Retrofit update", "Falha no Retrofit Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Retrofit update", "Falha no Retrofit: " + t.toString());
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

    private void loginSucesso() {
        Toast.makeText(getBaseContext(), getString(R.string.cadastroEfetuadoComSucesso), Toast.LENGTH_LONG).show();
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        finish();
    }

    private JsonObject criarJsonPessoa() {
        Pessoa pessoa = Usuario.getInsance().getUsuario().getPessoa();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("nome", pessoa.getNome());
        jsonObject.addProperty("sobrenome", pessoa.getSobrenome());
        jsonObject.addProperty("rg", pessoa.getRg());
        jsonObject.addProperty("cpf", pessoa.getCpf());
        jsonObject.addProperty("data_nasc", pessoa.getDataNasc());
        jsonObject.addProperty("tel_1", pessoa.getTel1());
        jsonObject.addProperty("tel_2", pessoa.getTel2());
        jsonObject.addProperty("rua", pessoa.getRua());
        jsonObject.addProperty("bairro", pessoa.getBairro());
        jsonObject.addProperty("numero", pessoa.getNumero());
        jsonObject.addProperty("cep", pessoa.getCep());
        jsonObject.addProperty("complemento", pessoa.getComplemento());
        jsonObject.addProperty("tipo_pessoa", "Física");
        jsonObject.addProperty("cidade_id", pessoa.getCidade());

        return jsonObject;
    }

    private JsonObject criarJsonPessoaAlterar() {
        Pessoa pessoa = Usuario.getInsance().getUsuario().getPessoa();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("nome", pessoa.getNome());
        jsonObject.addProperty("sobrenome", pessoa.getSobrenome());
        jsonObject.addProperty("rg", pessoa.getRg());
        jsonObject.addProperty("data_nasc", pessoa.getDataNasc());
        jsonObject.addProperty("tel_1", pessoa.getTel1());
        jsonObject.addProperty("tel_2", pessoa.getTel2());
        jsonObject.addProperty("rua", pessoa.getRua());
        jsonObject.addProperty("bairro", pessoa.getBairro());
        jsonObject.addProperty("numero", pessoa.getNumero());
        jsonObject.addProperty("cep", pessoa.getCep());
        jsonObject.addProperty("complemento", pessoa.getComplemento());
        jsonObject.addProperty("cidade_id", pessoa.getCidade());

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