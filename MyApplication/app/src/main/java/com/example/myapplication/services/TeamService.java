package com.example.myapplication.services;

import android.util.Log;

import com.example.myapplication.model.Coach;
import com.example.myapplication.model.Player;
import com.example.myapplication.model.Team;
import com.example.myapplication.repositories.TeamRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeamService {

    TeamRepository repository = new TeamRepository();

    public List<Team> getAll(){
        return new ArrayList<>(repository.getAll());
    }

    public Team getById(Long id){
        return repository.getByPrimaryKey(id);
    }

    public Team getByName(String name) { return repository.getByName(name); }
    public List<Team> getByCoach(Coach coach){
        return coach.getTeams();
    }

    public List<Team> getByPlayerIsMember(Player player){
        return new ArrayList<>(repository.getByPlayerIsMember(player));
    }

    public void insertOrUpdate(Team team){
        if(!repository.insertOrUpdate(team))
            Log.e("ERROR", "Error insertando team");
    }

    public void delete(Team team){
        if(!repository.delete(team))
            Log.e("ERROR", "Error borrando team");
    }

    public void deleteByName(Long id){
        if(!repository.deleteByPrimaryKey(id))
            Log.e("ERROR", "Error borrando team");
    }

    public List<Team> searchByName(String search){
        return getAll().stream().filter(team -> team.getName().toLowerCase().trim().contains(search.toLowerCase().trim()))
                .collect(Collectors.toList());
    }
}
