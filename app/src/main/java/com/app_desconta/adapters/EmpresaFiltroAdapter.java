package com.app_desconta.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app_desconta.R;
import com.app_desconta.api.Api;
import com.app_desconta.api.Cidade;
import com.app_desconta.api.Empresa;
import com.app_desconta.util.RetrofitCliente;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmpresaFiltroAdapter extends RecyclerView.Adapter<EmpresaFiltroAdapter.MyViewHolder> {

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
        public TextView porcentagemDesconto;
        public TextView telefone;
        public TextView rua;
        public TextView bairro;
        public TextView numero;
        public TextView cep;
        public TextView cepcomplemento;
        public TextView cidade;


        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            razao_social = itemView.findViewById(R.id.tv_razao_empresa);
            nome_fantasia = itemView.findViewById(R.id.tv_fantasia_empresa);
            cnpj = itemView.findViewById(R.id.tv_cnpj_empresa);
            porcentagemDesconto = itemView.findViewById(R.id.tv_porcentagem_cashback_empresa);
            telefone = itemView.findViewById(R.id.tv_telefone_empresa);
            rua = itemView.findViewById(R.id.tv_rua_empresa);
            bairro = itemView.findViewById(R.id.tv_bairro_empresa);
            numero = itemView.findViewById(R.id.tv_numero_empresa);
            cep = itemView.findViewById(R.id.tv_cep_empresa);
            cepcomplemento = itemView.findViewById(R.id.tv_complemento_empresa);
            cidade = itemView.findViewById(R.id.tv_cidade_empresa);

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

    public EmpresaFiltroAdapter(Context context, ArrayList<Empresa> listaEmpresa) {
        this.listaEmpresa = listaEmpresa;
        this.context = context;
        mlaLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mlaLayoutInflater.inflate(R.layout.item_pesquisa_empresa, parent, false);
        MyViewHolder recycleViewHolder = new MyViewHolder(view, mlistener);
        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Empresa itemAtual = listaEmpresa.get(position);

        holder.razao_social.setText(" " + itemAtual.getRazaoSocial());
        holder.nome_fantasia.setText(" " + itemAtual.getNomeFantasia());
        holder.cnpj.setText(" " + itemAtual.getCnpj());
        holder.porcentagemDesconto.setText(" " + itemAtual.getPorcentagemDesc());
        holder.telefone.setText(" " + itemAtual.getTel());
        holder.rua.setText(" " + itemAtual.getRua());
        holder.bairro.setText(" " + itemAtual.getBairro());
        holder.numero.setText(" " + itemAtual.getNumero());
        holder.cep.setText(" " + itemAtual.getCep());
        holder.cepcomplemento.setText(" " + itemAtual.getComplemento());
       preencherDados(itemAtual, holder);


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

    private void preencherDados(final Empresa empresa, final MyViewHolder holder) {

        Api httpRequest = RetrofitCliente.getCliente().create(Api.class);

        Call<Cidade> call = httpRequest.getCidade(""+ empresa.getCidadeId());
        call.enqueue(new Callback<Cidade>() {
            @Override
            public void onResponse(Call<Cidade> call, Response<Cidade> response) {
                holder.cidade.setText(response.body().getNome());
            }

            @Override
            public void onFailure(Call<Cidade> call, Throwable t) {
                Log.e("Retrofit get_cidade", "Falha no Retrofit: " + t.toString());
            }
        });
    }
}