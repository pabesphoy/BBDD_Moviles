package com.example.myapplication;

import com.example.myapplication.bbdd.model.AppUser;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.mongodb.App;
import io.realm.mongodb.User;

public class Utils {

    App app = MainActivity.app;

    public static Realm getRealm(){
        RealmConfiguration config = new RealmConfiguration.Builder().name("Nishida").build();
        Realm con = Realm.getInstance(config);
        return con;
    }

    public static AppUser userToAppUser(User user){
        Realm con = getRealm();
        return con.where(AppUser.class).equalTo("email", user.getProfile().getEmail()).findFirst();
    }
}
