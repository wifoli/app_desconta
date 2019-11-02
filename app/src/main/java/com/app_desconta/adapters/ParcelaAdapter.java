package com.app_desconta.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app_desconta.PagarBoletoActivity;
import com.app_desconta.R;
import com.app_desconta.api.Parcela;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

public class ParcelaAdapter  extends RecyclerView.Adapter<ParcelaAdapter.MyViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView numero_parcela;
        public TextView numero_boleto;
        public TextView valor_parcela;
        public Button pagar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            numero_parcela = itemView.findViewById(R.id.numero_parcela);
            numero_boleto = itemView.findViewById(R.id.numero_boleto);
            valor_parcela = itemView.findViewById( R.id.valor_parcela);
            pagar = itemView.findViewById(R.id.btn_pagar);
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
        holder.numero_boleto.setText(" " + itemAtual.getNrBoleto());
        holder.valor_parcela.setText(" " + itemAtual.getValorParcela());
        holder.pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PagarBoletoActivity.class);
                Bundle extras = new Bundle();
                extras.putString("id", itemAtual.getId());
                extras.putString("nr_boleto", itemAtual.getNrBoleto());
                extras.putString("valor", itemAtual.getValorParcela());
                intent.putExtras(extras);
                context.startActivity(intent);
            }
        });

        if (itemAtual.getBoleto_pago().trim().equals("S")){
            holder.pagar.setBackgroundResource(R.drawable.meu_botao_parcela_paga);
            holder.pagar.setText(context.getString(R.string.boleto_pago));
            holder.pagar.setTextSize(10);
            holder.pagar.setForeground(null);
            holder.pagar.setClickable(false);
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
}