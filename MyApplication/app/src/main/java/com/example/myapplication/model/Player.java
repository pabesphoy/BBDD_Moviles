package com.example.myapplication.model;

import com.example.myapplication.model.enums.VolleyballPosition;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

public class Player extends RealmObject {

    @PrimaryKey
    private Long id;
    private AppUser user;
    private String preferredPosition = VolleyballPosition.SEVERAL.getPosition();

    @LinkingObjects("player")
    private final RealmResults<JoinRequest> joinRequests = null;

    @LinkingObjects("player")
    private final RealmResults<Membership> memberships = null;



    public Player(){}
    public Player(AppUser user, VolleyballPosition preferredPosition) {
        Long previousId = getRealm().where(Coach.class).max("id").longValue();
        this.id = (previousId == null) ? 1 : previousId+1;
        this.preferredPosition = preferredPosition.getPosition();
    }


    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public AppUser getUser() {return user;}

    public void setUser(AppUser user) {this.user = user;}

    public VolleyballPosition getPreferredPosition(){
        VolleyballPosition preferredPosition = null;
        try {
            preferredPosition = VolleyballPosition.valueOf(this.preferredPosition);
        }catch (Exception e){
            preferredPosition = VolleyballPosition.SEVERAL;
        }
        return preferredPosition;}

    public void setPreferredPosition(VolleyballPosition preferredPosition) {this.preferredPosition = preferredPosition.getPosition();}





}
