package com.example.myapplication.repositories;

import com.example.myapplication.model.JoinRequest;
import com.example.myapplication.model.Membership;
import com.example.myapplication.model.Player;

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
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Membership membership = getByPrimaryKey(key);
                if(membership != null){
                    membership.deleteFromRealm();
                }
            }
        });
        return getByPrimaryKey(key) == null;
    }
}
