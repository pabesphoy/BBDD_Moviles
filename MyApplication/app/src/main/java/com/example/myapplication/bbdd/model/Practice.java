package com.example.myapplication.bbdd.model;

import java.time.LocalDateTime;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Practice extends RealmObject {

    @PrimaryKey
    Long id;
    String place;

    String date;

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

    @Override
    public String toString() {
        return "Practice{" +
                "place='" + place + '\'' +
                ", date=" + date +
                '}';
    }
}
