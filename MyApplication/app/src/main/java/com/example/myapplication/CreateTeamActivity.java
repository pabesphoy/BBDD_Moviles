package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.bbdd.model.Team;

import io.realm.Realm;

public class CreateTeamActivity extends AppCompatActivity {

    private EditText teamNameEdt, imageUrlEdt, cityEdt;
    private Realm realm;

    private String teamName, imageUrl, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_team);

        realm = Realm.getDefaultInstance();
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
                }else if (TextUtils.isEmpty(imageUrl)) {
                    imageUrlEdt.setError("Please enter a valid image url");
                }else if (TextUtils.isEmpty(city)) {
                    cityEdt.setError("Please enter Course Duration");
                }else {
                    // calling method to add data to Realm database..
                    addDataToDatabase(teamName, imageUrl, city);
                    Toast.makeText(CreateTeamActivity.this, "Team added to database..", Toast.LENGTH_SHORT).show();
                    teamNameEdt.setText("");
                    imageUrlEdt.setText("");
                    cityEdt.setText("");
                }
            }
        });
    }

    private void addDataToDatabase(String teamName, String imageUrl, String city) {

        Team team = new Team();

        // on below line we are getting id for the team which we are storing.
        Number id = realm.where(Team.class).max("id");

        long nextId;

        // validating if id is null or not.
        if (id == null) {
            // if id is null
            // we are passing it as 1.
            nextId = 1;
        } else {
            // if id is not null then
            // we are incrementing it by 1
            nextId = id.intValue() + 1;
        }

        team.setId(nextId);
        team.setName(teamName);
        team.setImage(imageUrl);
        team.setCity(city);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(team);
            }
        });
    }

}
