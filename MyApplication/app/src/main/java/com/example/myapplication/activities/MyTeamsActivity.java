package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.model.Coach;
import com.example.myapplication.model.Player;
import com.example.myapplication.model.Team;
import com.example.myapplication.services.TeamService;

import java.util.ArrayList;
import java.util.List;

public class MyTeamsActivity extends AppCompatActivity {

    public TeamService teamService = new TeamService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_teams);
        Utils.setFooterOnClickListeners(this);

        LinearLayout layoutTeamsResult = findViewById(R.id.layoutTeamsResult);
        Button btnCreateTeam = findViewById(R.id.btnCreateTeam);
        btnCreateTeam.setActivated(Utils.isCurrentUserCoach());

        btnCreateTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyTeamsActivity.this, CreateTeamActivity.class));
            }
        });


        List<Team> teams = new ArrayList<>();
        if(Utils.isCurrentUserPlayer()){
            teams = teamService.getByPlayerIsMember(((Player) Utils.userToPlayerOrCoach(Utils.getCurrentAppUser())));
        } else if (Utils.isCurrentUserCoach()) {
            teams = teamService.getByCoach(((Coach) Utils.userToPlayerOrCoach(Utils.getCurrentAppUser())));
        }
        for(Team team : teams){
            Button button = new Button(MyTeamsActivity.this);
            button.setTag("btnTeamDetailsId=" + team.getId());
            button.setText(team.getName());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.sendBubbleMessage(MyTeamsActivity.this, button.getTag().toString());
                    //TODO: Ir a details de team
                }
            });
            layoutTeamsResult.addView(button);
        }

    }
}