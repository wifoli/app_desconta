package com.app_desconta.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.app_desconta.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalhesCompraFragment extends Fragment {

    FrameLayout fl ;
    public DetalhesCompraFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhes_compra, container, false);
        return view;
    }



}
