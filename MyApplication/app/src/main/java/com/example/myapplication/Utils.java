package com.example.myapplication;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.activities.MainActivity;
import com.example.myapplication.activities.MyTeamsActivity;
import com.example.myapplication.activities.ProfileActivity;
import com.example.myapplication.activities.TeamSearchActivity;
import com.example.myapplication.model.AppUser;
import com.example.myapplication.model.Coach;
import com.example.myapplication.model.Player;
import com.example.myapplication.activities.PracticesActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.mongodb.App;
import io.realm.mongodb.User;

public class Utils {

    static App app = MainActivity.app;

    public static Realm getRealm(){
        RealmConfiguration config = new RealmConfiguration.Builder().name("Nishida").deleteRealmIfMigrationNeeded().build();
        return Realm.getInstance(config);
    }

    public static AppUser getCurrentAppUser(){
        return userToAppUser(app.currentUser());
    }

    public static AppUser userToAppUser(User user){
        Realm con = getRealm();
        return con.where(AppUser.class).equalTo("email", user.getProfile().getEmail()).findFirst();
    }

    public static RealmObject userToPlayerOrCoach(AppUser user){
        Realm con = getRealm();
        Player player = con.where(Player.class).equalTo("user.email",user.getEmail()).findFirst();
        return (player != null) ? player : con.where(Coach.class).equalTo("user.email",user.getEmail()).findFirst();
    }

    public static boolean isCurrentUserCoach(){
        return getRealm().where(Coach.class).equalTo("user.email", Utils.getCurrentAppUser().getEmail()).findFirst() != null;
    }

    public static boolean isCurrentUserPlayer(){
        return getRealm().where(Player.class).equalTo("user.email", Utils.getCurrentAppUser().getEmail()).findFirst() != null;
    }

    public static void sendBubbleMessage(AppCompatActivity context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void setFooterOnClickListeners(AppCompatActivity activity){
        Button btnProfile = activity.findViewById(R.id.btnProfile);
        Button btnMyTeams = activity.findViewById(R.id.btnMyTeams);
        Button btnSearch = activity.findViewById(R.id.btnSearch);
        Button btnPractices = activity.findViewById(R.id.btnPractices);
        btnProfile.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
            activity.startActivity(new Intent(activity, ProfileActivity.class));
            }});
        btnMyTeams.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
            activity.startActivity(new Intent(activity, MyTeamsActivity.class));
        }});
        btnSearch.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
            activity.startActivity(new Intent(activity, TeamSearchActivity.class));
        }});
        btnPractices.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
            activity.startActivity(new Intent(activity, PracticesActivity.class));
        }});
    }
}
