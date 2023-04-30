package com.example.myapplication.model;

import java.time.LocalDateTime;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Practice extends RealmObject {

    @PrimaryKey
    private Long id;
    private String place;
    private String date;
    private Team team;

    public Practice() {}

    public Practice(Long id, String place, String date, Team team) {
        this.id = id;
        this.place = place;
        this.date = date;
        this.team = team;
    }

    public String getPlace() {
        return place;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Team getTeam() {return team;}

    public void setTeam(Team team) {this.team = team;}

    @Override
    public String toString() {
        return "Practice{" +
                "place='" + place + '\'' +
                ", date=" + date +
                '}';
    }
}
