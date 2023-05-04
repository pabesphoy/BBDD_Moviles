package com.example.myapplication.repositories;

import com.example.myapplication.model.Coach;
import com.example.myapplication.model.Player;
import com.example.myapplication.model.Practice;
import com.example.myapplication.model.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.Realm;

public class PracticeRepository implements BaseRepository<Practice, Long> {

    TeamRepository teamRepository = new TeamRepository();
    @Override
    public Collection<Practice> getAll() {
        return realm.where(Practice.class).findAll();
    }

    @Override
    public Practice getByPrimaryKey(Long key) {
        return realm.where(Practice.class).equalTo("id", key).findFirst();
    }

    public List<Practice> getAllPracticesByTeam(Team team){
        return realm.where(Practice.class).equalTo("team.id", team.getId()).findAll();
    }

    public List<Practice> getFuturePracticesByTeam(Team team){
        return realm.where(Practice.class)
                .equalTo("team.id", team.getId())
                .greaterThanOrEqualTo("date", new Date())
                .findAll();
    }

    public List<Practice> getAllPracticesByPlayer(Player player) {
        Set<Practice> resSet = new HashSet<>();
        List<Team> playerTeams = new ArrayList<>(teamRepository.getByPlayerIsMember(player));
        for(Team team: playerTeams){
            resSet.addAll(getAllPracticesByTeam(team));
        }
        List<Practice> res = new ArrayList<>(resSet);
        res.sort(Comparator.naturalOrder());
        return res;
    }

    public List<Practice> getFuturePracticesByPlayer(Player player) {
        Set<Practice> resSet = new HashSet<>();
        List<Team> playerTeams = new ArrayList<>(teamRepository.getByPlayerIsMember(player));
        for(Team team: playerTeams){
            resSet.addAll(getFuturePracticesByTeam(team));
        }
        List<Practice> res = new ArrayList<>(resSet);
        res.sort(Comparator.naturalOrder());
        return res;
    }

    public List<Practice> getAllPracticesByCoach(Coach coach, Team teamFilter) {
        Set<Practice> resSet = new HashSet<>();
        List<Team> teams = (teamFilter == null) ? coach.getTeams() : List.of(teamFilter);
        for(Team team : teams){
            resSet.addAll(getAllPracticesByTeam(team));
        }
        List<Practice> res = new ArrayList<>(resSet);
        res.sort(Comparator.naturalOrder());
        return res;
    }

    public List<Practice> getFuturePracticesByCoach(Coach coach, Team teamFilter) {
        Set<Practice> resSet = new HashSet<>();
        List<Team> teams = (teamFilter == null) ? coach.getTeams() : List.of(teamFilter);
        for(Team team : teams){
            resSet.addAll(getFuturePracticesByTeam(team));
        }
        List<Practice> res = new ArrayList<>(resSet);
        res.sort(Comparator.naturalOrder());
        return res;
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
