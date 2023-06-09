package com.example.myapplication.repositories;

import com.example.myapplication.model.JoinRequest;
import com.example.myapplication.model.Player;
import com.example.myapplication.model.Team;
import com.example.myapplication.model.enums.RequestStatus;

import java.util.Collection;
import java.util.List;

import io.realm.Realm;

public class JoinRequestRepository implements BaseRepository<JoinRequest, Long> {


    @Override
    public Collection<JoinRequest> getAll() {
        return realm.where(JoinRequest.class).findAll();
    }

    @Override
    public JoinRequest getByPrimaryKey(Long key) {
        return realm.where(JoinRequest.class).equalTo("id", key).findFirst();
    }

    public Collection<JoinRequest> getRequestsByPlayer(Player player) {
        return realm.where(JoinRequest.class).equalTo("player.id",player.getId()).findAll();
    }

    public Collection<JoinRequest> getRequestsByTeam(Team team) {
        return realm.where(JoinRequest.class).equalTo("team.id", team.getId()).findAll();
    }

    public Collection<JoinRequest> getPendingRequestsByTeam(Team team) {
        return realm.where(JoinRequest.class).equalTo("team.id", team.getId()).equalTo("status","Pending").findAll();
    }

    public boolean getHasPlayerPendingRequestForTeam(Player player, Team team) {
        return realm.where(JoinRequest.class).equalTo("team.id", team.getId())
                                                .equalTo("player.user.email", player.getUser().getEmail())
                                                .equalTo("status","Pending").count() > 0;
    }
    @Override
    public boolean insertOrUpdate(JoinRequest item) {
        realm.beginTransaction();
        realm.insertOrUpdate(item);
        realm.commitTransaction();
        return realm.where(JoinRequest.class).equalTo("id", item.getId()) != null;
    }

    @Override
    public boolean delete(JoinRequest item) {
        return deleteByPrimaryKey(item.getId());
    }

    @Override
    public boolean deleteByPrimaryKey(Long key) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                JoinRequest joinRequest = getByPrimaryKey(key);
                if(joinRequest != null){
                    joinRequest.deleteFromRealm();
                }
            }
        });
        return getByPrimaryKey(key) == null;
    }


    public void acceptJoinRequest(JoinRequest request) {
        realm.beginTransaction();
        request.setStatus(RequestStatus.ACCEPTED);
        realm.copyToRealmOrUpdate(request);
        realm.commitTransaction();
    }
    public void denyJoinRequest(JoinRequest request) {
        realm.beginTransaction();
        request.setStatus(RequestStatus.ACCEPTED);
        realm.copyToRealmOrUpdate(request);
        realm.commitTransaction();
    }

}
