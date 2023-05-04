package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.model.AppUser;
import com.example.myapplication.model.Player;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_activity);
        Utils.setFooterOnClickListeners(this);

        AppUser user = Utils.getCurrentAppUser();

        ((TextView) findViewById(R.id.txtName)).setText(user.getName());
        ((TextView) findViewById(R.id.txtSurname)).setText(user.getSurname());
        ((TextView) findViewById(R.id.txtEmail)).setText(user.getEmail());
        ((TextView) findViewById(R.id.txtBirthday)).setText(user.getBirthday());
        if(Utils.isCurrentUserPlayer()){
            Player player = (Player) Utils.userToPlayerOrCoach(user);
            String position = player.getPreferredPosition().getPosition();
            ((TextView) findViewById(R.id.txtPreferredPosition)).setText(player.getPreferredPosition().getPosition().toString());
        }else{
            ((TextView) findViewById(R.id.lblBirthday)).setVisibility(View.GONE);
        }


    }
}