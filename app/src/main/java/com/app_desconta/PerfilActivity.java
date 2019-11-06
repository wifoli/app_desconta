package com.app_desconta;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app_desconta.api.Api;
import com.app_desconta.api.Pessoa;
import com.app_desconta.fragments.AtualizarEnderecoFragment;
import com.app_desconta.util.RetrofitCliente;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView nomeToolbar;
    private ImageView voltarToolbar;

    //Nome
    private TextView textViewNome;
    private TextView textViewNovoNome;
    private TextView btnAtualizarNome;
    private EditText editTextNovoNome;
    private ImageView imageViewNome;

    //Sobrenome
    private TextView textViewSobrenome;
    private TextView textViewNovoSobrenome;
    private TextView btnAtualizarSobrenome;
    private EditText editTextNovoSobrenome;
    private ImageView imageViewSobrenome;

    //Telefone1
    private TextView textViewTelefone1;
    private TextView textViewNovoTelefone1;
    private TextView btnAtualizarTelefone1;
    private EditText editTextNovoTelefone1;
    private ImageView imageViewTelefone1;

    //Telefone2
    private TextView textViewTelefone2;
    private TextView textViewNovoTelefone2;
    private TextView btnAtualizarTelefone2;
    private EditText editTextNovoTelefone2;
    private ImageView imageViewTelefone2;

    //Endereco
    private FrameLayout frameLayoutEndereco;
    private TextView textViewEndereco;
    private ImageView imageViewEndereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        nomeToolbar = (TextView) findViewById(R.id.text_toolbar_drawer);
        voltarToolbar = (ImageView) findViewById(R.id.booton_toolbar_drawer);

        nomeToolbar.setText(getString(R.string.dn_perfil));

        setarViews();
        setarListeners();
        setarPerfilAtual();
        setarMascaraTelefone(editTextNovoTelefone1);
        setarMascaraTelefone(editTextNovoTelefone2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.booton_toolbar_drawer:
                onBackPressed();
                break;
            case R.id.image_nome:
                if (btnAtualizarNome.getVisibility() == TextView.GONE) {
                    List<View> listaNome = new ArrayList<>();
                    listaNome.add(textViewNovoNome);
                    listaNome.add(editTextNovoNome);
                    listaNome.add(btnAtualizarNome);
                    textViewNovoNome.setVisibility(TextView.VISIBLE);
                    editTextNovoNome.setVisibility(EditText.VISIBLE);
                    btnAtualizarNome.setVisibility(TextView.VISIBLE);
                    imageViewNome.setBackground(getDrawable(R.drawable.ic_expand_less_black_24dp));
                    setarAnimacao(Techniques.SlideInDown, listaNome);
                } else {
                    textViewNovoNome.setVisibility(TextView.GONE);
                    editTextNovoNome.setVisibility(EditText.GONE);
                    btnAtualizarNome.setVisibility(TextView.GONE);
                    imageViewNome.setBackground(getDrawable(R.drawable.ic_expand_more_black_24dp));
                }
                break;
            case R.id.btn_atualizar_nome:
                List<View> listaUpNome = new ArrayList<>();
                listaUpNome.add(textViewNovoNome);
                listaUpNome.add(editTextNovoNome);
                listaUpNome.add(btnAtualizarNome);

                upPessoa("nome", editTextNovoNome.getText().toString(), textViewNome, imageViewNome, listaUpNome, getString(R.string.nome_atualizado_sucesso), this);
                break;
            case R.id.image_sobrenome:
                if (btnAtualizarSobrenome.getVisibility() == TextView.GONE) {
                    List<View> listaSobrenome = new ArrayList<>();
                    listaSobrenome.add(textViewNovoSobrenome);
                    listaSobrenome.add(editTextNovoSobrenome);
                    listaSobrenome.add(btnAtualizarSobrenome);
                    textViewNovoSobrenome.setVisibility(TextView.VISIBLE);
                    editTextNovoSobrenome.setVisibility(EditText.VISIBLE);
                    btnAtualizarSobrenome.setVisibility(TextView.VISIBLE);
                    imageViewSobrenome.setBackground(getDrawable(R.drawable.ic_expand_less_black_24dp));
                    setarAnimacao(Techniques.SlideInDown, listaSobrenome);
                } else {
                    textViewNovoSobrenome.setVisibility(TextView.GONE);
                    editTextNovoSobrenome.setVisibility(EditText.GONE);
                    btnAtualizarSobrenome.setVisibility(TextView.GONE);
                    imageViewSobrenome.setBackground(getDrawable(R.drawable.ic_expand_more_black_24dp));
                }
                break;
            case R.id.btn_atualizar_sobrenome:
                List<View> listaUpSobrenome = new ArrayList<>();
                listaUpSobrenome.add(textViewNovoSobrenome);
                listaUpSobrenome.add(editTextNovoSobrenome);
                listaUpSobrenome.add(btnAtualizarSobrenome);

                upPessoa("sobrenome", editTextNovoSobrenome.getText().toString(), textViewSobrenome, imageViewSobrenome, listaUpSobrenome, getString(R.string.sobrenome_atualizado_sucesso), this);
                break;
            case R.id.image_telefone1:
                if (btnAtualizarTelefone1.getVisibility() == TextView.GONE) {
                    List<View> listaTelefone1 = new ArrayList<>();
                    listaTelefone1.add(textViewNovoTelefone1);
                    listaTelefone1.add(editTextNovoTelefone1);
                    listaTelefone1.add(btnAtualizarTelefone1);
                    textViewNovoTelefone1.setVisibility(TextView.VISIBLE);
                    editTextNovoTelefone1.setVisibility(EditText.VISIBLE);
                    btnAtualizarTelefone1.setVisibility(TextView.VISIBLE);
                    imageViewTelefone1.setBackground(getDrawable(R.drawable.ic_expand_less_black_24dp));
                    setarAnimacao(Techniques.SlideInDown, listaTelefone1);
                } else {
                    textViewNovoTelefone1.setVisibility(TextView.GONE);
                    editTextNovoTelefone1.setVisibility(EditText.GONE);
                    btnAtualizarTelefone1.setVisibility(TextView.GONE);
                    imageViewTelefone1.setBackground(getDrawable(R.drawable.ic_expand_more_black_24dp));
                }
                break;
            case R.id.btn_atualizar_telefone1:
                List<View> listaUpTelefone1 = new ArrayList<>();
                listaUpTelefone1.add(textViewNovoTelefone1);
                listaUpTelefone1.add(editTextNovoTelefone1);
                listaUpTelefone1.add(btnAtualizarTelefone1);

                upPessoa("tel_1", editTextNovoTelefone1.getText().toString(), textViewTelefone1, imageViewTelefone1, listaUpTelefone1, getString(R.string.telefone_atualizado_sucesso), this);
                break;
            case R.id.image_telefone2:
                if (btnAtualizarTelefone2.getVisibility() == TextView.GONE) {
                    List<View> listaTelefone2 = new ArrayList<>();
                    listaTelefone2.add(textViewNovoTelefone2);
                    listaTelefone2.add(editTextNovoTelefone2);
                    listaTelefone2.add(btnAtualizarTelefone2);
                    textViewNovoTelefone2.setVisibility(TextView.VISIBLE);
                    editTextNovoTelefone2.setVisibility(EditText.VISIBLE);
                    btnAtualizarTelefone2.setVisibility(TextView.VISIBLE);
                    imageViewTelefone2.setBackground(getDrawable(R.drawable.ic_expand_less_black_24dp));
                    setarAnimacao(Techniques.SlideInDown, listaTelefone2);
                } else {
                    textViewNovoTelefone2.setVisibility(TextView.GONE);
                    editTextNovoTelefone2.setVisibility(EditText.GONE);
                    btnAtualizarTelefone2.setVisibility(TextView.GONE);
                    imageViewTelefone2.setBackground(getDrawable(R.drawable.ic_expand_more_black_24dp));
                }
                break;
            case R.id.btn_atualizar_telefone2:
                List<View> listaUpTelefone2 = new ArrayList<>();
                listaUpTelefone2.add(textViewNovoTelefone2);
                listaUpTelefone2.add(editTextNovoTelefone2);
                listaUpTelefone2.add(btnAtualizarTelefone2);

                upPessoa("tel_2", editTextNovoTelefone2.getText().toString(), textViewTelefone2, imageViewTelefone2, listaUpTelefone2, getString(R.string.telefone_atualizado_sucesso), this);
                break;
            case R.id.image_endereco:
                if (frameLayoutEndereco.getVisibility() == FrameLayout.GONE) {
                    List<View> listaEndereco = new ArrayList<>();
                    listaEndereco.add(frameLayoutEndereco);
                    frameLayoutEndereco.setVisibility(FrameLayout.VISIBLE);
                    setarAnimacao(Techniques.SlideInDown, listaEndereco);
                    imageViewEndereco.setBackground(getDrawable(R.drawable.ic_expand_less_black_24dp));
                }else{
                    frameLayoutEndereco.setVisibility(FrameLayout.GONE);
                    imageViewEndereco.setBackground(getDrawable(R.drawable.ic_expand_more_black_24dp));
                }
                break;
        }
    }

    private void setarViews() {
        textViewNome = (TextView) findViewById(R.id.nome_perfil);
        textViewNovoNome = (TextView) findViewById(R.id.novo_nome_perfil);
        btnAtualizarNome = (TextView) findViewById(R.id.btn_atualizar_nome);
        editTextNovoNome = (EditText) findViewById(R.id.edit_novo_nome_perfil);
        imageViewNome = (ImageView) findViewById(R.id.image_nome);

        textViewSobrenome = (TextView) findViewById(R.id.sobrenome_perfil);
        textViewNovoSobrenome = (TextView) findViewById(R.id.novo_sobrenome_perfil);
        btnAtualizarSobrenome = (TextView) findViewById(R.id.btn_atualizar_sobrenome);
        editTextNovoSobrenome = (EditText) findViewById(R.id.edit_novo_sobrenome_perfil);
        imageViewSobrenome = (ImageView) findViewById(R.id.image_sobrenome);

        textViewTelefone1 = (TextView) findViewById(R.id.telefone1_perfil);
        textViewNovoTelefone1 = (TextView) findViewById(R.id.novo_telefone1_perfil);
        btnAtualizarTelefone1 = (TextView) findViewById(R.id.btn_atualizar_telefone1);
        editTextNovoTelefone1 = (EditText) findViewById(R.id.edit_novo_telefone1_perfil);
        imageViewTelefone1 = (ImageView) findViewById(R.id.image_telefone1);

        textViewTelefone2 = (TextView) findViewById(R.id.telefone2_perfil);
        textViewNovoTelefone2 = (TextView) findViewById(R.id.novo_telefone2_perfil);
        btnAtualizarTelefone2 = (TextView) findViewById(R.id.btn_atualizar_telefone2);
        editTextNovoTelefone2 = (EditText) findViewById(R.id.edit_novo_telefone2_perfil);
        imageViewTelefone2 = (ImageView) findViewById(R.id.image_telefone2);

        textViewEndereco = (TextView) findViewById(R.id.endereco_perfil);
        imageViewEndereco = (ImageView) findViewById(R.id.image_endereco);
        frameLayoutEndereco = (FrameLayout) findViewById(R.id.frameEndereco);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frameEndereco, new AtualizarEnderecoFragment());
        ft.commit();
    }

    private void setarListeners() {
        voltarToolbar.setOnClickListener(this);

        imageViewNome.setOnClickListener(this);
        btnAtualizarNome.setOnClickListener(this);

        imageViewSobrenome.setOnClickListener(this);
        btnAtualizarSobrenome.setOnClickListener(this);

        imageViewTelefone1.setOnClickListener(this);
        btnAtualizarTelefone1.setOnClickListener(this);

        imageViewTelefone2.setOnClickListener(this);
        btnAtualizarTelefone2.setOnClickListener(this);

        imageViewEndereco.setOnClickListener(this);
    }

    private void setarPerfilAtual() {
        textViewNome.setText(Usuario.getInsance().getUsuario().getPessoa().getNome());
        textViewSobrenome.setText(Usuario.getInsance().getUsuario().getPessoa().getSobrenome());
        textViewTelefone1.setText(Usuario.getInsance().getUsuario().getPessoa().getTel1());
        textViewTelefone2.setText(Usuario.getInsance().getUsuario().getPessoa().getTel2());
        textViewEndereco.setText(getString(R.string.meu_endereco));
    }

    private void setarAnimacao(Techniques animacao, List<View> lista) {
        for (View view : lista) {
            try {
                YoYo.with(animacao) // FadeInDown, ZoomInDown, BounceInDown
                        .duration(500)
                        .playOn(view);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void upPessoa(String campoBanco, final String valorNovo, final TextView textView, final ImageView imageView, final List<View> viewList, final String message, final Context context) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("campo", campoBanco);
        jsonObject.addProperty("valorNovo", valorNovo);

        Api httpRequest = RetrofitCliente.getCliente().create(Api.class);

        Call<Pessoa> call = httpRequest.atualizarPessoa(Usuario.getInsance().getUsuario().getPessoa().getId(), jsonObject);
        call.enqueue(new Callback<Pessoa>() {
            @Override
            public void onResponse(Call<Pessoa> call, Response<Pessoa> response) {
                Usuario.getInsance().getUsuario().setPessoa(response.body());
                textView.setText(valorNovo);
                imageView.setBackground(getDrawable(R.drawable.ic_expand_more_black_24dp));
                for (View view : viewList) {
                    view.setVisibility(View.GONE);
                }
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Pessoa> call, Throwable t) {
                Log.e("Retrofit UP Pessoa", "Falha no Retrofit: " + t.toString());

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
