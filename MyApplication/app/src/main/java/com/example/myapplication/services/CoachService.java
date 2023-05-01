package com.example.myapplication.services;

import android.util.Log;

import com.example.myapplication.model.Coach;
import com.example.myapplication.repositories.CoachRepository;

import java.util.ArrayList;
import java.util.List;

public class CoachService {

    CoachRepository repository = new CoachRepository();

    public List<Coach> getAll(){
        return new ArrayList<>(repository.getAll());
    }

    public Coach getByEmail(String email){
        return repository.getByPrimaryKey(email);
    }

    public void insertOrUpdateCoach(Coach coach){
        if(!repository.insertOrUpdate(coach))
            Log.e("ERROR","Error insertando Coach");
    }

    public void delete(Coach coach){
        if(!repository.delete(coach))
            Log.e("ERROR","Error borrando Coach");
    }

    public void deleteByEmail(String email){
        if(!repository.deleteByPrimaryKey(email))
            Log.e("ERROR","Error borrando Coach");
    }
}
