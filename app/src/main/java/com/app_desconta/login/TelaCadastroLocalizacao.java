package com.app_desconta.login;

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

import androidx.appcompat.app.AppCompatActivity;

import com.app_desconta.R;
import com.app_desconta.api.Api;
import com.app_desconta.api.CEP;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TelaCadastroLocalizacao extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private EditText editTextCep;
    private EditText editTextBairro;
    private EditText editTextRua;
    private EditText editTextNumero;
    private EditText editTextComplemento;

    private Spinner spinnerEstado;
    private Spinner spinnerCidade;

    private ImageView procurarCep;

    private Button botaoCadastrar;

    private String cep;

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
        spinnerEstado = (Spinner) findViewById(R.id.sp_cadastro2_estado);
        spinnerCidade = (Spinner) findViewById(R.id.sp_cadastro2_cidade);
        procurarCep = (ImageView) findViewById(R.id.iv_cadastrar2_encontrar_cep);
        botaoCadastrar = (Button) findViewById(R.id.bt_cadastrar2_proximo);

        botaoCadastrar.setOnClickListener(this);
        procurarCep.setOnClickListener(this);
        setarMascaraCep();
        
    }

    @Override
    public void onClick(View view) {
        cep = editTextCep.getText().toString().trim();

        switch (view.getId()) {
            case R.id.iv_cadastrar2_encontrar_cep:
                if (verificaCep()) getEndereco(cep
                        .replaceAll("[.]", "")
                        .replaceAll("[-]", ""));
                break;
            case R.id.bt_cadastrar2_proximo:
                break;
            default:
                break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }

    private boolean verificaCep() {
        if (cep.length() == 10) return true;
        return false;
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
            editTextBairro.setText(response.body().getBairro());
            editTextRua.setText(response.body().getLogradouro());
            editTextComplemento.setText(response.body().getComplemento());
        }

        @Override
        public void onFailure(Call<CEP> call, Throwable t) {
            Log.e("Retrofit getEndereço", "Falha no Retrofit: " + t.toString());
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
}

