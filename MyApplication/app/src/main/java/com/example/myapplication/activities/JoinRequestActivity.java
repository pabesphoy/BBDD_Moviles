package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.model.Coach;
import com.example.myapplication.model.JoinRequest;
import com.example.myapplication.model.Player;
import com.example.myapplication.model.Team;
import com.example.myapplication.services.JoinRequestService;
import com.example.myapplication.services.TeamService;

import java.util.ArrayList;
import java.util.List;

public class JoinRequestActivity extends AppCompatActivity {


    TeamService teamService = new TeamService();

    JoinRequestService joinRequestService = new JoinRequestService();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_request);
        LinearLayout layoutRequests = findViewById(R.id.layoutRequests);

        List<JoinRequest> joinRequests = new ArrayList<>();
        Team team = teamService.getById(getIntent().getLongExtra("id", 1));
        joinRequests = joinRequestService.getPendingRequestsByTeam(team);
        for(JoinRequest request : joinRequests){
            Button button = new Button(JoinRequestActivity.this);
            button.setTag("btnJoinRequestId=" + request.getId());
            button.setText(request.getPlayer().getUser().getName() + " , " + request.getPlayer().getUser().getSurname());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(JoinRequestActivity.this, AcceptOrDenyActivity.class);
                    intent.putExtra("id", request.getId());
                    startActivity(intent);
                }
            });
            layoutRequests.addView(button);
        }

    }
}