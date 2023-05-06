package com.example.myapplication.repositories;

import com.example.myapplication.model.JoinRequest;
import com.example.myapplication.model.Membership;
import com.example.myapplication.model.Player;
import com.example.myapplication.model.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class TeamRepository implements BaseRepository<Team, Long> {

    private JoinRequestRepository joinRequestRepository = new JoinRequestRepository();
    private MembershipRepository membershipRepository = new MembershipRepository();

    @Override
    public Collection<Team> getAll() {
        return realm.where(Team.class).findAll().stream().collect(Collectors.toList());
    }

    @Override
    public Team getByPrimaryKey(Long key) {
        return realm.where(Team.class).equalTo("id", key).findFirst();
    }

    public Team getByName(String name){
        return realm.where(Team.class).equalTo("name",name).findFirst();
    }

    public Collection<Team> getByPlayerIsMember(Player player) {
        Collection<Team> teams = new HashSet<>();
        Collection<Membership> memberships = membershipRepository.getByPlayer(player);
        for(Membership membership : memberships){
            teams.add(membership.getTeam());
        }
        return teams;

    }

    @Override
    public boolean insertOrUpdate(Team item) {
        try {
            realm.beginTransaction();
            realm.insertOrUpdate(item);
            realm.commitTransaction();
            return realm.where(Team.class).equalTo("id", item.getId()).findFirst() != null;
        }catch (Exception e){
            return false;
        }
    }

    public boolean edit(String name, String imageUrl, Team item){
        realm.beginTransaction();
        item.setName(name);
        item.setImage(imageUrl);
        realm.commitTransaction();
        return getByPrimaryKey(item.getId()).getCity().equals(name);
    }

    @Override
    public boolean delete(Team item) {
        return deleteByPrimaryKey(item.getId());
    }

    @Override
    public boolean deleteByPrimaryKey(Long key) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Team team = getByPrimaryKey(key);
                if(team != null){
                    for (Membership m: realm.where(Membership.class).equalTo("team.id", key).findAll()){
                        membershipRepository.delete(m);
                    }
                    for (JoinRequest j: realm.where(JoinRequest.class).equalTo("team.id", key).findAll()){
                        joinRequestRepository.delete(j);
                    }
                    team.deleteFromRealm();
                }
            }
        });
        return getByPrimaryKey(key) == null;
    }


}
