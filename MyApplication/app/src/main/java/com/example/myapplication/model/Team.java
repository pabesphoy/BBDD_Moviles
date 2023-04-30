package com.example.myapplication.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Team extends RealmObject {

    @PrimaryKey
    private String name;

    private String image;

    private String city;

    private Coach coach;

    @LinkingObjects("team")
    private final RealmResults<Practice> practices = null;

    @LinkingObjects("team")
    private final RealmResults<JoinRequest> joinRequests = null;

    @LinkingObjects("team")
    private final RealmResults<Membership> memberships = null;

    public Team(){
    }
    public Team(String name, String image, String city, Coach coach){
        this.name = name;
        this.image = image;
        this.city = city;
        this.coach = coach;
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

    public Coach getCoach(){return coach;}

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCoach(Coach coach){this.coach = coach;}



    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
