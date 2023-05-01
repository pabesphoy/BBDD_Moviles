package com.example.myapplication.model;

import com.example.myapplication.Utils;

import java.time.LocalDateTime;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Practice extends RealmObject implements Comparable<Practice>{

    @PrimaryKey
    private Long id;
    private String place;
    private Date date;
    private Team team;

    public Practice() {}

    public Practice(String place, Date date, Team team) {
        Number previousId = Utils.getRealm().where(Practice.class).max("id");
        this.id = (previousId == null) ? 1 : previousId.longValue()+1;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    @Override
    public int compareTo(Practice o) {
        return this.date.compareTo(o.getDate());
    }
}
