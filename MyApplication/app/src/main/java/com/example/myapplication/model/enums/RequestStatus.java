package com.example.myapplication.model.enums;

public enum RequestStatus {
    PENDING("Pending"),
    ACCEPTED("Accepted"),
    DENIED("Denied");
    private String status;
    RequestStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
