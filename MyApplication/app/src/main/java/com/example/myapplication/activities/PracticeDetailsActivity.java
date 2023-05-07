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
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.model.Practice;
import com.example.myapplication.repositories.PracticeRepository;
import com.example.myapplication.services.PracticeService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

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
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        dateEdt.setText(formatter.format(currentPractice.getDate()), TextView.BufferType.EDITABLE);

        if(Utils.isCurrentUserPlayer()) {
            btnPracticeEdt.setVisibility(View.GONE);
        }else{
            btnPracticeEdt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        if(formatter.parse(dateEdt.getText().toString()).after(new GregorianCalendar(1900, 1, 1).getTime())){
                            practiceService.edit(placeEdt.getText().toString(),formatter.parse(dateEdt.getText().toString()),currentPractice);
                            Toast.makeText(PracticeDetailsActivity.this, "Practice edited..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(PracticeDetailsActivity.this, PracticesActivity.class));
                        }else{
                            Toast.makeText(PracticeDetailsActivity.this, "Invalid date", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(PracticeDetailsActivity.this, "Invalid data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}