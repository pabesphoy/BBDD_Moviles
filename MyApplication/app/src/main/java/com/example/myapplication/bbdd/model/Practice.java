package com.example.myapplication.bbdd.model;

import java.time.LocalDateTime;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Practice extends RealmObject {

    @PrimaryKey
    Long id;
    String place;

    LocalDateTime date;

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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
