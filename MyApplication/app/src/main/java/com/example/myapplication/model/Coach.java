package com.example.myapplication.model;

import com.example.myapplication.Utils;

import java.security.SecureRandom;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Coach extends RealmObject {

    @PrimaryKey
    private Long id;
    private AppUser user;

    @LinkingObjects("coach")
    private final RealmResults<Team> teams = null;

    public Coach(AppUser user) {
        Number previousId = Utils.getRealm().where(Coach.class).max("id");
        this.id = (previousId == null) ? 1 : previousId.longValue()+1;
        this.user = user;}

    public Coach() {}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public AppUser getUser() {return user;}

    public void setUser(AppUser user) {this.user = user;}

    public RealmResults<Team> getTeams() {return teams;}
}
