package com.example.myapplication.repositories;

import com.example.myapplication.model.Coach;
import com.example.myapplication.model.Player;
import com.example.myapplication.model.Team;

import java.util.Collection;
import java.util.stream.Collectors;

import io.realm.Realm;
import io.realm.mongodb.User;

public class CoachRepository implements BaseRepository<Coach, String> {

    TeamRepository teamRepository = new TeamRepository();

    @Override
    public Collection<Coach> getAll() {
        return realm.where(Coach.class).findAll().stream().collect(Collectors.toList());
    }

    @Override
    public Coach getByPrimaryKey(String key) {
        return realm.where(Coach.class).equalTo("user.email", key).findFirst();
    }

    @Override
    public boolean insertOrUpdate(Coach item) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(item);
        realm.commitTransaction();

        return getByPrimaryKey(item.getUser().getEmail()) != null;
    }

    @Override
    public boolean delete(Coach item) {
        return deleteByPrimaryKey(item.getUser().getEmail());
    }

    @Override
    public boolean deleteByPrimaryKey(String key) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Coach coach = getByPrimaryKey(key);
                if(coach != null){
                    for(Team t: realm.where(Team.class).equalTo("coach.user.email", key).findAll()){
                        teamRepository.delete(t);
                    }
                    coach.deleteFromRealm();
                    coach.getUser().deleteFromRealm();
                }

            }
        });
        return getByPrimaryKey(key) == null;
    }
}
