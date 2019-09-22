package com.app_desconta.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.app_desconta.R;


public class TelaCadastro2 extends AppCompatActivity {

    private String arrey_spinner[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro2);
        setTitle("Cadastro: Etapa 2 de 3 - Localização");

        Button bt_proximo = findViewById(R.id.bt_cadastrar2_proximo);
        bt_proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TelaCadastroEmail.class);
                startActivity(intent);
            }
        });

        //adicionando valores para o combobox estado e cidade
        arrey_spinner = new String[19];
        arrey_spinner[0] = "Escolha";
        arrey_spinner[1] = "Item 1";
        arrey_spinner[2] = "Item 2";
        arrey_spinner[3] = "Item 3";
        arrey_spinner[4] = "Item 4";
        arrey_spinner[5] = "Item 5";
        arrey_spinner[6] = "Item 6";
        arrey_spinner[7] = "Item 7";
        arrey_spinner[8] = "Item 8";
        arrey_spinner[9] = "Item 9";
        arrey_spinner[10] = "Item 10";
        arrey_spinner[11] = "Item 11";
        arrey_spinner[12] = "Item 12";
        arrey_spinner[13] = "Item 13";
        arrey_spinner[14] = "Item 14";
        arrey_spinner[15] = "Item 15";
        arrey_spinner[16] = "Item 16";
        arrey_spinner[17] = "Item 17";
        arrey_spinner[18] = "Item 18";

        Spinner spinnerEstado = (Spinner) findViewById(R.id.sp_cadastro2_estado);
        Spinner spinnerCidade = (Spinner) findViewById(R.id.sp_cadastro2_cidade);
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, arrey_spinner);
        spinnerEstado.setAdapter(adapter);
        spinnerCidade.setAdapter(adapter);
    }
}
