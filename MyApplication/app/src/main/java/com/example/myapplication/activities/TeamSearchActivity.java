package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.model.Coach;
import com.example.myapplication.model.Team;
import com.example.myapplication.services.TeamService;

import java.util.List;
import java.util.Random;

public class TeamSearchActivity extends AppCompatActivity {

    public TeamService teamService = new TeamService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_search);
        Utils.setFooterOnClickListeners(this);
        LinearLayout layoutTeamsResult = findViewById(R.id.layoutTeamsResult);
        Button btnInputSearch = findViewById(R.id.btnInputSearch);
        btnInputSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutTeamsResult.removeAllViews();
                String query = ((EditText)findViewById(R.id.inputTeam)).getText().toString();
                List<Team> result = teamService.searchByName(query);
                for(Team team : result){
                    Button button = new Button(TeamSearchActivity.this);
                    button.setTag("btnTeamDetailsId=" + team.getId());
                    button.setText(team.getName() + " - " + team.getCoach().getUser().getName() + " " + team.getCoach().getUser().getSurname());
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Utils.sendBubbleMessage(TeamSearchActivity.this, button.getTag().toString());
                            //TODO: Ir a details de team
                        }
                    });
                    layoutTeamsResult.addView(button);
                }
            }
        });



    }
}