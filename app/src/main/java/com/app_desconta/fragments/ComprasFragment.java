package com.app_desconta.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app_desconta.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComprasFragment extends Fragment {


    public ComprasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compras, container, false);
    }

}
