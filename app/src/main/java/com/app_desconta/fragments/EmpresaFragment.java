package com.app_desconta.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app_desconta.R;
import com.app_desconta.api.Empresa;

import java.util.ArrayList;

public class EmpresaFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ArrayList<Empresa> listaEmpresa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_empresa, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list_empresa);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(llm);



        return view;
    }

}
