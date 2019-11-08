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
import com.app_desconta.api.Pessoa;
import com.app_desconta.util.ValidaCPF;
import com.app_desconta.util.ValidaData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.app_desconta.util.Sair.sair;
import static com.app_desconta.util.Util.verificaConexao;


public class TelaCadastroDadosPessoais extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private EditText editTextNome;
    private EditText editTextSobreNome;
    private EditText editTextRg;
    private EditText editTextCpf;
    private EditText editTextDataNasc;
    private EditText editTextTelefone1;
    private EditText editTextTelefone2;

    private ImageView imagemData;
    private ImageView imagemCpf;

    private Button buttonProximo;

    private String nome;
    private String sobrenome;
    private String rg;
    private String cpf;
    private String dataNasc;
    private String telefone1;
    private String telefone2;

    private boolean isCpf = false;
    private boolean isdata = false;

    private Bundle extras = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro_dados_pessoais);
        setTitle("Dados pessoais");

        editTextNome = (EditText) findViewById(R.id.et_cadastrar_nome);
        editTextSobreNome = (EditText) findViewById(R.id.et_cadastrar_sobrenome);
        editTextRg = (EditText) findViewById(R.id.et_cadastrar_rg);
        editTextCpf = (EditText) findViewById(R.id.et_cadastrar_cpf);
        editTextDataNasc = (EditText) findViewById(R.id.et_cadastrar_dataNasc);
        editTextTelefone1 = (EditText) findViewById(R.id.et_cadastrar_telefone1);
        editTextTelefone2 = (EditText) findViewById(R.id.et_cadastrar_telefone2);
        imagemData = (ImageView) findViewById(R.id.iv_cadastrar_validacaoData);
        imagemCpf = (ImageView) findViewById(R.id.iv_cadastrar_validacaoCPF);
        buttonProximo = (Button) findViewById(R.id.bt_cadastrar_proximo);

        buttonProximo.setOnClickListener(this);
        editTextNome.setOnFocusChangeListener(this);
        editTextSobreNome.setOnFocusChangeListener(this);
        editTextRg.setOnFocusChangeListener(this);
        editTextCpf.setOnFocusChangeListener(this);
        editTextDataNasc.setOnFocusChangeListener(this);
        editTextTelefone1.setOnFocusChangeListener(this);
        setarMascaraCPF();
        setarMascaraData();
        setarMascaraTelefone(editTextTelefone1);
        setarMascaraTelefone(editTextTelefone2);
        setarCampos();
    }

    @Override
    protected void onDestroy() {
        //sair();
        Log.d("test", "deytroy");
        super.onDestroy();
    }


    @Override
    public void onClick(View view) {
        nome = editTextNome.getText().toString().trim();
        sobrenome = editTextSobreNome.getText().toString().trim();
        rg = editTextRg.getText().toString().trim();
        cpf = editTextCpf.getText().toString().trim();
        dataNasc = editTextDataNasc.getText().toString().trim();
        telefone1 = editTextTelefone1.getText().toString().trim();
        telefone2 = editTextTelefone2.getText().toString().trim();

        if (!estaVazio() && (isCpf) && (isdata) && (verificaTelefone()) && (verificaConexao(getBaseContext()))) {
            setarPessoa();
            Intent intent = new Intent(this, TelaCadastroLocalizacao.class);
            intent.putExtras(extras);
            startActivity(intent);
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
        if (nome.isEmpty()) erro = getString(R.string.nomeObrigatorio);
        if (erro.contains(getString(R.string.nomeObrigatorio))) erro += "\n";
        if (sobrenome.isEmpty()) erro += getString(R.string.sobrenomeObrigatorio);
        if (erro.contains(getString(R.string.sobrenomeObrigatorio))) erro += "\n";
        if (rg.isEmpty()) erro += getString(R.string.rgObrigatorio);
        if (erro.contains(getString(R.string.rgObrigatorio))) erro += "\n";
        if (cpf.isEmpty()) erro += getString(R.string.cpfObrigatorio);
        if (erro.contains(getString(R.string.cpfObrigatorio))) erro += "\n";
        if (dataNasc.isEmpty()) erro += getString(R.string.dataNascObrigatorio);
        if (erro.contains(getString(R.string.dataNascObrigatorio))) erro += "\n";
        if (telefone1.isEmpty()) erro += getString(R.string.telefoneObrigatorio);
        if (erro.isEmpty()) return false;
        corrigirCampos(erro);
        Toast.makeText(getBaseContext(), erro, Toast.LENGTH_LONG).show();
        return true;
    }

    private void corrigirCampos(String erro) {
        if (erro.contains(getString(R.string.telefoneObrigatorio))) {
            editTextTelefone1.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            editTextTelefone1.requestFocus();
        }
        if (erro.contains(getString(R.string.dataNascObrigatorio))) {
            editTextDataNasc.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            editTextDataNasc.requestFocus();
        }
        if (erro.contains(getString(R.string.cpfObrigatorio))) {
            editTextCpf.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            editTextCpf.requestFocus();
        }
        if (erro.contains(getString(R.string.rgObrigatorio))) {
            editTextRg.setBackground(getDrawable(R.drawable.meu_edit_text_error));
            editTextRg.requestFocus();
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

    private boolean verificaTelefone() {
        String tel = telefone1.replaceAll("[(]", "")
                .replaceAll("[)]", "")
                .replaceAll("[.]", "")
                .replaceAll("[-]", "");
        if (tel.length() == 11) return true;
        editTextTelefone1.setBackground(getDrawable(R.drawable.meu_edit_text_error));
        return false;
    }

    private void setarCampos() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        boolean temRegistro = extras.getBoolean("Tem Registro");
        if (temRegistro) {
            Pessoa pessoa = Usuario.getInsance().getUsuario().getPessoa();
            editTextNome.setText(pessoa.getNome());
            editTextSobreNome.setText(pessoa.getSobrenome());
            editTextCpf.setText(pessoa.getCpf());
            editTextCpf.setFocusable(false);

            this.extras.putBoolean("Tem Registro", true);
        } else {
            editTextCpf.setText(extras.getString("CPF"));
            Toast.makeText(getBaseContext(), getString(R.string.CPF_nao_identificado), Toast.LENGTH_LONG).show();
            this.extras.putBoolean("Tem Registro", false);
        }
    }

    private void setarPessoa() {
        Usuario.getInsance().getUsuario().getPessoa().setNome(nome);
        Usuario.getInsance().getUsuario().getPessoa().setSobrenome(sobrenome);
        Usuario.getInsance().getUsuario().getPessoa().setCpf(cpf.replaceAll("[.]", "").replaceAll("[-]", ""));
        Usuario.getInsance().getUsuario().getPessoa().setRg(rg);
        Usuario.getInsance().getUsuario().getPessoa().setDataNasc(retornaDataConvertida());
        Usuario.getInsance().getUsuario().getPessoa().setTel1(telefone1);
        Usuario.getInsance().getUsuario().getPessoa().setTel2(telefone2);
    }


    private String retornaDataConvertida(){
        Date date = null;
        String dataString = "";
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = new java.sql.Date( ((java.util.Date)formatter.parse(dataNasc)).getTime() );

            dataString = date.toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dataString;
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
                        setarErro(editTextCpf, imagemCpf);
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

                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                boolean hasMask =
                        s.toString().indexOf('/') > -1;

                String str = s.toString()
                        .replaceAll("[/]", "");

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
                    isUpdating = true;
                    editTextDataNasc.setText(str);
                    editTextDataNasc.setSelection(editTextDataNasc.getText().length());

                } else {
                    isUpdating = true;
                    editTextDataNasc.setText(str);
                    if (str.length() == 7 && isdata) {
                        setarErro(editTextDataNasc, imagemData);
                        isdata = false;
                    }

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

    private void setarMascaraTelefone(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
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
                        s.toString().indexOf('(') > -1 ||
                                s.toString().indexOf(')') > -1 ||
                                s.toString().indexOf('-') > -1 ||
                                s.toString().indexOf('.') > -1;

                String str = s.toString()
                        .replaceAll("[(]", "")
                        .replaceAll("[)]", "")
                        .replaceAll("[-]", "")
                        .replaceAll("[.]", "");

                if (after > before) {
                    if (str.length() > 7) {
                        str =
                                '(' +
                                        str.substring(0, 2) + ')' +
                                        str.substring(2, 3) + '.' +
                                        str.substring(3, 7) + '-' +
                                        str.substring(7);
                    } else if (str.length() > 3) {
                        str =
                                '(' +
                                        str.substring(0, 2) + ')' +
                                        str.substring(2, 3) + '.' +
                                        str.substring(3);
                    } else if (str.length() > 2) {
                        str =
                                '(' +
                                        str.substring(0, 2) + ')' +
                                        str.substring(2);
                    } else if (str.length() > 0) {
                        str = '(' + str;
                    }
                    isUpdating = true;
                    editText.setText(str);
                    editText.setSelection(editText.getText().length());

                } else {
                    isUpdating = true;
                    editText.setText(str);
                    editText.setSelection(
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