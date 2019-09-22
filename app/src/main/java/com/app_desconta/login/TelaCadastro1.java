package com.app_desconta.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app_desconta.R;
import com.app_desconta.util.ValidaCPF;


public class TelaCadastro1 extends AppCompatActivity {

    private EditText editTextNome;
    private EditText editTextSobreNome;
    private EditText editTextCpf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro1);
        setTitle("Cadastro: 1 de 3 - Dados pessoais");

        editTextNome = (EditText) findViewById(R.id.et_cadastrar_nome);
        editTextSobreNome = (EditText) findViewById(R.id.et_cadastrar_sobrenome);
        editTextCpf = (EditText) findViewById(R.id.et_cadastrar_cpf);
        setarListeners();

        Button bt_proximo = findViewById(R.id.bt_cadastrar_proximo);
        bt_proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (verificador()) {

                }


                //   Intent intent = new Intent(getApplicationContext(), TelaCadastro2.class);
                // startActivity(intent);
            }
        });
    }

    public boolean verificador() {

        String toast = "";

        if (editTextNome.getText().toString().trim().equals("")) {
            editTextNome.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            toast += getString(R.string.tv_cadastrar_nome) + " " + getString(R.string.obrigatorio);
        }
        if (editTextSobreNome.getText().toString().trim().equals("")) {
            editTextSobreNome.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            if (!toast.trim().equals("")) toast += "\n";
            toast += getString(R.string.tv_cadastrar_sobrenome) + " " + getString(R.string.obrigatorio);
        }
        Log.d("Test", ValidaCPF.imprimeCPFSemPontuacoes(editTextCpf.getText().toString().trim()));
        if (!ValidaCPF.isCPF(ValidaCPF.imprimeCPFSemPontuacoes(editTextCpf.getText().toString().trim()))) {
            editTextCpf.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            if (!toast.trim().equals("")) toast += "\n";
            toast += getString(R.string.tv_cadastrar_cpf) + " " + getString(R.string.invalido);
        }

        if (toast.trim().equals("")) return true;
        else {
            Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void setarListeners() {
        editTextNome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean temFoco) {
                if (temFoco) {
                    editTextNome.setBackground(getDrawable(R.drawable.meu_edit_text));
                } else if (editTextNome.getText().toString().trim().equals(""))
                    editTextNome.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            }
        });
        editTextSobreNome.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean temFoco) {
                if (temFoco) {
                    editTextSobreNome.setBackground(getDrawable(R.drawable.meu_edit_text));
                } else if (editTextSobreNome.getText().toString().trim().equals(""))
                    editTextSobreNome.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            }
        });
        editTextCpf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean temFoco) {
                if (temFoco) {
                    editTextCpf.setBackground(getDrawable(R.drawable.meu_edit_text));
                } else if (editTextCpf.getText().toString().trim().length() < 14)
                    editTextCpf.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            }
        });
        editTextCpf.addTextChangedListener(new TextWatcher() {
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
                        s.toString().indexOf('.') > -1 ||
                                s.toString().indexOf('-') > -1;

                // Remove o '.' e '-' da String
                String str = s.toString()
                        .replaceAll("[.]", "")
                        .replaceAll("[-]", "");

                // as variáveis before e after dizem o tamanho
                // anterior e atual da String, se after > before
                // é pq está apagando
                if (after > before) {
                    // Se tem mais de 5 caracteres (sem máscara)
                    // coloca o '.' e o '-'
                    if (str.length() > 9) {
                        str =
                                str.substring(0, 3) + '.' +
                                        str.substring(3, 6) + '.' +
                                        str.substring(6, 9) + '-' +
                                        str.substring(9);

                        // Se tem mais de 2, coloca só o ponto
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
                    // Seta a flag pra evitar chamada infinita
                    isUpdating = true;
                    // seta o novo texto
                    editTextCpf.setText(str);
                    // seta a posição do cursor
                    editTextCpf.setSelection(editTextCpf.getText().length());

                } else {
                    isUpdating = true;
                    editTextCpf.setText(str);
                    // Se estiver apagando posiciona o cursor
                    // no local correto. Isso trata a deleção dos
                    // caracteres da máscara.
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