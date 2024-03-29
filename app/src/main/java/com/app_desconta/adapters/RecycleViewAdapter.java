package com.app_desconta.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app_desconta.R;
import com.app_desconta.api.Compras;
import com.app_desconta.util.FormataData;
import com.app_desconta.util.FormataValorEmReal;
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
        public LinearLayout linear;

        public RecycleViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            empresa = itemView.findViewById(R.id.cv_nome_empresa);
            valor = itemView.findViewById(R.id.valor_compra);
            data = itemView.findViewById(R.id.cv_data_compra);
            linear = itemView.findViewById(R.id.linearLayout_card_compra);

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
    private Context context;

    public RecycleViewAdapter(Context context, ArrayList<Compras> listaCompras) {
        this.listaCompras = listaCompras;
        this.context = context;
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
        holder.valor.setText(" " + FormataValorEmReal.formataValorEmReal(itemAtual.getValorTotal()));
        holder.data.setText(" " + FormataData.formataData(itemAtual.getDataVenda()));

        if(itemAtual.getCompra_paga().equals("S")){
            holder.linear.setVisibility(LinearLayout.VISIBLE);
        }

        try {
            YoYo.with(Techniques.SlideInDown) // FadeInDown, ZoomInDown, BounceInDown
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