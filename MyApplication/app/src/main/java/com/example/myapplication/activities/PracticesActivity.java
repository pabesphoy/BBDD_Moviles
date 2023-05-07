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
import com.example.myapplication.model.Practice;
import com.example.myapplication.services.PracticeService;
import com.example.myapplication.services.TeamService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PracticesActivity extends AppCompatActivity {


    public PracticeService practiceService = new PracticeService();
    public TeamService teamService = new TeamService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_practices);
        Utils.setFooterOnClickListeners(this);

        LinearLayout layoutPracticesResult = findViewById(R.id.layoutPracticesResult);
        Button btnCreatePractice = findViewById(R.id.btnCreatePractice);
        btnCreatePractice.setActivated(Utils.isCurrentUserCoach());

        boolean isCoach = Utils.isCurrentUserCoach();
        btnCreatePractice.setVisibility(isCoach ? View.VISIBLE : View.GONE);

        btnCreatePractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PracticesActivity.this, CreatePracticeActivity.class));
            }
        });


        List<Practice> practices = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        if(Utils.isCurrentUserPlayer()){
            practices = practiceService.getByPlayer((Player) Utils.userToPlayerOrCoach(Utils.getCurrentAppUser()),true);
        } else if (Utils.isCurrentUserCoach()) {
            practices = practiceService.getByCoach((Coach) Utils.userToPlayerOrCoach(Utils.getCurrentAppUser()),true, null);
        }
        for(Practice practice : practices){
            Button button = new Button(PracticesActivity.this);
            button.setTag("btnPracticeDetailsId=" + practice.getId());
            button.setText(practice.getTeam().getName() + " - " + practice.getPlace() + " - " + formatter.format(practice.getDate()));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PracticesActivity.this, PracticeDetailsActivity.class);
                    intent.putExtra("id",practice.getId());
                    startActivity(intent);
                }
            });
            layoutPracticesResult.addView(button);
        }
    }
}