package com.app_desconta.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app_desconta.R;
import com.app_desconta.api.Empresa;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

public class EmpresaDescontoAdapter extends RecyclerView.Adapter<EmpresaDescontoAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListenet(OnItemClickListener listenet) {
        mlistener = listenet;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView descricaoEmpresa;


        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            descricaoEmpresa = itemView.findViewById(R.id.descricao_empresa);

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

    private ArrayList<Empresa> listaEmpresa;
    private OnItemClickListener mlistener;
    private LayoutInflater mlaLayoutInflater;
    private Context context;

    public EmpresaDescontoAdapter(Context context, ArrayList<Empresa> listaEmpresa) {
        this.listaEmpresa = listaEmpresa;
        this.context = context;
        mlaLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mlaLayoutInflater.inflate(R.layout.item_empresa_desconto, parent, false);
        MyViewHolder recycleViewHolder = new MyViewHolder(view, mlistener);
        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Empresa itemAtual = listaEmpresa.get(position);

        holder.descricaoEmpresa.setText(itemAtual.getRazaoSocial() + " (" + itemAtual.getNomeFantasia() + ")");

        try {
            YoYo.with(Techniques.FadeInDown) // FadeInDown, ZoomInDown, BounceInDown
                    .duration(680)
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
        if (listaEmpresa != null) return listaEmpresa.size();
        return 0;
    }
}