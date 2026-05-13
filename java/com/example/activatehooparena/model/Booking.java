package com.example.activatehooparena.model;

public class Booking {
    private String id;
    private String courtName;
    private String date;
    private String time;
    private String status; // "upcoming", "past", "cancelled"

    public Booking(String id, String courtName, String date, String time, String status) {
        this.id = id;
        this.courtName = courtName;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public String getId() { return id; }
    public String getCourtName() { return courtName; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getStatus() { return status; }
}