package com.app_desconta.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app_desconta.R;
import com.app_desconta.util.ValidaCPF;
import com.app_desconta.util.ValidaData;

import static com.app_desconta.util.Util.verificaConexao;


public class TelaCadastroDadosPessoais extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private EditText editTextNome;
    private EditText editTextSobreNome;
    private EditText editTextCpf;
    private EditText editTextDataNasc;

    private ImageView imagemData;
    private ImageView imagemCpf;

    private Button buttonProximo;

    private String nome;
    private String sobrenome;
    private String cpf;
    private String dataNasc;

    private boolean isCpf = false;
    private boolean isdata = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro_dados_pessoais);
        setTitle("Dados pessoais");

        editTextNome = (EditText) findViewById(R.id.et_cadastrar_nome);
        editTextSobreNome = (EditText) findViewById(R.id.et_cadastrar_sobrenome);
        editTextCpf = (EditText) findViewById(R.id.et_cadastrar_cpf);
        editTextDataNasc = (EditText) findViewById(R.id.et_cadastrar_dataNasc);
        imagemData = (ImageView) findViewById(R.id.iv_cadastrar_validacaoData);
        imagemCpf = (ImageView) findViewById(R.id.iv_cadastrar_validacaoCPF);
        buttonProximo = (Button) findViewById(R.id.bt_cadastrar_proximo);

        buttonProximo.setOnClickListener(this);
        editTextNome.setOnFocusChangeListener(this);
        editTextSobreNome.setOnFocusChangeListener(this);
        editTextCpf.setOnFocusChangeListener(this);
        editTextDataNasc.setOnFocusChangeListener(this);
        setarMascaraCPF();
        setarMascaraData();
        setarCampos();
    }

    @Override
    public void onClick(View view) {
        nome = editTextNome.getText().toString().trim();
        sobrenome = editTextSobreNome.getText().toString().trim();
        cpf = editTextCpf.getText().toString().trim();
        dataNasc = editTextDataNasc.getText().toString().trim();

        if (!estaVazio() && (isCpf) && (isdata) && (verificaConexao(getBaseContext())))
            startActivity(new Intent(getBaseContext(), TelaCadastroLocalizacao.class));
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
        if (nome.isEmpty()) erro = getString(R.string.nomeObrigatorio);
        if (erro.contains(getString(R.string.nomeObrigatorio))) erro += "\n";
        if (sobrenome.isEmpty()) erro += getString(R.string.sobrenomeObrigatorio);
        if (erro.contains(getString(R.string.sobrenomeObrigatorio))) erro += "\n";
        if (cpf.isEmpty()) erro += getString(R.string.cpfObrigatorio);
        if (erro.contains(getString(R.string.cpfObrigatorio))) erro += "\n";
        if (dataNasc.isEmpty()) erro += getString(R.string.dataNascObrigatorio);
        if (erro.isEmpty()) return false;
        corrigirCampos(erro);
        Toast.makeText(getBaseContext(), erro, Toast.LENGTH_LONG).show();
        return true;
    }

    private void corrigirCampos(String erro) {
        if (erro.contains(getString(R.string.dataNascObrigatorio))) {
            editTextDataNasc.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            editTextDataNasc.requestFocus();
        }
        if (erro.contains(getString(R.string.cpfObrigatorio))) {
            editTextCpf.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            editTextCpf.requestFocus();
        }
        if (erro.contains(getString(R.string.sobrenomeObrigatorio))) {
            editTextSobreNome.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            editTextSobreNome.requestFocus();
        }
        if (erro.contains(getString(R.string.nomeObrigatorio))) {
            editTextNome.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            editTextNome.requestFocus();
        }
    }

    private void verificaCPF(String cpf) {
        if (ValidaCPF.isCPF(cpf)) {
            imagemCpf.setImageDrawable(getDrawable(R.drawable.correto));
            editTextCpf.setBackground(getDrawable(R.drawable.meu_edit_text));
            isCpf = true;
            return;
        }
        setarErro(editTextCpf, imagemCpf);
    }

    private void verificaData(String data) {
        if (ValidaData.isData(getBaseContext(), data)) {
            imagemData.setImageDrawable(getDrawable(R.drawable.correto));
            editTextDataNasc.setBackground(getDrawable(R.drawable.meu_edit_text));
            isdata = true;
            return;
        }
        setarErro(editTextDataNasc, imagemData);
    }

    private void setarErro(EditText editText, ImageView imageView) {
        editText.setBackground(getDrawable(R.drawable.meu_edit_text_error));
        imageView.setImageDrawable(getDrawable(R.drawable.incorreto));
    }

    private void setarCampos() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        boolean temRegistro = extras.getBoolean("Tem Registro");
        if (temRegistro) {

        } else {
            editTextCpf.setText(extras.getString("CPF"));
            Toast.makeText(getBaseContext(), getString(R.string.CPF_nao_identificado), Toast.LENGTH_LONG).show();
        }
    }

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

                    if (str.length() == 11) verificaCPF(str);

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
                    if (str.length() == 10) {
                        setarErro(editTextCpf, imagemCpf);
                        isCpf = false;
                    }
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

    private void setarMascaraData() {
        editTextDataNasc.addTextChangedListener(new TextWatcher() {
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
                        .replaceAll("[/]", "");

                // as variáveis before e after dizem o tamanho
                // anterior e atual da String, se after > before
                // é pq está apagando
                if (after > before) {

                    if (str.length() == 8) verificaData(str);

                    if (str.length() > 4) {
                        str =
                                str.substring(0, 2) + '/' +
                                        str.substring(2, 4) + '/' +
                                        str.substring(4);

                    } else if (str.length() > 2) {
                        str =
                                str.substring(0, 2) + '/' +
                                        str.substring(2);
                    }
                    // Seta a flag pra evitar chamada infinita
                    isUpdating = true;
                    // seta o novo texto
                    editTextDataNasc.setText(str);
                    // seta a posição do cursor
                    editTextDataNasc.setSelection(editTextDataNasc.getText().length());

                } else {
                    isUpdating = true;
                    editTextDataNasc.setText(str);
                    if (str.length() == 7 && isdata) {
                        setarErro(editTextDataNasc, imagemData);
                        isdata = false;
                    }
                    // Se estiver apagando posiciona o cursor
                    // no local correto. Isso trata a deleção dos
                    // caracteres da máscara.
                    editTextDataNasc.setSelection(
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