package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.model.Coach;
import com.example.myapplication.model.Practice;
import com.example.myapplication.model.Team;
import com.example.myapplication.services.PracticeService;
import com.example.myapplication.services.TeamService;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import io.realm.Realm;

public class CreatePracticeActivity extends AppCompatActivity {

    private EditText teamEdt,  placeEdt;
    private DatePicker dateEdt;
    private Realm realm;

    private String team, date, place;

    private PracticeService practiceService = new PracticeService();
    private TeamService teamService = new TeamService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_practice);


        realm = Utils.getRealm();
        dateEdt = findViewById(R.id.idEdtDate);
        teamEdt = findViewById(R.id.idEdtTeam);
        placeEdt = findViewById(R.id.idEdtPlace);

        Button createPracticeBtn = findViewById(R.id.idBtnCreatePractice);
        createPracticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                team = teamEdt.getText().toString();
                date = "" + dateEdt.getDayOfMonth() + "/" + (dateEdt.getMonth()+1) + "/" + dateEdt.getYear();
                place = placeEdt.getText().toString();

                if (TextUtils.isEmpty(team) || teamService.getByName(team) == null) {
                    teamEdt.setError("Please enter the name of an existent team");
                } else if (TextUtils.isEmpty(date)) {
                    Utils.sendBubbleMessage(CreatePracticeActivity.this, "Invalid date");
                } else if (TextUtils.isEmpty(place)) {
                    placeEdt.setError("Please enter a place for the practice");
                } else {
                    // calling method to add data to Realm database..
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Practice p = null;
                    try {
                        p = new Practice(place, formatter.parse(date),teamService.getByName(team));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    practiceService.insertOrUpdate(p);
                    Toast.makeText(CreatePracticeActivity.this, "Practice added to database..", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreatePracticeActivity.this, PracticesActivity.class));
                }
            }
        });


    }
}