package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.model.Coach;
import com.example.myapplication.model.JoinRequest;
import com.example.myapplication.model.Membership;
import com.example.myapplication.model.Player;
import com.example.myapplication.model.Team;
import com.example.myapplication.services.JoinRequestService;
import com.example.myapplication.services.MembershipService;
import com.example.myapplication.services.TeamService;

public class TeamDetailsActivity extends AppCompatActivity {

    private Team team;

    private JoinRequest joinRequest;

    TeamService teamService = new TeamService();

    JoinRequestService joinRequestService = new JoinRequestService();
    MembershipService membershipService = new MembershipService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_team);
        Utils.setFooterOnClickListeners(this);
        team = teamService.getById(getIntent().getExtras().getLong("id"));

        ((TextView)findViewById(R.id.txtTeamName)).setText(team.getName());
        ((TextView)findViewById(R.id.txtTeamCity)).setText(team.getCity());
        //((ImageView)findViewById(R.id.imageView)).setImageURI(Uri.parse(team.getImage()));
        Button btnAddPlayer = (Button) findViewById(R.id.btnAddPlayer);
        Button btnEditTeam = (Button) findViewById(R.id.btnEditTeam);
        Button btnJoinRequest = (Button) findViewById(R.id.btnJoinRequest);

        if(Utils.isCurrentUserCoach()){
            btnJoinRequest.setActivated(false);
            btnJoinRequest.setVisibility(View.GONE);
            if(((Coach) Utils.userToPlayerOrCoach(Utils.getCurrentAppUser())).equals(team.getCoach())){
                btnAddPlayer.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
                    Intent intent = new Intent(TeamDetailsActivity.this, JoinRequestActivity.class);
                    intent.putExtra("id",team.getId());
                    startActivity(intent);
                }});
                btnEditTeam.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
                    Intent intent = new Intent(TeamDetailsActivity.this, EditTeamActivity.class);
                    intent.putExtra("id",team.getId());
                    startActivity(intent);
                }});
            }else{
                hideButtons();
            }
        }else{
            Player player = (Player) Utils.userToPlayerOrCoach(Utils.getCurrentAppUser());
            btnJoinRequest.setActivated(!joinRequestService.getHasPlayerPendingRequestForTeam(player, team)
                                        && !membershipService.getPlayerInTeam(player, team));
            btnJoinRequest.setVisibility(!joinRequestService.getHasPlayerPendingRequestForTeam((Player) Utils.userToPlayerOrCoach(Utils.getCurrentAppUser()), team) && !membershipService.getPlayerInTeam(player, team)
                                            ? View.VISIBLE : View.GONE);
            btnJoinRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    joinRequestService.requestJoinTeam((Player)(Utils.userToPlayerOrCoach(Utils.getCurrentAppUser())),team);
                    btnJoinRequest.setVisibility(View.GONE);
                    btnJoinRequest.setActivated(false);
                    Utils.sendBubbleMessage(TeamDetailsActivity.this, "¡Request enviada!");
                }
            });
            hideButtons();
        }


        LinearLayout linearLayoutPlayers = findViewById(R.id.linearLayoutPlayers);

        for(Membership membership : membershipService.getByTeam(team)){
            LinearLayout linearLayoutPlayer = new LinearLayout(this);

            TextView teamLine = new TextView(this);
            String isCaptain = membership.isCaptain() ? " - CAPITÁN" : "";
            teamLine.setText("#" + membership.getNumber() + ": " + membership.getPlayer().getUser().getName() + " " + membership.getPlayer().getUser().getSurname() + " - " + membership.getPosition() + isCaptain);
            linearLayoutPlayer.addView(teamLine);
            if(Utils.isCurrentUserCoach()){
                if(((Coach)Utils.userToPlayerOrCoach(Utils.getCurrentAppUser())).equals(team.getCoach())){
                    Button btnEdit = new Button(this);
                    btnEdit.setText("Edit");
                    btnEdit.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
                        Intent intent = new Intent(TeamDetailsActivity.this, EditMembershipActivity.class);
                        intent.putExtra("id", membership.getId());
                        startActivity(intent);
                    }
                    });
                    linearLayoutPlayer.addView(btnEdit);
                }
            }



            linearLayoutPlayers.addView(linearLayoutPlayer);
        }
    }

    private void hideButtons(){
        Button btnAddPlayer = (Button) findViewById(R.id.btnAddPlayer);
        Button btnEditTeam = (Button) findViewById(R.id.btnEditTeam);
        btnAddPlayer.setActivated(false);
        btnAddPlayer.setVisibility(View.GONE);
        btnEditTeam.setActivated(false);
        btnEditTeam.setVisibility(View.GONE);
    }
}