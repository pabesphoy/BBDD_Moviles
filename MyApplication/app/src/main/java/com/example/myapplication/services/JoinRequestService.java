package com.example.myapplication.services;

import android.graphics.Paint;
import android.util.Log;

import com.example.myapplication.model.JoinRequest;
import com.example.myapplication.model.Membership;
import com.example.myapplication.model.Player;
import com.example.myapplication.model.Team;
import com.example.myapplication.model.enums.RequestStatus;
import com.example.myapplication.model.enums.VolleyballPosition;
import com.example.myapplication.repositories.JoinRequestRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JoinRequestService {

    JoinRequestRepository repository = new JoinRequestRepository();
    MembershipService membershipService = new MembershipService();

    public List<JoinRequest> getAll(){
        return new ArrayList<>(repository.getAll());
    }

    public JoinRequest getById(Long id){
        return repository.getByPrimaryKey(id);
    }

    public List<JoinRequest> getRequestsByPlayer(Player player){
        return new ArrayList<>(repository.getRequestsByPlayer(player));
    }

    public List<JoinRequest> getRequestsByTeam(Team team){
        return new ArrayList<>(repository.getRequestsByTeam(team));
    }

    public List<JoinRequest> getPendingRequestsByTeam(Team team){
        return new ArrayList<>(repository.getPendingRequestsByTeam(team));
    }

    public boolean getHasPlayerPendingRequestForTeam(Player player, Team team){
        return repository.getHasPlayerPendingRequestForTeam(player, team);
    }

    public void insertOrUpdate(JoinRequest request){
        if(!repository.insertOrUpdate(request))
            Log.e("ERROR", "Error al insertar request");
    }

    public void requestJoinTeam(Player player, Team team){
        JoinRequest request = new JoinRequest(player, team, new Date());
        insertOrUpdate(request);
    }

    public void acceptJoinRequest(JoinRequest request){
        repository.acceptJoinRequest(request);
        membershipService.insertOrUpdate(new Membership(request.getPlayer(), request.getTeam(), 0, VolleyballPosition.toVolleyballPosition(request.getPlayer().getPreferredPosition()), false));

    }

    public void denyJoinRequest(JoinRequest request){
        repository.denyJoinRequest(request);
    }

    public void deleteRequest(JoinRequest request){
        if(!repository.delete(request))
            Log.e("ERROR", "Error al borrar request");
    }

    public void deleteRequestById(Long id){
        if(!repository.deleteByPrimaryKey(id))
            Log.e("ERROR", "Error al borrar request");
    }
}
