package com.app_desconta.cardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app_desconta.R;
import com.app_desconta.api.Compra;

import java.util.ArrayList;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.RecycleViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListenet(OnItemClickListener listenet) {
        mlistener = listenet;
    }

    public static class RecycleViewHolder extends RecyclerView.ViewHolder {

        public TextView empresa;
        public TextView valor;
        public TextView data;

        public RecycleViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            empresa = itemView.findViewById(R.id.cv_nome_empresa);
            valor = itemView.findViewById(R.id.valor_compra);
            data = itemView.findViewById(R.id.cv_data_compra);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    private ArrayList<Compra> listaCompras;
    private OnItemClickListener mlistener;

    public RecycleViewAdapter(ArrayList<Compra> listaCompras) {
        this.listaCompras = listaCompras;
    }


    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout, parent, false);
        RecycleViewHolder recycleViewHolder = new RecycleViewHolder(view, mlistener);
        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {
        Compra itemAtual = listaCompras.get(position);

        holder.empresa.setText(itemAtual.getNomeFantasia());
        holder.valor.setText(itemAtual.getValorTotal());
        holder.data.setText(itemAtual.getDataVenda());

    }

    @Override
    public int getItemCount() {
        if (listaCompras != null) return listaCompras.size();
        return 0;
    }
}