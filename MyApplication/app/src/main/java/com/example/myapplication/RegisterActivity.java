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

import org.w3c.dom.Text;

import io.realm.Realm;

public class RegisterActivity extends AppCompatActivity {

    private Realm con = BaseDatos.getInstance().conectar(getBaseContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //BOTÓN REGISTER
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        String email = ((TextView)findViewById(R.id.email)).getText().toString();
        String password = ((TextView)findViewById(R.id.password)).getText().toString();
        String passwordConfirm = ((TextView)findViewById(R.id.passwordConfirm)).getText().toString();

        if(email.length() == 0 || password.length() == 0 || passwordConfirm.length() == 0){
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }else if(con.where(User.class).equalTo("email", email).count() > 0){
            Toast.makeText(this, "Este usuario ya está registrado", Toast.LENGTH_SHORT).show();
        }else if(!password.equals(passwordConfirm)){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }else{
            User user = new User(email, password);
            con.beginTransaction();
            con.copyToRealmOrUpdate(user);
            con.commitTransaction();
            startActivity(new Intent(this, MainActivity.class));
        }
    }


}