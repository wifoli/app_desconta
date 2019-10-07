package com.app_desconta;

import android.util.Log;

import com.app_desconta.api.User;

public class Usuario {

    public static Usuario instance;
    private User usuario;
    private String uid;


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

    public void teste(){
        Log.d("Test - Email", usuario.getEmail());
        Log.d("Test - Nomme", usuario.getPessoa().getNome());
        Log.d("Test - CPF", usuario.getPessoa().getRg());
    }
}