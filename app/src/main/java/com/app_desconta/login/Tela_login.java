package com.app_desconta.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app_desconta.MainActivity;
import com.app_desconta.R;
import com.app_desconta.util.verificaConexao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.app_desconta.util.verificaConexao.verificaConexao;

public class Tela_login extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private Button bt_acessar;
    private Button bt_cadastrar;
    private EditText editTextEmail;
    private EditText editTextSenha;

    private String email;
    private String senha;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);

        bt_acessar = findViewById(R.id.bt_login_acessar);
        bt_cadastrar = findViewById(R.id.bt_login_cadastrar);
        editTextEmail = findViewById(R.id.et_login_login);
        editTextSenha = findViewById(R.id.et_login_senha);

        auth = FirebaseAuth.getInstance();

        bt_acessar.setOnClickListener(this);
        bt_cadastrar.setOnClickListener(this);
        editTextEmail.setOnFocusChangeListener(this);
        editTextSenha.setOnFocusChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login_acessar:
                email = editTextEmail.getText().toString().trim();
                senha = editTextSenha.getText().toString().trim();

                if (!estaVazio() && verificaConexao(getBaseContext())) loginEmail();
                break;
            case R.id.bt_login_cadastrar:
                startActivity(new Intent(getBaseContext(), TelaCadastroEmail.class));
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
        if (email.isEmpty()) erro = getString(R.string.emailObrigatorio);
        if (!erro.isEmpty()) erro += "\n";
        if (senha.isEmpty()) erro += getString(R.string.senhaObrigatorio);
        if (erro.isEmpty()) return false;
        corrigirCampos(erro);
        Toast.makeText(getBaseContext(), erro, Toast.LENGTH_LONG).show();
        return true;
    }

    private void corrigirCampos(String erro) {
        if (erro.contains(getString(R.string.senhaObrigatorio))) {
            editTextSenha.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            editTextSenha.requestFocus();
        }
        if (erro.contains(getString(R.string.emailObrigatorio))) {
            editTextEmail.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            editTextEmail.requestFocus();
        }
    }

    private void loginEmail() {
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    Toast.makeText(getBaseContext(), getString(R.string.loginEfetuadoComSucesso), Toast.LENGTH_LONG).show();
                    finish();
                } else errosFirebase(task.getException().toString());
            }
        });
    }

    private void errosFirebase(String excessao) {
        if (excessao.contains("There is no user"))
            Toast.makeText(getBaseContext(), getString(R.string.emailNaoCadastrado), Toast.LENGTH_LONG).show();
        else if (excessao.contains("address is badly"))
            Toast.makeText(getBaseContext(), getString(R.string.emailInvalido), Toast.LENGTH_LONG).show();
        else if (excessao.contains("password is invalid"))
            Toast.makeText(getBaseContext(), getString(R.string.senhaInvalida), Toast.LENGTH_LONG).show();
        else if (excessao.contains("interrupted connection"))
            Toast.makeText(getBaseContext(), getString(R.string.semConexaoComFirebase), Toast.LENGTH_LONG).show();
        else Toast.makeText(getBaseContext(), excessao, Toast.LENGTH_LONG).show();
    }
}
