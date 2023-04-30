package com.example.myapplication.model;

import com.example.myapplication.model.enums.RequestStatus;
import com.example.myapplication.model.enums.VolleyballPosition;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Membership extends RealmObject {

    @PrimaryKey
    private Long id;
    private Player player;

    private Team team;

    private Integer number;

    private String position = VolleyballPosition.SEVERAL.getPosition();

    private boolean isCaptain;

    public Membership(){}

    public Membership(Long id, Player player, Team team, Integer number, VolleyballPosition position, boolean isCaptain) {
        this.id = id;
        this.player = player;
        this.team = team;
        this.number = number;
        this.position = position.getPosition();
        this.isCaptain = isCaptain;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public VolleyballPosition getPosition() {
        VolleyballPosition position = null;
        try {
            position = VolleyballPosition.valueOf(this.position);
        }catch (Exception e){
            position = VolleyballPosition.SEVERAL;
        }
        return position;
    }

    public void setPosition(VolleyballPosition position) {
        this.position = position.getPosition();
    }

    public boolean isCaptain() {
        return isCaptain;
    }

    public void setCaptain(boolean captain) {
        isCaptain = captain;
    }
}
