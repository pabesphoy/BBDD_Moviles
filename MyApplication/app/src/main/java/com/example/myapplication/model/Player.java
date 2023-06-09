package com.example.myapplication.model;

import com.example.myapplication.Utils;
import com.example.myapplication.model.enums.VolleyballPosition;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Player extends RealmObject {

    @PrimaryKey
    private Long id;
    private AppUser user;
    private String preferredPosition;

    @LinkingObjects("player")
    private final RealmResults<JoinRequest> joinRequests = null;

    @LinkingObjects("player")
    private final RealmResults<Membership> memberships = null;



    public Player(){}
    public Player(AppUser user, String preferredPosition) {
        Number previousId = Utils.getRealm().where(Player.class).max("id");
        this.id = (previousId == null) ? 1 : previousId.longValue()+1;
        this.preferredPosition = preferredPosition;
        this.user = user;
    }


    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public AppUser getUser() {return user;}

    public void setUser(AppUser user) {this.user = user;}

    public String getPreferredPosition(){
        return preferredPosition;}

    public void setPreferredPosition(String preferredPosition) {this.preferredPosition = preferredPosition;}





}
