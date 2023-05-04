package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.services.TeamService;

import io.realm.Realm;

public class CreatePracticeActivity extends AppCompatActivity {

    private EditText practiceNameEdt, dateEdt, placeEdt;
    private Realm realm;

    private String practiceName, date, place;

    private TeamService teamService = new TeamService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_practice);
    }
}