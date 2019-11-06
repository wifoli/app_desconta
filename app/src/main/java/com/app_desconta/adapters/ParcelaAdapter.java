package com.app_desconta.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app_desconta.PagarBoletoActivity;
import com.app_desconta.R;
import com.app_desconta.api.Api;
import com.app_desconta.api.Parcela;
import com.app_desconta.util.RetrofitCliente;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParcelaAdapter  extends RecyclerView.Adapter<ParcelaAdapter.MyViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView numero_parcela;
        public TextView data_vencimento;
        public TextView valor_parcela;
        public Button gerar_boleto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            numero_parcela = itemView.findViewById(R.id.numero_parcela);
            data_vencimento = itemView.findViewById(R.id.data_vencimento);
            valor_parcela = itemView.findViewById( R.id.valor_parcela);
            gerar_boleto = itemView.findViewById(R.id.btn_pagar);
        }
    }

    private ArrayList<Parcela> listaParcela;
    private LayoutInflater mlaLayoutInflater;
    private Context context;

    public ParcelaAdapter(Context context, ArrayList<Parcela> listaParcela) {
        this.listaParcela = listaParcela;
        this.context = context;
        mlaLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mlaLayoutInflater.inflate(R.layout.item_parcelas, parent, false);
        MyViewHolder recycleViewHolder = new MyViewHolder(view);
        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Parcela itemAtual = listaParcela.get(position);

        holder.numero_parcela.setText(" " + itemAtual.getNrParcela());
        holder.data_vencimento.setText(" " + itemAtual.getData_vencimento());
        holder.valor_parcela.setText(" " + itemAtual.getValorParcela());
        holder.gerar_boleto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrofitGerarBoleto(itemAtual);

            }
        });

        if (itemAtual.getBoleto_pago().trim().equals("S")){
            holder.gerar_boleto.setBackgroundResource(R.drawable.meu_botao_parcela_paga);
            holder.gerar_boleto.setText(context.getString(R.string.boleto_pago));
            holder.gerar_boleto.setTextSize(10);
            holder.gerar_boleto.setForeground(null);
            holder.gerar_boleto.setClickable(false);
        }

        try {
            YoYo.with(Techniques.FadeInDown) // FadeInDown, ZoomInDown, BounceInDown
                    .duration(680)
                    .playOn(holder.itemView);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        if (listaParcela != null) return listaParcela.size();
        return 0;
    }


    private void retrofitGerarBoleto(final Parcela parcela){
        Api httpRequest = RetrofitCliente.getCliente().create(Api.class);

        Call<Parcela> call = httpRequest.gerarBoleto(parcela.getId());
        call.enqueue(new Callback<Parcela>() {
            @Override
            public void onResponse(Call<Parcela> call, Response<Parcela> response) {
                if (response.isSuccessful()){
                    Log.d("test", "passei");
                    Intent intent = new Intent(context, PagarBoletoActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("id", parcela.getId());
                    extras.putString("nr_boleto", response.body().getNrBoleto());
                    extras.putString("valor", parcela.getValorParcela());
                    intent.putExtras(extras);
                    context.startActivity(intent);
                }else{
                    Log.d("test", "deu erro - " + response.code());

                }
            }

            @Override
            public void onFailure(Call<Parcela> call, Throwable t) {
                Log.e("Retrofit gerar_boleto", "Falha no Retrofit: " + t.toString());

            }
        });
    }
}