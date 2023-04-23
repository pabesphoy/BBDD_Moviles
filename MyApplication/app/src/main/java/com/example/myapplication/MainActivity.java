package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.bbdd.BaseDatos;
import com.example.myapplication.bbdd.model.User;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private Realm con;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
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