package com.app_desconta.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app_desconta.R;
import com.app_desconta.api.Api;
import com.app_desconta.api.CEP;
import com.app_desconta.api.Pessoa;

import org.json.JSONException;
import org.json.JSONObject;

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
    private EditText editTextCidade;
    private EditText editTextBairro;
    private EditText editTextRua;
    private EditText editTextNumero;
    private EditText editTextComplemento;

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
        editTextCidade = (EditText) findViewById(R.id.et_cadastro2_cidade);
        editTextBairro = (EditText) findViewById(R.id.et_cadastrar2_bairro);
        editTextRua = (EditText) findViewById(R.id.et_cadastrar2_rua);
        editTextNumero = (EditText) findViewById(R.id.et_cadastrar2_numero);
        editTextComplemento = (EditText) findViewById(R.id.et_cadastrar2_complemento);
        spinnerEstado = (Spinner) findViewById(R.id.sp_cadastro2_estado);
        procurarCep = (ImageView) findViewById(R.id.iv_cadastrar2_encontrar_cep);
        botaoCadastrar = (Button) findViewById(R.id.bt_cadastrar2_proximo);

        botaoCadastrar.setOnClickListener(this);
        procurarCep.setOnClickListener(this);
        setarMascaraCep();
        setarArrayAdapterEstado();
        povoarMapPosicaoEstado();
    }

    @Override
    public void onClick(View view) {
        cep = editTextCep.getText().toString().trim();
        estado = spinnerEstado.getSelectedItemId() != 0 ? spinnerEstado.getSelectedItem().toString() : "";
        cidade = editTextCidade.getText().toString().trim();
        bairro = editTextBairro.getText().toString().trim();
        rua = editTextRua.getText().toString().trim();
        numero = editTextNumero.getText().toString().trim();
        complemento = editTextComplemento.getText().toString().trim();

        switch (view.getId()) {
            case R.id.iv_cadastrar2_encontrar_cep:
                if (verificaCep()) getEndereco(cep
                        .replaceAll("[.]", "")
                        .replaceAll("[-]", ""));
                break;
            case R.id.bt_cadastrar2_proximo:
                if ((!estaVazio()) && (verificaCep()) && (verificaConexao(getBaseContext()))) {
                    try {
                        obterInfoParaCadastro();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
            editTextCidade.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            editTextCidade.requestFocus();
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

    private void obterInfoParaCadastro() throws JSONException {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String nome = extras.getString("nome");
        String sobrenome = extras.getString("sobrenome");
        String rg = extras.getString("rg");
        String cpf = extras.getString("cpf");
        String dataNasc = extras.getString("dataNasc");
        String telefone1 = extras.getString("telefone1");
        String telefone2 = extras.getString("telefone2");

        cadastrar(nome, sobrenome, rg, cpf, dataNasc, telefone1, telefone2);
    }

    private void getEndereco(String cep) {
        Retrofit client = new Retrofit.Builder()
                .baseUrl("http://ws.matheuscastiglioni.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api httpRequest = client.create(Api.class);

        Call<CEP> call = httpRequest.buscarCEP(cep);
        call.enqueue(callback);
    }

    private Callback<CEP> callback = new Callback<CEP>() {
        @Override
        public void onResponse(Call<CEP> call, Response<CEP> response) {
            editTextCidade.setText(response.body().getCidade());
            spinnerEstado.setSelection(posicaoEstado.get(response.body().getEstado()) != null ? (Integer) posicaoEstado.get(response.body().getEstado()) : 0);
            editTextBairro.setText(response.body().getBairro());
            editTextRua.setText(response.body().getLogradouro());
            editTextComplemento.setText(response.body().getComplemento());
        }

        @Override
        public void onFailure(Call<CEP> call, Throwable t) {
            Log.e("Retrofit getEndereço", "Falha no Retrofit: " + t.toString());
        }
    };

    private void cadastrar(String nome, String sobrenome, String rg, String cpf, String dataNasc, String telefone1, String telefone2) throws JSONException {
        Retrofit client = new Retrofit.Builder()
                .baseUrl("http://192.168.0.129/public/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api httpRequest = client.create(Api.class);
        Toast.makeText(getBaseContext(), nome + " - " + sobrenome + " - " + rg + " - " + cpf + " - " + dataNasc + " - " + telefone1 + " - " + telefone2 + " - " + rua + " - " + bairro + " - " + numero + " - " + cep + " - " + complemento + " - " + cidade, Toast.LENGTH_LONG).show();
        // Pessoa pessoa = new Pessoa(nome, sobrenome, cpf, rg, dataNasc, telefone1, telefone2, rua, bairro, numero, cep, complemento, "1");
        //  Call<Pessoa> call = httpRequest.criarUsuario(pessoa);
        //  Call<Pessoa> call = httpRequest.criarUsuario(nome, sobrenome, rg, cpf, dataNasc, telefone1, telefone2, rua, bairro, numero, cep, complemento, cidade);
        JSONObject jsonPessoa = new JSONObject();
        jsonPessoa.put("nome", nome);
        jsonPessoa.put("sobrenome", sobrenome);
        jsonPessoa.put("cpf", cpf);
        jsonPessoa.put("rg", rg);
        jsonPessoa.put("dataNasc", dataNasc);
        jsonPessoa.put("telefone1", telefone1);
        jsonPessoa.put("telefone2", telefone2);
        jsonPessoa.put("rua", rua);
        jsonPessoa.put("bairro", bairro);
        jsonPessoa.put("numero", numero);
        jsonPessoa.put("cep", cep);
        jsonPessoa.put("complemento", complemento);
        jsonPessoa.put("cidade", cidade);
        Call<Pessoa> call = httpRequest.criarUsuario(jsonPessoa.toString());
        call.enqueue(callbackCadastro);
    }

    private Callback<Pessoa> callbackCadastro = new Callback<Pessoa>() {
        @Override
        public void onResponse(Call<Pessoa> call, Response<Pessoa> response) {
            Log.d("test", response.body().getNome());
            Log.d("test", "passei aqui");
        }

        @Override
        public void onFailure(Call<Pessoa> call, Throwable t) {
            Log.e("Retrofit castrarUsuário", "Falha no Retrofit: " + t.toString());
        }
    };

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

                // Quando o texto é alterado o onTextChange é chamado
                // Essa flag evita a chamada infinita desse método
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                // Ao apagar o texto, a máscara é removida,
                // então o posicionamento do cursor precisa
                // saber se o texto atual tinha ou não, máscara
                boolean hasMask =
                        s.toString().indexOf('/') > -1;

                // Remove o '.' e '-' da String
                String str = s.toString()
                        .replaceAll("[.]", "")
                        .replaceAll("[-]", "");

                // as variáveis before e after dizem o tamanho
                // anterior e atual da String, se after > before
                // é pq está apagando
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
                    // Seta a flag pra evitar chamada infinita
                    isUpdating = true;
                    // seta o novo texto
                    editTextCep.setText(str);
                    // seta a posição do cursor
                    editTextCep.setSelection(editTextCep.getText().length());

                } else {
                    isUpdating = true;
                    editTextCep.setText(str);
                    // Se estiver apagando posiciona o cursor
                    // no local correto. Isso trata a deleção dos
                    // caracteres da máscara.
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

