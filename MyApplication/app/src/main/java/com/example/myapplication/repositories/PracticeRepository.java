package com.example.myapplication.repositories;

import com.example.myapplication.model.Player;
import com.example.myapplication.model.Practice;

import java.util.Collection;
import java.util.stream.Collectors;

import io.realm.Realm;

public class PracticeRepository implements BaseRepository<Practice, Long> {
    @Override
    public Collection<Practice> getAll() {
        return realm.where(Practice.class).findAll().stream().collect(Collectors.toList());
    }

    @Override
    public Practice getByPrimaryKey(Long key) {
        return realm.where(Practice.class).equalTo("id", key).findFirst();
    }

    @Override
    public boolean insertOrUpdate(Practice item) {
        realm.beginTransaction();
        realm.insertOrUpdate(item);
        realm.commitTransaction();
        return getByPrimaryKey(item.getId()) != null;
    }

    @Override
    public boolean delete(Practice item) {
        return deleteByPrimaryKey(item.getId());
    }

    @Override
    public boolean deleteByPrimaryKey(Long key) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Practice practice = getByPrimaryKey(key);
                if(practice != null){
                    practice.deleteFromRealm();
                }
            }
        });
        return getByPrimaryKey(key) == null;
    }
}
