package com.example.myapplication.bbdd.model;

public enum UserType {
    Player("Player"),
    Coach("Coach");
    String displayName;
    UserType(String displayName) {
        this.displayName = displayName;
    }
}
