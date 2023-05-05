package com.example.myapplication;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.myapplication.model.AppUser;
import com.example.myapplication.model.Coach;
import com.example.myapplication.model.Practice;
import com.example.myapplication.model.Team;
import com.example.myapplication.services.PracticeService;

import java.sql.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    PracticeService practiceService = new PracticeService();

    @Test
    public void practice_creation_isCorrect() {
        AppUser user = new AppUser("migue@hot.es","Migue","Gomez","05/06/1999");
        Team roche = new Team("Roche","imagen","Sevilla",new Coach());
        Practice p = new Practice("Sevilla", Date.valueOf("05/06/2023"),roche);

        practiceService.insertOrUpdate(p);
    }
}