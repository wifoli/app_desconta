package com.app_desconta;

import com.app_desconta.api.User;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class Usuario {

    public static Usuario instance;
    private User usuario;
    private String uid;
    private String email;
    private GoogleSignInClient googleSignInClient;

    public static Usuario getInsance() {
        if (Usuario.instance == null)  Usuario.instance = new Usuario();
        return Usuario.instance;
    }

    public void setUsuario(User usuario){
        this.usuario = usuario;
    }


    public User getUsuario(){
        return usuario;
    }

    public void setarUid(String uid){
        this.uid = uid;
    }

    public String getUid(){
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GoogleSignInClient getGoogleSignInClient() {
        return googleSignInClient;
    }

    public void setGoogleSignInClient(GoogleSignInClient googleSignInClient) {
        this.googleSignInClient = googleSignInClient;
    }
}