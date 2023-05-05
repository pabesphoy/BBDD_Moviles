package com.example.myapplication.services;

import android.util.Log;

import com.example.myapplication.model.Player;
import com.example.myapplication.repositories.PlayerRepository;

public class PlayerService {

    PlayerRepository playerRepository = new PlayerRepository();
    public void delete(Player player) {
        if(!playerRepository.delete(player))
            Log.e("ERROR", "Error borrando jugador");
    }

    public void deleteByPrimaryKey(String email){
        if(!playerRepository.deleteByPrimaryKey(email))
            Log.e("ERROR", "Error borrando jugador");
    }

    public void insertOrUpdate(Player currentPlayer) {
        if(!playerRepository.insertOrUpdate(currentPlayer))
            Log.e("ERROR", "Error insertando jugador");
    }

    public void editPosition(Player player, String newPos){
        playerRepository.edit(player, newPos);
    }
}
