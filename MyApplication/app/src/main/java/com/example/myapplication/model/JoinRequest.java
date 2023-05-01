package com.example.myapplication.model;

import com.example.myapplication.Utils;
import com.example.myapplication.model.enums.RequestStatus;

import java.util.Date;

import io.realm.RealmObject;

public class JoinRequest extends RealmObject {

    private Long id;

    private Player player;

    private Team team;

    private Date requestDate;

    private String status = RequestStatus.PENDING.getStatus();



    public JoinRequest() {}

    public JoinRequest(Player player, Team team, Date requestDate) {
        Number previousId = Utils.getRealm().where(JoinRequest.class).max("id");
        this.id = (previousId == null) ? 1 : previousId.longValue()+1;
        this.player = player;
        this.team = team;
        this.requestDate = requestDate;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

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

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public RequestStatus getStatus() {
        RequestStatus currentStatus = null;
        try {
            currentStatus = RequestStatus.valueOf(status);
        }catch (Exception e){
            currentStatus = RequestStatus.PENDING;
        }
        return currentStatus;
    }

    public void setStatus(RequestStatus status) {
        this.status = status.getStatus();
    }
}
