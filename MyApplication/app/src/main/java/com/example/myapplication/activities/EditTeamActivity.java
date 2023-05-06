package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Team;
import com.example.myapplication.services.TeamService;

public class EditTeamActivity extends AppCompatActivity {


    TeamService teamService = new TeamService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);
        Team currentTeam = teamService.getById(getIntent().getLongExtra("id",1));

        EditText nameEdt = ((EditText) findViewById(R.id.nameEdit));
        EditText imageEdt = ((EditText) findViewById(R.id.idEdtImageUrl));
        Button btnEditTeamSubmit = (Button) findViewById(R.id.btnEdit);

        nameEdt.setText(currentTeam.getName().toString(), TextView.BufferType.EDITABLE);
        imageEdt.setText(currentTeam.getImage().toString(), TextView.BufferType.EDITABLE);

        btnEditTeamSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teamService.edit(nameEdt.getText().toString(),imageEdt.getText().toString(),currentTeam);
                startActivity(new Intent(EditTeamActivity.this, TeamDetailsActivity.class));

            }
        });

    }
}