package com.app_desconta.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.app_desconta.MainActivity;
import com.app_desconta.R;
import com.app_desconta.RedefinirSenhaActivity;
import com.app_desconta.Usuario;
import com.app_desconta.api.Api;
import com.app_desconta.api.User;
import com.app_desconta.util.RetrofitCliente;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.app_desconta.util.Util.errosFirebase;
import static com.app_desconta.util.Util.verificaConexao;

public class Tela_login extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private Button bt_acessar;
    private CardView loginGoogle;
    private EditText editTextEmail;
    private EditText editTextSenha;
    private TextView esqueceuSenha;
    private TextView cadastrar;

    private String email;
    private String senha;

    private ProgressBar progressBar;

    private FirebaseAuth auth;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);

        bt_acessar = (Button) findViewById(R.id.bt_login_acessar);
        loginGoogle = (CardView) findViewById(R.id.logar_com_google);
        editTextEmail = (EditText) findViewById(R.id.et_login_login);
        editTextSenha = (EditText) findViewById(R.id.et_login_senha);
        esqueceuSenha = (TextView) findViewById(R.id.esqueceuSenha);
        cadastrar = (TextView) findViewById(R.id.et_cadastrese);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);

        auth = FirebaseAuth.getInstance();
        iniciarServicosGoogle();

        bt_acessar.setOnClickListener(this);
        cadastrar.setOnClickListener(this);
        esqueceuSenha.setOnClickListener(this);
        loginGoogle.setOnClickListener(this);
        editTextEmail.setOnFocusChangeListener(this);
        editTextSenha.setOnFocusChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_login_acessar:
                email = editTextEmail.getText().toString().trim();
                senha = editTextSenha.getText().toString().trim();

                if (!estaVazio() && verificaConexao(getBaseContext())) {
                    iniciarProgess();
                    logarComEmail();
                }
                break;
            case R.id.et_cadastrese:
                startActivity(new Intent(getBaseContext(), TelaCadastroEmail.class));
                break;
            case R.id.esqueceuSenha:
                startActivity(new Intent(getBaseContext(), RedefinirSenhaActivity.class));
                break;
            case R.id.logar_com_google:
                logarComGoogle();
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

    private void logarComEmail() {
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Usuario.getInsance().setarUid(task.getResult().getUser().getUid());
                    getUsuario();

                    //----------METODO PARA RETORNAR O TOKEN--------------
                   /* Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()).getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        @Override
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()){
                                abrirApp();
                            }
                        }
                    });  */
                } else {
                    errosFirebase(getBaseContext(), task.getException().toString());
                    fecharProgess();
                }
            }
        });

    }

    private void logarComGoogle() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account == null) {
            Intent intent = Usuario.getInsance().getGoogleSignInClient().getSignInIntent();
            startActivityForResult(intent, 555);
        }else{
            Usuario.getInsance().getGoogleSignInClient().signOut();
            logarComGoogle();
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 555) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                adicionarContaGoogleAoFirebase(account);
                iniciarProgess();

            } catch (ApiException e) {
                Toast.makeText(getBaseContext(), getString(R.string.erroAoLogarComGoogle), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void iniciarServicosGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        Usuario.getInsance().setGoogleSignInClient(GoogleSignIn.getClient(this, gso));
    }


    private void adicionarContaGoogleAoFirebase(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Usuario.getInsance().setarUid(auth.getCurrentUser().getUid());
                            getUsuario();
                        } else
                            Toast.makeText(getBaseContext(), getString(R.string.erroAoAdicionarContaGoogleAoFirebase), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void verificarSeExisteUsuario() {
        fecharProgess();
        if (Usuario.getInsance().getUsuario().getId().trim().equals("")) {
            Usuario.getInsance().setEmail(auth.getCurrentUser().getEmail());
            startActivity(new Intent(getBaseContext(), TelaVerificarCpf.class));
        } else {
            startActivity(new Intent(getBaseContext(), MainActivity.class));
            finish();
        }
    }

    private void getUsuario() {

        Api httpRequest = RetrofitCliente.getCliente().create(Api.class);

        Call<User> call = httpRequest.getUsuario(Usuario.getInsance().getUid());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Usuario.getInsance().setUsuario(response.body());
                verificarSeExisteUsuario();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Retrofit get_usuario", "Falha no Retrofit: " + t.toString());

            }
        });
    }

    private void iniciarProgess() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    private void fecharProgess() {
        progressBar.setVisibility(ProgressBar.GONE);
    }
}