package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.model.AppUser;
import com.example.myapplication.model.Player;
import com.example.myapplication.model.enums.VolleyballPosition;
import com.example.myapplication.repositories.AppUserRepository;
import com.example.myapplication.services.PlayerService;

public class EditProfileActivity extends AppCompatActivity {

    PlayerService playerService = new PlayerService();
    AppUserRepository appUserRepository = new AppUserRepository();
    AppUser currentUser;
    Player currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Utils.setFooterOnClickListeners(this);
        currentUser = Utils.getCurrentAppUser();


        EditText nameEdit = ((EditText) findViewById(R.id.nameEdit));
        EditText surnameEdit = ((EditText) findViewById(R.id.surnameEdit));
        DatePicker birthday = ((DatePicker) findViewById(R.id.birthdayEdit));
        Spinner preferredPositionEdit = (Spinner) findViewById(R.id.preferredPositionEdit);
        Button btnEditProfileSubmit = (Button) findViewById(R.id.btnEditProfileSubmit);

        nameEdit.setText(currentUser.getName(), TextView.BufferType.EDITABLE);
        surnameEdit.setText(currentUser.getSurname(), TextView.BufferType.EDITABLE);
        String[] splittedBirthday = currentUser.getBirthday().split("/");
        birthday.updateDate(Integer.parseInt(splittedBirthday[2]), Integer.parseInt(splittedBirthday[1]),Integer.parseInt(splittedBirthday[0]));

        if (Utils.isCurrentUserPlayer()){
            currentPlayer = (Player) Utils.userToPlayerOrCoach(currentUser);
            for(int index = 0 ; index<VolleyballPosition.values().length ;index++){
                if(VolleyballPosition.values()[index].getPosition().equals(currentPlayer.getPreferredPosition())){
                    preferredPositionEdit.setSelection(index);
                    break;
                }
            }
        }

        else{
            preferredPositionEdit.setVisibility(View.GONE);
        }

        btnEditProfileSubmit.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

            appUserRepository.edit(nameEdit.getText().toString(), surnameEdit.getText().toString(),
                    birthday.getDayOfMonth() + "/" + birthday.getMonth() + "/" + birthday.getYear(), currentUser);

            if(Utils.isCurrentUserPlayer()){
                playerService.editPosition(currentPlayer, ((Spinner)findViewById(R.id.preferredPositionEdit)).getSelectedItem().toString());
            }
            startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
        }
        });
    }
}