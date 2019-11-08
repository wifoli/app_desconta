package com.app_desconta;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app_desconta.fragments.ParcelasFragment;
import com.app_desconta.util.FormataData;
import com.app_desconta.util.FormataValorEmReal;

public class DetalhesCompraActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView nome_fantasia;
    private TextView data_compra;
    private TextView valor_compra;

    private ImageView botaoVoltar;

    private Bundle extras;

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_compra);

        nome_fantasia = (TextView) findViewById(R.id.nome_fantasia_detalhe);
        data_compra = (TextView) findViewById(R.id.data_compra_detalhes);
        valor_compra = (TextView) findViewById(R.id.valor_detalhes);

        botaoVoltar = (ImageView) findViewById(R.id.bt_voltar);

        Intent intent = getIntent();
        extras = intent.getExtras();

        setarCompra();

        botaoVoltar.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        setarFragment();
        super.onResume();
    }


    @Override
    public void onClick(View view) {
        onBackPressed();
    }

    private void setarCompra() {
        nome_fantasia.setText(" " + extras.getString("nomeEmpresa"));
        data_compra.setText(" " + FormataData.formataData(extras.getString("data")));
        valor_compra.setText(" " + FormataValorEmReal.formataValorEmReal(extras.getString("valor")));
    }

    private String idCompra() {
        String id = extras.getString("id");
        return id;
    }

    private void setarFragment() {
        if (fm == null) {
            fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            ParcelasFragment parcelasFragment = new ParcelasFragment();

            Bundle extra = new Bundle();
            extra.putString("idCompra", idCompra());
            parcelasFragment.setArguments(extra);
            ft.add(R.id.frameParcelas, parcelasFragment);
            ft.commit();
        }else {
            FragmentTransaction ft = fm.beginTransaction();

            ParcelasFragment parcelasFragment = new ParcelasFragment();

            Bundle extra = new Bundle();
            extra.putString("idCompra", idCompra());
            parcelasFragment.setArguments(extra);
            ft.replace(R.id.frameParcelas, parcelasFragment);
            ft.commit();
        }
    }
}
