package com.app_desconta.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app_desconta.R;
import com.app_desconta.api.Empresa;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

public class EmpresaAdapter  extends RecyclerView.Adapter<EmpresaAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListenet(OnItemClickListener listenet) {
        mlistener = listenet;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView razao_social;
        public TextView nome_fantasia;
        public TextView cnpj;
        public TextView telefone;
        public ImageView mais;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            razao_social = itemView.findViewById(R.id.razao_social);
            nome_fantasia = itemView.findViewById(R.id.nome_fantasia);
            cnpj = itemView.findViewById(R.id.cnpj);
            telefone = itemView.findViewById(R.id.tel);

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

    public EmpresaAdapter(Context context, ArrayList<Empresa> listaEmpresa) {
        this.listaEmpresa = listaEmpresa;
        mlaLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mlaLayoutInflater.inflate(R.layout.item_empresa, parent, false);
        MyViewHolder recycleViewHolder = new MyViewHolder(view, mlistener);
        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Empresa itemAtual = listaEmpresa.get(position);

        holder.nome_fantasia.setText(" " + itemAtual.getNomeFantasia());
        holder.razao_social.setText(" " + itemAtual.getRazaoSocial());
        holder.cnpj.setText(" " + itemAtual.getCnpj());
        holder.telefone.setText(" " + itemAtual.getTel());

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
        if (listaEmpresa != null) return listaEmpresa.size();
        return 0;
    }
}