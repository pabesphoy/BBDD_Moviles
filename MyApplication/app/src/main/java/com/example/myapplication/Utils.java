package com.example.myapplication;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.model.AppUser;

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

    public static void sendBubbleMessage(AppCompatActivity context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
