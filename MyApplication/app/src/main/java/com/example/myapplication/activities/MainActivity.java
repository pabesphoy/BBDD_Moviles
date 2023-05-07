package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.model.AppUser;
import com.example.myapplication.model.Membership;
import com.example.myapplication.model.enums.VolleyballPosition;
import com.example.myapplication.repositories.PlayerRepository;
import com.example.myapplication.services.MembershipService;
import com.example.myapplication.services.TeamService;

import io.realm.Realm;
import io.realm.mongodb.*;

public class MainActivity extends AppCompatActivity {
    public static App app;
    private PlayerRepository playerRepository = new PlayerRepository();
    private TeamService teamService = new TeamService();
    private MembershipService membershipService = new MembershipService();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);

        app = Utils.app;
        Realm con = Utils.getRealm();

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
                    Utils.sendBubbleMessage(MainActivity.this, "Login failed: " + result.getError().getMessage());
                }
        }
    });
    }

    private void redirectToRegister() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private void redirectToHome() {
        try {
            startActivity(new Intent(this, HomeActivity.class));
        }catch (Exception e){
            Utils.sendBubbleMessage(this,e.getMessage().toString());
        }
    }
}