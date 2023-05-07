package com.example.myapplication.repositories;

import android.widget.Switch;

import com.example.myapplication.model.JoinRequest;
import com.example.myapplication.model.Membership;
import com.example.myapplication.model.Player;
import com.example.myapplication.model.Team;
import com.example.myapplication.model.enums.VolleyballPosition;

import java.util.Collection;

import io.realm.Realm;

public class MembershipRepository implements BaseRepository<Membership, Long> {
    @Override
    public Collection<Membership> getAll() {
        return realm.where(Membership.class).findAll();
    }

    @Override
    public Membership getByPrimaryKey(Long key) {
        return realm.where(Membership.class).equalTo("id", key).findFirst();
    }

    public Collection<Membership> getByPlayer(Player player){
        return realm.where(Membership.class).equalTo("player.id",player.getId()).findAll();
    }

    public Collection<Membership> getByTeam(Team team){
        return realm.where(Membership.class).equalTo("team.id",team.getId()).findAll();
    }

    public boolean getPlayerInTeam(Player player, Team team) {
        return realm.where(Membership.class).equalTo("player.id",player.getId())
                                    .equalTo("team.id", team.getId())
                                    .count() > 0;
    }

    @Override
    public boolean insertOrUpdate(Membership item) {
        realm.beginTransaction();
        realm.insertOrUpdate(item);
        realm.commitTransaction();
        return realm.where(Membership.class).equalTo("id", item.getId()) != null;
    }

    @Override
    public boolean delete(Membership item) {
        return deleteByPrimaryKey(item.getId());
    }

    @Override
    public boolean deleteByPrimaryKey(Long key) {
        realm.beginTransaction();
        Membership membership = getByPrimaryKey(key);
        if(membership != null){
            membership.deleteFromRealm();
        }
        realm.copyToRealmOrUpdate(membership);
        realm.commitTransaction();
        return getByPrimaryKey(key) == null;
    }


    public void edit(Membership membership, String number, String position, Boolean isCaptain) {
        realm.beginTransaction();
        membership.setNumber(Integer.valueOf(number));
        membership.setPosition(VolleyballPosition.toVolleyballPosition(position));
        membership.setCaptain(isCaptain);
        realm.commitTransaction();
    }
}
