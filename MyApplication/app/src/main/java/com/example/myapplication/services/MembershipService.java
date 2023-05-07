package com.example.myapplication.services;

import android.util.Log;

import com.example.myapplication.model.Membership;
import com.example.myapplication.model.Player;
import com.example.myapplication.model.Team;
import com.example.myapplication.repositories.MembershipRepository;

import java.util.ArrayList;
import java.util.List;

public class MembershipService {

    MembershipRepository repository = new MembershipRepository();

    public List<Membership> getAll(){
        return new ArrayList<>(repository.getAll());
    }

    public Membership getById(Long id){
        return repository.getByPrimaryKey(id);
    }

    public List<Membership> getByPlayer(Player player){return new ArrayList<>(repository.getByPlayer(player));}

    public List<Membership> getByTeam(Team team){return new ArrayList<>(repository.getByTeam(team));}

    public boolean getPlayerInTeam(Player player, Team team){
        return repository.getPlayerInTeam(player, team);
    }

    public void insertOrUpdate(Membership membership){
        if(!repository.insertOrUpdate(membership))
            Log.e("ERROR","Error insertando membresía");
    }


    public void delete(Membership membership){
        if(!repository.delete(membership))
            Log.e("ERROR","Error borrando membresía");
    }

    public void deleteById(Long id){
        if(!repository.deleteByPrimaryKey(id))
            Log.e("ERROR","Error borrando membresía");
    }
}
