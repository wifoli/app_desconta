package com.app_desconta.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app_desconta.MainActivity;
import com.app_desconta.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.app_desconta.util.Util.errosFirebase;
import static com.app_desconta.util.Util.verificaConexao;


public class TelaCadastroEmail extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private Button cadastrarUsuario;
    private EditText editTextEmail;
    private EditText editTextSenha;
    private EditText editTextConfirmarSenha;
    private CheckBox termos;

    private String email;
    private String senha;
    private String confirmarSenha;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro_email);
        setTitle("Cadastro de usuário");

        cadastrarUsuario = (Button) findViewById(R.id.bt_cadastrar_usuario_email);
        editTextEmail = (EditText) findViewById(R.id.et_cadastrar_email);
        editTextSenha = (EditText) findViewById(R.id.et_cadastrar_senha);
        editTextConfirmarSenha = (EditText) findViewById(R.id.et_cadastrar_ConfSenha);
        termos = (CheckBox) findViewById(R.id.cb_cadastrar_termos);

        auth = FirebaseAuth.getInstance();

        cadastrarUsuario.setOnClickListener(this);
        editTextEmail.setOnFocusChangeListener(this);
        editTextSenha.setOnFocusChangeListener(this);
        editTextConfirmarSenha.setOnFocusChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        email = editTextEmail.getText().toString().trim();
        senha = editTextSenha.getText().toString().trim();
        confirmarSenha = editTextConfirmarSenha.getText().toString().trim();

        if (!estaVazio() && aceitouTermos() && senhasIguais() && verificaConexao(getBaseContext())) criarUsuario();
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
        if (erro.contains(getString(R.string.emailObrigatorio))) erro += "\n";
        if (senha.isEmpty()) erro += getString(R.string.senhaObrigatorio);
        if (erro.contains(getString(R.string.senhaObrigatorio))) erro += "\n";
        if (confirmarSenha.isEmpty()) erro += getString(R.string.confirmarSenhaObrigatorio);
        if (erro.isEmpty()) return false;
        corrigirCampos(erro);
        Toast.makeText(getBaseContext(), erro, Toast.LENGTH_LONG).show();
        return true;
    }

    private void corrigirCampos(String erro) {
        if (erro.contains(getString(R.string.confirmarSenhaObrigatorio))) {
            editTextConfirmarSenha.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            editTextConfirmarSenha.requestFocus();
        }
        if (erro.contains(getString(R.string.senhaObrigatorio))) {
            editTextSenha.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            editTextSenha.requestFocus();
        }
        if (erro.contains(getString(R.string.emailObrigatorio))) {
            editTextEmail.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            editTextEmail.requestFocus();
        }
    }

    private boolean aceitouTermos() {
        if (termos.isChecked()) return true;
        Toast.makeText(getBaseContext(), getString(R.string.termosObrigatorio), Toast.LENGTH_LONG).show();
        return false;
    }

    private boolean senhasIguais() {
        if (senha.equals(confirmarSenha)) return true;
        Toast.makeText(getBaseContext(), getString(R.string.senhasDiferentes), Toast.LENGTH_LONG).show();
        editTextConfirmarSenha.requestFocus();
        editTextConfirmarSenha.setBackground(getDrawable(R.drawable.meu_edit_text_error));
        return false;
    }

    private void criarUsuario() {
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getBaseContext(), getString(R.string.loginCriadoComSucesso), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                    finish();
                } else errosFirebase(getBaseContext(), task.getException().toString());
            }
        });
    }
}