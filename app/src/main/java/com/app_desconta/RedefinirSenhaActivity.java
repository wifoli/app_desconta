package com.app_desconta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static com.app_desconta.util.Util.errosFirebase;
import static com.app_desconta.util.Util.verificaConexao;

public class RedefinirSenhaActivity extends AppCompatActivity implements View.OnClickListener {

    private Button redefinir;
    private EditText editTextEmail;

    private String email;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_senha);

        redefinir = (Button) findViewById(R.id.bt_redefinir_enviar);
        editTextEmail = (EditText) findViewById(R.id.et_recuperar_senha_email);

        auth = FirebaseAuth.getInstance();

        redefinir.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        email = editTextEmail.getText().toString().trim();

        if (!estaVazio() && verificaConexao(getBaseContext())) redenifirSenha();
    }

    private boolean estaVazio() {
        String erro = "";
        if (email.isEmpty()) erro = getString(R.string.emailObrigatorio);
        if (erro.isEmpty()) return false;
        Toast.makeText(getBaseContext(), erro, Toast.LENGTH_LONG).show();
        editTextEmail.requestFocus();
        return true;
    }

    private void redenifirSenha() {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) Toast.makeText(getBaseContext(), getString(R.string.mensagem_enviada) + " " + email, Toast.LENGTH_LONG).show();
                else errosFirebase(getBaseContext(), task.getException().toString());
            }
        });
    }
}
