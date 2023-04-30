package com.example.myapplication.repositories;

import com.example.myapplication.model.JoinRequest;
import com.example.myapplication.model.Membership;
import com.example.myapplication.model.Team;

import java.util.Collection;
import java.util.stream.Collectors;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class TeamRepository implements BaseRepository<Team, String> {

    private JoinRequestRepository joinRequestRepository = new JoinRequestRepository();
    private MembershipRepository membershipRepository = new MembershipRepository();

    @Override
    public Collection<Team> getAll() {
        return realm.where(Team.class).findAll().stream().collect(Collectors.toList());
    }

    @Override
    public Team getByPrimaryKey(String key) {
        return realm.where(Team.class).equalTo("name", key).findFirst();
    }

    @Override
    public boolean insertOrUpdate(Team item) {
        try {
            realm.beginTransaction();
            realm.insertOrUpdate(item);
            realm.commitTransaction();
            return realm.where(Team.class).equalTo("name", item.getName()).findFirst() != null;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean delete(Team item) {
        return deleteByPrimaryKey(item.getName());
    }

    @Override
    public boolean deleteByPrimaryKey(String key) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Team team = getByPrimaryKey(key);
                if(team != null){
                    for (Membership m: realm.where(Membership.class).equalTo("team.name", key).findAll()){
                        membershipRepository.delete(m);
                    }
                    for (JoinRequest j: realm.where(JoinRequest.class).equalTo("team.name", key).findAll()){
                        joinRequestRepository.delete(j);
                    }
                    team.deleteFromRealm();
                }
            }
        });
        return getByPrimaryKey(key) == null;
    }
}
