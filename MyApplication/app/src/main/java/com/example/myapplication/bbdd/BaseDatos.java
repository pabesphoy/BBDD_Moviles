package com.example.myapplication.bbdd;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class BaseDatos {

    private static BaseDatos instance = new BaseDatos();
    private Realm con;

    public static BaseDatos getInstance(){return instance;}

    public Realm conectar(Context context){
        if(con == null){
            Realm.init(context);
            String nombre = "Database";
            RealmConfiguration config = new RealmConfiguration.Builder().name(nombre).build();
            con = Realm.getInstance(config);
        }
        return con;


    }
}
