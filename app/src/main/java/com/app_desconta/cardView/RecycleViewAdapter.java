package com.app_desconta.cardView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app_desconta.R;
import com.app_desconta.api.Compras;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

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

    private ArrayList<Compras> listaCompras;
    private OnItemClickListener mlistener;
    private LayoutInflater mlaLayoutInflater;

    public RecycleViewAdapter(Context context, ArrayList<Compras> listaCompras) {
        this.listaCompras = listaCompras;
        mlaLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mlaLayoutInflater.inflate(R.layout.card_view_layout, parent, false);
        RecycleViewHolder recycleViewHolder = new RecycleViewHolder(view, mlistener);
        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {
        Compras itemAtual = listaCompras.get(position);

        holder.empresa.setText(itemAtual.getNomeFantasia());
        holder.valor.setText(" " + itemAtual.getValorTotal());
        holder.data.setText(" " + itemAtual.getDataVenda());

        try {
            YoYo.with(Techniques.ZoomInDown) // FadeInDown, ZoomInDown, BounceInDown
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
        if (listaCompras != null) return listaCompras.size();
        return 0;
    }
}