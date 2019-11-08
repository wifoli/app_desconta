package com.app_desconta.util;

import android.util.Log;

import com.app_desconta.Usuario;
import com.google.firebase.auth.FirebaseAuth;

public class Sair {

    public static void sair(){
        FirebaseAuth.getInstance().signOut();
        if (Usuario.getInsance().getGoogleSignInClient() != null) {
            Log.d("test", "entrei");

            Usuario.getInsance().getGoogleSignInClient().signOut();
            Usuario.getInsance().setUsuario(null);
        }
    }
}
