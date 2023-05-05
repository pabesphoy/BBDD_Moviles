package com.example.myapplication.services;

import android.util.Log;

import com.example.myapplication.model.Coach;
import com.example.myapplication.model.Player;
import com.example.myapplication.model.Practice;
import com.example.myapplication.model.Team;
import com.example.myapplication.repositories.PracticeRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PracticeService {

    PracticeRepository repository = new PracticeRepository();

    public List<Practice> getAll(){
        return new ArrayList<>(repository.getAll());
    }

    public Practice getById(Long id){
        return repository.getByPrimaryKey(id);
    }

    public List<Practice> getByPlayer(Player player, boolean seePastPractices){
        return seePastPractices ? repository.getAllPracticesByPlayer(player) : repository.getFuturePracticesByPlayer(player);
    }

    public List<Practice> getByCoach(Coach coach, boolean seePastPractices, Team teamFilter){
        return seePastPractices ? repository.getAllPracticesByCoach(coach, teamFilter) : repository.getFuturePracticesByCoach(coach, teamFilter);
    }



    public void insertOrUpdate(Practice practice){
        if (!repository.insertOrUpdate(practice))
            Log.e("ERROR", "Error al insertar practica");
    }

    public boolean edit(String place, Date date, Practice item) {
        return repository.edit(place, date, item);
    }

    public void delete(Practice practice){
        if (!repository.delete(practice))
            Log.e("ERROR", "Error al borrar practica");
    }

    public void deleteById(Long id){
        if (!repository.deleteByPrimaryKey(id))
            Log.e("ERROR", "Error al borrar practica");
    }
}
