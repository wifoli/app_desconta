package com.app_desconta.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app_desconta.R;
import com.app_desconta.Usuario;
import com.app_desconta.api.Api;
import com.app_desconta.api.User;
import com.app_desconta.util.RetrofitCliente;
import com.app_desconta.util.ValidaCPF;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app_desconta.util.Util.verificaConexao;

public class TelaVerificarCpf extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private EditText editTextCpf;

    private ImageView imagemCpf;

    private Button buttonVerificar;

    private String cpf;

    private boolean isCpf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_verificar_cpf);
        setTitle("Cadastro de usuário");

        editTextCpf = (EditText) findViewById(R.id.et_verificar_cadastrar_cpf);
        imagemCpf = (ImageView) findViewById(R.id.iv_verificar_cadastrar_validacaoCPF);
        buttonVerificar = (Button) findViewById(R.id.bt_verificar_cadastrar_verificar);

        buttonVerificar.setOnClickListener(this);
        setarMascaraCPF();
    }

    @Override
    public void onClick(View view) {
        cpf = editTextCpf.getText().toString().trim();

        if ((!estaVazio()) && (isCpf) && (verificaConexao(getBaseContext())))
            retrofitGetUsuarioComCpf(cpf
                    .replaceAll("[.]", "")
                    .replaceAll("[-]", ""));
    }

    @Override
    public void onFocusChange(View view, boolean b) {

    }

    private boolean estaVazio() {
        String erro = "";
        if (cpf.isEmpty()) erro += getString(R.string.cpfObrigatorio);
        if (erro.isEmpty()) return false;
        editTextCpf.requestFocus();
        Toast.makeText(getBaseContext(), erro, Toast.LENGTH_LONG).show();
        return true;
    }

    private void verificaCPF(String cpf) {
        if (ValidaCPF.isCPF(cpf)) {
            imagemCpf.setImageDrawable(getDrawable(R.drawable.correto));
            editTextCpf.setBackground(getDrawable(R.drawable.meu_edit_text));
            isCpf = true;
            return;
        }
        setarErro();
    }

    private void setarErro() {
        editTextCpf.setBackground(getDrawable(R.drawable.meu_edit_text_error));
        imagemCpf.setImageDrawable(getDrawable(R.drawable.incorreto));
    }

    private void iniciarActivitPassandoOCPF() {
        Intent intent = new Intent(this, TelaCadastroDadosPessoais.class);
        Bundle extras = new Bundle();

        if (Usuario.getInsance().getUsuario().getPessoa().getId().trim().equals("")) {
            extras.putBoolean("Tem Registro", false);
            extras.putString("CPF", cpf);
        }else extras.putBoolean("Tem Registro", true);

        intent.putExtras(extras);
        startActivity(intent);
    }

    private void retrofitGetUsuarioComCpf(String cpf) {
        Api httpRequest = RetrofitCliente.getCliente().create(Api.class);

        Call<User> call = httpRequest.getUsuarioComCpf(cpf);
        call.enqueue(callback);
    }

    private Callback<User> callback = new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            Usuario.getInsance().setUsuario(response.body());
            iniciarActivitPassandoOCPF();
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {
            Log.e("Retrofit get_usuario", "Falha no Retrofit: " + t.toString());
        }
    };

    private void setarMascaraCPF() {
        editTextCpf.addTextChangedListener(new TextWatcher() {
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
                        s.toString().indexOf('.') > -1 ||
                                s.toString().indexOf('-') > -1;

                String str = s.toString()
                        .replaceAll("[.]", "")
                        .replaceAll("[-]", "");

                if (after > before) {

                    if (str.length() == 11) verificaCPF(str);

                    if (str.length() > 9) {
                        str =
                                str.substring(0, 3) + '.' +
                                        str.substring(3, 6) + '.' +
                                        str.substring(6, 9) + '-' +
                                        str.substring(9);

                    } else if (str.length() > 6) {
                        str =
                                str.substring(0, 3) + '.' +
                                        str.substring(3, 6) + '.' +
                                        str.substring(6);
                    } else if (str.length() > 3) {
                        str =
                                str.substring(0, 3) + '.' +
                                        str.substring(3);
                    }
                    isUpdating = true;
                    editTextCpf.setText(str);
                    editTextCpf.setSelection(editTextCpf.getText().length());

                } else {
                    isUpdating = true;
                    editTextCpf.setText(str);
                    if (str.length() == 10) {
                        setarErro();
                        isCpf = false;
                    }
                    editTextCpf.setSelection(
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