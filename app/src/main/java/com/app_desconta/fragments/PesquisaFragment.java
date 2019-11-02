package com.app_desconta.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app_desconta.R;
import com.app_desconta.Usuario;
import com.app_desconta.login.Tela_login;
import com.google.firebase.auth.FirebaseAuth;


public class PesquisaFragment extends Fragment {

    private Button botao;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pesquisa, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        botao = (Button) view.findViewById(R.id.botaooo);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                if (Usuario.getInsance().getGoogleSignInClient() != null) {
                    Usuario.getInsance().getGoogleSignInClient().signOut();
                    Usuario.getInsance().setUsuario(null);
                }
                fecharApp();
            }
        });


    }

    private void fecharApp(){
        getActivity().finishAffinity();
        startActivity(new Intent(getActivity(), Tela_login.class));

    }
}
