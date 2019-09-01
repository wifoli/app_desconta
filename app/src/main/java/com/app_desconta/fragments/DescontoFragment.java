package com.app_desconta.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.app_desconta.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DescontoFragment extends Fragment {


    public DescontoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_desconto, container, false);
    }

}
