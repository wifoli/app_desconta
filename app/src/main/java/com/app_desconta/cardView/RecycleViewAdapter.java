package com.app_desconta.cardView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app_desconta.R;
import com.app_desconta.api.PojoCompra;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.RecycleViewHolder> {

    private ArrayList<PojoCompra> listaCompras;

    public  static class RecycleViewHolder extends RecyclerView.ViewHolder{

        public TextView empresa;
        public TextView valor;
        public TextView data;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            empresa = itemView.findViewById(R.id.cv_nome_empresa);
            valor = itemView.findViewById(R.id.valor_compra);
            data = itemView.findViewById(R.id.data_compra);
        }
    }

    public RecycleViewAdapter(ArrayList<PojoCompra> listaCompras) {
        this.listaCompras = listaCompras;
    }


    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
        RecycleViewHolder recycleViewHolder = new RecycleViewHolder(view);
        Log.d("onCreateViewHolder", "passei");
        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {
        PojoCompra itemAtual = listaCompras.get(position);

        holder.empresa.setText(itemAtual.getNomeFantasia());
        holder.valor.setText(itemAtual.getValorTotal());
        holder.data.setText(itemAtual.getDataVenda());

        Log.d("Recicler",listaCompras.get(position).getNomeFantasia());
        Log.d("Recicler",listaCompras.get(position).getValorTotal());
        Log.d("Recicler",listaCompras.get(position).getDataVenda());
        Log.d("Recicler","");
    }

    @Override
    public int getItemCount() {
        return listaCompras.size();
    }
}
