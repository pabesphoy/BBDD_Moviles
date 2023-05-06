package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.model.AppUser;
import com.example.myapplication.model.Coach;
import com.example.myapplication.model.Player;
import com.example.myapplication.model.enums.VolleyballPosition;
import com.example.myapplication.repositories.CoachRepository;
import com.example.myapplication.repositories.PlayerRepository;

import io.realm.Realm;

public class RegisterActivity extends AppCompatActivity {

    private Realm con;
    private PlayerRepository playerRepository = new PlayerRepository();
    private CoachRepository coachRepository = new CoachRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        try {
            con = Utils.getRealm();
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG);
        }


        //BOTÓN REGISTER
        Button btnRegister = findViewById(R.id.btnRegister);
        ToggleButton isPlayer = findViewById(R.id.isPlayer);
        Spinner preferredPosition = findViewById(R.id.preferredPosition);
        preferredPosition.setEnabled(isPlayer.isChecked());
        isPlayer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferredPosition.setEnabled(isChecked);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    private void register() {
        String name = ((TextView)findViewById(R.id.name)).getText().toString();
        String surname = ((TextView)findViewById(R.id.surname)).getText().toString();
        String email = ((TextView)findViewById(R.id.email)).getText().toString();
        String password = ((TextView)findViewById(R.id.password)).getText().toString();
        String passwordConfirm = ((TextView)findViewById(R.id.passwordConfirm)).getText().toString();
        String preferredPosition = ((Spinner)findViewById(R.id.preferredPosition)).getSelectedItem().toString();

        DatePicker birthday = findViewById(R.id.birthday);
        String strBirthday = birthday.getDayOfMonth() + "/" + (birthday.getMonth() +1) + "/" + birthday.getYear();

        boolean isPlayer = ((ToggleButton)findViewById(R.id.isPlayer)).isChecked();

        if(name.isBlank() || surname.isBlank() || email.isBlank() || password.isBlank() || passwordConfirm.isBlank()){
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }else if(con.where(AppUser.class).equalTo("email", email).count() > 0){
            Toast.makeText(this, "Este usuario ya está registrado", Toast.LENGTH_SHORT).show();
        }else if(!password.equals(passwordConfirm)){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }else {
            AppUser user = new AppUser(email, name, surname, strBirthday);
            MainActivity.app.getEmailPassword().registerUserAsync(email, password, res -> {
                try {
                    if(res.isSuccess()){
                        con.beginTransaction();
                        con.copyToRealmOrUpdate(user);
                        con.commitTransaction();
                        if(isPlayer)
                            playerRepository.insertOrUpdate(new Player(user, preferredPosition));
                        else
                            coachRepository.insertOrUpdate(new Coach(user));

                        this.finish();
                        startActivity(new Intent(this, MainActivity.class));
                    }else{
                        Toast.makeText(this, "Error al registrar el usuario: " + res.getError(), Toast.LENGTH_SHORT).show();
                        this.finish();
                    }

                }catch (Exception e){
                    Log.e("ERROR", e.toString());
                    Utils.sendBubbleMessage(this, e.getMessage());
                }

            });
        }

    }


}