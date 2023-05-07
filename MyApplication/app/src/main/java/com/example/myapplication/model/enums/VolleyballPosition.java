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

    public static VolleyballPosition toVolleyballPosition(String position){
        switch (position){
            case "Middle":return VolleyballPosition.MIDDLE;
            case "Opposite Hitter":return VolleyballPosition.OPPOSITE_HITTER;
            case "Outside Hitter":return VolleyballPosition.OUTSIDE_HITTER;
            case "Libero":return VolleyballPosition.LIBERO;
            case "Setter":return VolleyballPosition.SETTER;
            default: return VolleyballPosition.SEVERAL;
        }
    }
}
