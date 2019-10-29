package com.app_desconta;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app_desconta.api.Api;
import com.app_desconta.util.RetrofitCliente;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagarBoletoActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textoManu;

    private EditText numero_boleto;
    private EditText valor_boleto;

    private Button botaoVoltar;
    private Button pagar;

    String idParcela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar_boleto);

        numero_boleto = (EditText) findViewById(R.id.et_pagar_numero_boleto);
        textoManu = (TextView) findViewById(R.id.tv_detalhes_compra);
        valor_boleto = (EditText) findViewById(R.id.et_pagar_valor_boleto);
        botaoVoltar = (Button) findViewById(R.id.bt_voltar);
        pagar = (Button) findViewById(R.id.btn_pagarBoleto);

        textoManu.setText(getString(R.string.pagarBoleto));

        setarDados();

        botaoVoltar.setOnClickListener(this);
        pagar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_voltar:
                onBackPressed();
                break;
            case R.id.btn_pagarBoleto:
                retrofitPagarCompra();
                break;
            default:
                break;
        }
    }

    private void setarDados() {
        Intent intent = getIntent();

        Bundle extras = intent.getExtras();

        idParcela = extras.getString("id");
        String nrBoleto = extras.getString("nr_boleto");
        String valorBoleto = extras.getString("valor");

        numero_boleto.setText(nrBoleto);
        valor_boleto.setText(valorBoleto);
    }

    private void confirmarPagamento() {
        pagar.setVisibility(Button.VISIBLE);
        try {
            YoYo.with(Techniques.Wave) // FadeInDown, ZoomInDown, BounceInDown
                    .duration(680)
                    .playOn(pagar);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void retrofitPagarCompra() {
        Api httpRequest = RetrofitCliente.getCliente().create(Api.class);

        Call<String> call = httpRequest.pagarParcela(idParcela, getJson());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("test", "passei - " + response.code());
                if (response.isSuccessful()) {

                    if (response.code() == 200) {

                        confirmarPagamento();
                        Toast.makeText(getBaseContext(), getString(R.string.pagamento_realizado_comSucesso), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("Retrofit pagarParcela", "Falha no Retrofit: " + t.toString());

            }
        });
    }

    private JsonObject getJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("boleto_pago", "S");
        return jsonObject;
    }
}
