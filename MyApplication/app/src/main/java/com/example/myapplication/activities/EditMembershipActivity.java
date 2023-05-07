package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.model.Membership;
import com.example.myapplication.model.Player;
import com.example.myapplication.model.enums.VolleyballPosition;
import com.example.myapplication.services.MembershipService;

public class EditMembershipActivity extends AppCompatActivity {

    private Membership membership;
    private MembershipService membershipService = new MembershipService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_membership);
        Utils.setFooterOnClickListeners(this);
        membership = membershipService.getById(getIntent().getLongExtra("id", 0));

        EditText number = ((EditText)findViewById(R.id.inputNumber));
        Integer numberInt = membership.getNumber();
        number.setText(String.valueOf(numberInt));
        Switch isCaptain = (Switch) findViewById(R.id.isCaptain);

        Spinner preferredPositionEdit = (Spinner) findViewById(R.id.inputPosition);
        isCaptain.setChecked(membership.isCaptain());
        for(int index = 0; index< VolleyballPosition.values().length ; index++){
            if(VolleyballPosition.values()[index].getPosition().equals(membership.getPosition())){
                preferredPositionEdit.setSelection(index);
                break;
            }
        }


        Button btnEditMembership = (Button) findViewById(R.id.btnEditMembership);
        btnEditMembership.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
                membershipService.edit(membership, number.getText().toString(), preferredPositionEdit.getSelectedItem().toString(), isCaptain.isChecked());
                Utils.sendBubbleMessage(EditMembershipActivity.this, "Edit done");
                Intent intent = new Intent(EditMembershipActivity.this, TeamDetailsActivity.class);
                Long id = Long.valueOf((long)membership.getTeam().getId());
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        Button btnDeleteMembership = (Button) findViewById(R.id.btnDeleteMembership);
        btnDeleteMembership.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
            Intent intent = new Intent(EditMembershipActivity.this, TeamDetailsActivity.class);
            intent.putExtra("id", membership.getTeam().getId());
            membershipService.delete(membership);
            Utils.sendBubbleMessage(EditMembershipActivity.this, "Player removed from team");
            startActivity(intent);
        }
        });

    }
}