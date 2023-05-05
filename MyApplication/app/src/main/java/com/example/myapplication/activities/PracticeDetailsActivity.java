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
import com.example.myapplication.Utils;
import com.example.myapplication.model.Practice;
import com.example.myapplication.repositories.PracticeRepository;
import com.example.myapplication.services.PracticeService;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PracticeDetailsActivity extends AppCompatActivity {


    Practice currentPractice = new Practice();

    PracticeService practiceService = new PracticeService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_details);
        Long practiceId = getIntent().getLongExtra("id",1);
        currentPractice = practiceService.getById(practiceId);

        TextView teamEdt = ((TextView) findViewById(R.id.teamEdt));
        EditText placeEdt = ((EditText) findViewById(R.id.placeEdt));
        EditText dateEdt = ((EditText) findViewById(R.id.dateEdt));
        Button btnPracticeEdt = (Button) findViewById(R.id.btnPracticeEdt);

        teamEdt.setText(currentPractice.getTeam().getName(), TextView.BufferType.NORMAL);
        placeEdt.setText(currentPractice.getPlace(), TextView.BufferType.EDITABLE);
        dateEdt.setText(new SimpleDateFormat("dd/MM/yyyy").format(currentPractice.getDate()), TextView.BufferType.EDITABLE);

        if(Utils.isCurrentUserPlayer()) {
            btnPracticeEdt.setVisibility(View.GONE);
        }else{
            btnPracticeEdt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        practiceService.edit(placeEdt.getText().toString(),new SimpleDateFormat("dd/MM/yyyy").parse(dateEdt.getText().toString()),currentPractice);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    startActivity(new Intent(PracticeDetailsActivity.this,PracticesActivity.class));
                }
            });
        }
    }
}