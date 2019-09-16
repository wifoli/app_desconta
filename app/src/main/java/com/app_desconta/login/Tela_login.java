package com.app_desconta.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app_desconta.MainActivity;
import com.app_desconta.R;

public class Tela_login extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);

        Button bt_acessar = findViewById(R.id.bt_login_acessar);
        bt_acessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button bt_cadastrar = findViewById(R.id.bt_login_cadastrar);
        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tela_cadastro1.class);
                startActivity(intent);
            }
        });
    }

    private void loginUsuario(){
        editTextEmail = findViewById(R.id.et_login_login);
        editTextSenha = findViewById(R.id.et_login_senha);


    }
}
