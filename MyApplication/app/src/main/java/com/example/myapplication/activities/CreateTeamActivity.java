package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.model.Coach;
import com.example.myapplication.model.Team;
import com.example.myapplication.services.TeamService;

import io.realm.Realm;

public class CreateTeamActivity extends AppCompatActivity {

    private EditText teamNameEdt, imageUrlEdt, cityEdt;
    private Realm realm;

    private String teamName, imageUrl, city;

    private TeamService teamService = new TeamService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_team);
        Utils.setFooterOnClickListeners(this);

        realm = Utils.getRealm();
        teamNameEdt = findViewById(R.id.idEdtTeamName);
        imageUrlEdt = findViewById(R.id.idEdtImageUrl);
        cityEdt = findViewById(R.id.idEdtCity);

        Button createTeamBtn = findViewById(R.id.idBtnCreateTeam);
        createTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                teamName = teamNameEdt.getText().toString();
                imageUrl = imageUrlEdt.getText().toString();
                city = cityEdt.getText().toString();

                if (TextUtils.isEmpty(teamName)) {
                    teamNameEdt.setError("Please enter Team Name");
                } else if (TextUtils.isEmpty(imageUrl)) {
                    imageUrlEdt.setError("Please enter a valid image url");
                } else if (TextUtils.isEmpty(city)) {
                    cityEdt.setError("Please enter Course Duration");
                } else {
                    // calling method to add data to Realm database..
                    teamService.insertOrUpdate(new Team(teamName, imageUrl, city, (Coach)Utils.userToPlayerOrCoach(Utils.getCurrentAppUser())));
                    Toast.makeText(CreateTeamActivity.this, "Team added to database..", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateTeamActivity.this, MyTeamsActivity.class));
                }
            }
        });
    }
}