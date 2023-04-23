package com.example.myapplication.bbdd.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Team extends RealmObject {

    @PrimaryKey
    private String name;

    private String image;

    private String city;

    public Team(){
    }
    public Team(String name, String image, String city){
        this.name = name;
        this.image = image;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getCity() {
        return city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
