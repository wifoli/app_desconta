package com.app_desconta.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class EmpresaAdapter extends RecyclerView.Adapter<EmpresaAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListenet(OnItemClickListener listenet) {
        mlistener = listenet;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView nome_fantasia;
        public TextView cnpj;
        public TextView telefone;
        public Button mais;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            nome_fantasia = itemView.findViewById(R.id.nome_fantasia);
            cnpj = itemView.findViewById(R.id.cnpj);
            telefone = itemView.findViewById(R.id.tel);
            mais = itemView.findViewById(R.id.mais);

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

    public EmpresaAdapter(Context context, ArrayList<Empresa> listaEmpresa) {
        this.listaEmpresa = listaEmpresa;
        this.context = context;
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
        final Empresa itemAtual = listaEmpresa.get(position);

        holder.nome_fantasia.setText(" " + itemAtual.getNomeFantasia());
        holder.cnpj.setText(" " + itemAtual.getCnpj());
        holder.telefone.setText(" " + itemAtual.getTel());

        holder.mais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preencherDados(itemAtual);
            }
        });

        try {
            YoYo.with(Techniques.FadeInDown) // FadeInDown, ZoomInDown, BounceInDown
                    .duration(680)
                    .playOn(holder.itemView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarMais(Empresa empresa, String nomeCidade) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(context.getString(R.string.sobreEmpresa))
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Razão Social: " + empresa.getRazaoSocial() + "\n" +
                        "Nome Fantasia: " + empresa.getNomeFantasia() + "\n" +
                        "Telefone: " + empresa.getTel() + "\n" +
                        "CNPJ: " + empresa.getCnpj() + "\n" +
                        "Cidade: " + nomeCidade + "\n" +
                        "Desconto: " + empresa.getPorcentagemDesc() + "%")
                .setNegativeButton("Sair", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alert.setCancelable(true);
                    }
                });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
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

    private void preencherDados(final Empresa empresa) {

        Api httpRequest = RetrofitCliente.getCliente().create(Api.class);

        Call<Cidade> call = httpRequest.getCidade(""+ empresa.getCidadeId());
        call.enqueue(new Callback<Cidade>() {
            @Override
            public void onResponse(Call<Cidade> call, Response<Cidade> response) {
                mostrarMais(empresa, response.body().getNome());
            }

            @Override
            public void onFailure(Call<Cidade> call, Throwable t) {
                Log.e("Retrofit get_cidade", "Falha no Retrofit: " + t.toString());
            }
        });
    }
}