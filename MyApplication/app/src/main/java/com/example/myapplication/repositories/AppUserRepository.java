package com.example.myapplication.repositories;

import com.example.myapplication.model.AppUser;
import com.example.myapplication.model.Coach;
import com.example.myapplication.model.Player;
import com.example.myapplication.services.CoachService;
import com.example.myapplication.services.PlayerService;

import java.util.Collection;

import io.realm.mongodb.App;

public class AppUserRepository implements BaseRepository<AppUser, String> {

    private CoachService coachService = new CoachService();
    private PlayerService playerService = new PlayerService();


    @Override
    public Collection<AppUser> getAll() {
        return realm.where(AppUser.class).findAll();
    }

    @Override
    public AppUser getByPrimaryKey(String key) {
        return realm.where(AppUser.class).equalTo("email", key).findFirst();
    }

    @Override
    public boolean insertOrUpdate(AppUser item) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(item);
        realm.commitTransaction();


        return realm.where(AppUser.class).equalTo("email", item.getEmail()).findFirst() != null;
    }

    public boolean edit(String name, String surname, String birthday, AppUser item) {
        realm.beginTransaction();
        item.setName(name);
        item.setSurname(surname);
        item.setBirthday(birthday);
        realm.commitTransaction();


        return realm.where(AppUser.class).equalTo("email", item.getEmail()).findFirst() != null;
    }

    @Override
    public boolean delete(AppUser item) {
        return deleteByPrimaryKey(item.getEmail());
    }

    @Override
    public boolean deleteByPrimaryKey(String key) {
        realm.beginTransaction();
        for(Coach coach : realm.where(Coach.class).equalTo("user.email", key).findAll()){
            coachService.delete(coach);
        }
        for(Player player : realm.where(Player.class).equalTo("user.email", key).findAll()){
            playerService.delete(player);
        }
        realm.where(AppUser.class).equalTo("user.email", key).findFirst().deleteFromRealm();
        realm.commitTransaction();
        return false;
    }
}
