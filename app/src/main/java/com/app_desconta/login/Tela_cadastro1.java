package com.app_desconta.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.app_desconta.R;


public class Tela_cadastro1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro1);
        setTitle("Cadastro: Etapa 1 de 3 - Dados pessoais");

        Button bt_proximo = findViewById(R.id.bt_cadastrar_proximo);
        bt_proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tela_cadastro2.class);
                startActivity(intent);
            }
        });
    }
}