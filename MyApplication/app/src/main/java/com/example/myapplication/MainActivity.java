package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.model.AppUser;
import com.example.myapplication.model.Coach;
import com.example.myapplication.model.Player;
import com.example.myapplication.model.Team;
import com.example.myapplication.repositories.PlayerRepository;
import com.example.myapplication.repositories.TeamRepository;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.mongodb.*;

public class MainActivity extends AppCompatActivity {
    public String appId = "nishida-hsltg";
    public static App app;
    private PlayerRepository playerRepository = new PlayerRepository();
    private TeamRepository teamRepository = new TeamRepository();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);

        app = new App(new AppConfiguration.Builder(appId)
                .appName("Nishida")
                .requestTimeout(30, TimeUnit.SECONDS)
                .build());
        Realm con = Utils.getRealm();
        Utils.sendBubbleMessage(this, ""+ con.where(AppUser.class).count());


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
                    Utils.sendBubbleMessage(MainActivity.this, e.toString());
                }
            }
        });


    }
    public void login(){
        TextView email = findViewById(R.id.email);
        TextView password = findViewById(R.id.password);
        Credentials emailPasswordCredentials = Credentials.emailPassword(email.getText().toString(), password.getText().toString());
        app.loginAsync(emailPasswordCredentials, new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                if (result.isSuccess()){
                    redirectToHome();
                }else{
                    Utils.sendBubbleMessage(MainActivity.this, "Login failed: " + result.getError());
                }
        }
    });
    }

    private void redirectToRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void redirectToHome() {
        Player player = playerRepository.getByPrimaryKey(app.currentUser().getProfile().getEmail());
        Utils.sendBubbleMessage(this, Utils.userToAppUser(app.currentUser()).getName()); //TODO: Debe redirigir a pagina principal.
    }


}