package com.example.myapplication.repositories;

import com.example.myapplication.model.JoinRequest;

import java.util.Collection;

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
}
