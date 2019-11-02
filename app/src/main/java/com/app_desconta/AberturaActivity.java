package com.app_desconta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

import com.app_desconta.api.Api;
import com.app_desconta.api.User;
import com.app_desconta.login.Tela_login;
import com.app_desconta.util.RetrofitCliente;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AberturaActivity extends AppCompatActivity implements Runnable{

    private ProgressBar progressBar;
    private Thread thread;
    private Handler handler;
    Intent intent;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abertura);

        handler = new Handler();
        thread = new Thread(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_abertura);

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            Usuario.getInsance().setarUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
            getUsuario();
            intent = new Intent(this, MainActivity.class);
        }else{
            intent = new Intent(this, Tela_login.class);
            thread.start();
        }
    }

    @Override
    public void run() {
        i = 1;
        try {
            while (i <100){
                Thread.sleep(10);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        i++;
                        progressBar.setProgress(i);
                    }
                });
            }
            finish();
            startActivity(intent);

        }catch (InterruptedException e){

        }
    }

    private void getUsuario() {

        Api httpRequest = RetrofitCliente.getCliente().create(Api.class);

        Call<User> call = httpRequest.getUsuario(Usuario.getInsance().getUid());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Usuario.getInsance().setUsuario(response.body());
                thread.start();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Retrofit get_usuario", "Falha no Retrofit: " + t.toString());
            }
        });
    }

}
