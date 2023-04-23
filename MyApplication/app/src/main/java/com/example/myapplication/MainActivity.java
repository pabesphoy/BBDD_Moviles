package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Credentials;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.bbdd.BaseDatos;
import com.example.myapplication.bbdd.model.User;

import io.realm.Realm;
import io.realm.mongodb.*;

public class MainActivity extends AppCompatActivity {
    private Realm con;
    private String appId = "nishida-hsltg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Realm.init(this);
            App app = new App(new AppConfiguration.Builder(appId).build());

            createAdminUser();



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
                    redirectToRegister();
                }
            });

        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }



    private void createAdminUser(){
        con = BaseDatos.getInstance().conectar(getBaseContext());
        if(con.where(User.class).count() == 0){ //Si no hay ningún usuario crea un usuario admin.
            User user = new User("admin@admin.com", "admin");
            con.beginTransaction();
            con.copyToRealmOrUpdate(user);
            con.commitTransaction();
        }
    }
    public void login(){
        TextView email = findViewById(R.id.email);
        TextView password = findViewById(R.id.password);


        /*
            Credentials credentials = Credentials.anonymous();
            app.loginAsync(credentials, result -> {
                if (result.isSuccess()) {
                    Log.v("QUICKSTART", "Successfully authenticated anonymously.");
                    User user = app.currentUser();
                    String partitionValue = "My Project";
                    SyncConfiguration config = new SyncConfiguration.Builder(
                            user,
                            partitionValue)
                        .build();
                    uiThreadRealm = Realm.getInstance(config);
                    addChangeListenerToRealm(uiThreadRealm);
                    FutureTask<String> task = new FutureTask(new BackgroundQuickStart(app.currentUser()), "test");
                    ExecutorService executorService = Executors.newFixedThreadPool(2);
                    executorService.execute(task);
                } else {
                    Log.e("QUICKSTART", "Failed to log in. Error: " + result.getError());
                }
            });
             */

        try {
            User user = con.where(User.class).equalTo("email", email.getText().toString()).findFirst();
            if (user == null){
                Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_LONG).show();
            }else{
                if(user.getPassword().equals(password.getText().toString())){
                    Intent i = new Intent(this, HomeActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void redirectToRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}