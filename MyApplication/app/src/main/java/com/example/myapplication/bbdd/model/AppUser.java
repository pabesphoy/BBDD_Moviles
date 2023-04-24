package com.example.myapplication.bbdd.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;
import io.realm.mongodb.User;

public class AppUser extends RealmObject {

    @PrimaryKey
    private String email;

    private String name;
    private String surname;
    private String birthday;

    @Required
    private String type;

    public String getType(){return type;};

    public void setType(String type){this.type = type;}

    public String getEmail() {return email;}

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public AppUser(){

    }
    public AppUser(String email, String name, String surname, String birthday, String type) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.type = type;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday='" + birthday + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
