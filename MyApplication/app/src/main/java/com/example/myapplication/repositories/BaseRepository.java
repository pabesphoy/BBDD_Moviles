package com.example.myapplication.repositories;

import com.example.myapplication.Utils;

import java.util.Collection;

import io.realm.Realm;

public interface BaseRepository<T, E> {

    Realm realm = Utils.getRealm();

    public Collection<T> getAll();
    public T getByPrimaryKey(E key);
    public boolean insertOrUpdate(T item);
    public boolean delete(T item);
    public boolean deleteByPrimaryKey(E key);

}
