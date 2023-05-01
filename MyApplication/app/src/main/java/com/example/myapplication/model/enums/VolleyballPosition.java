package com.example.myapplication.model.enums;

public enum VolleyballPosition {
    MIDDLE("Middle"),
    OPPOSITE_HITTER("Opposite Hitter"),
    OUTSIDE_HITTER("Outside Hitter"),
    LIBERO("Libero"),
    SETTER("Setter"),
    SEVERAL("Several");

    private String position;
    VolleyballPosition(String position) {
        this.position = position;
    }
    public String getPosition() {
        return position;
    }
}
