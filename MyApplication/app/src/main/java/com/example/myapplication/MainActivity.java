package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.bbdd.model.AppUser;

import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.mongodb.*;

public class MainActivity extends AppCompatActivity {
    public String appId = "nishida-hsltg";
    public static App app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        app = new App(new AppConfiguration.Builder(appId).build());
        Realm con = Utils.getRealm();
        sendBubbleMessage(""+ con.where(AppUser.class).count());
        //BOTÓN LOGIN
        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        //BOTÓN REGISTER
        Button btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    redirectToRegister();
                }catch (Exception e){
                    sendBubbleMessage(e.toString());
                }
            }
        });


    }
    public void login(){
        TextView email = findViewById(R.id.email);
        TextView password = findViewById(R.id.password);
        Credentials emailPasswordCredentials = Credentials.emailPassword(email.getText().toString(), password.getText().toString());
        AtomicReference<User> user = new AtomicReference<User>();
        app.loginAsync(emailPasswordCredentials, new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                if (result.isSuccess()){
                    redirectToHome();
                }else{
                    sendBubbleMessage("Login failed: " + result.getError());
                }
        }
    });
    }

    private void redirectToRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void redirectToHome() {
        sendBubbleMessage(Utils.userToAppUser(app.currentUser()).getName()); //Debe redirigir a pagina principal.
    }

    private void sendBubbleMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}