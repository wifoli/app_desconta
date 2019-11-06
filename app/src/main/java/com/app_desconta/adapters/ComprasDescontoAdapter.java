package com.app_desconta.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app_desconta.R;
import com.app_desconta.api.Compras;
import com.app_desconta.api.Empresa;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

public class ComprasDescontoAdapter extends RecyclerView.Adapter<ComprasDescontoAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListenet(OnItemClickListener listenet) {
        mlistener = listenet;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView descricaoCompras;


        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            descricaoCompras = itemView.findViewById(R.id.descricao_compra);

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

    private ArrayList<Compras> listaCompra;
    private OnItemClickListener mlistener;
    private LayoutInflater mlaLayoutInflater;
    private Context context;

    public ComprasDescontoAdapter(Context context, ArrayList<Compras> listaCompra) {
        this.listaCompra = listaCompra;
        this.context = context;
        mlaLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mlaLayoutInflater.inflate(R.layout.item_comppras_desconto, parent, false);
        MyViewHolder recycleViewHolder = new MyViewHolder(view, mlistener);
        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Compras itemAtual = listaCompra.get(position);

        holder.descricaoCompras.setText(itemAtual.getValorTotal()+ " ainda não sei oque vou por aqui");

        try {
            YoYo.with(Techniques.ZoomInDown) // FadeInDown, ZoomInDown, BounceInDown
                    .duration(350)
                    .playOn(holder.itemView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        if (listaCompra != null) return listaCompra.size();
        return 0;
    }
}