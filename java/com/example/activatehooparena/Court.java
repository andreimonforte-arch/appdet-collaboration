package com.example.activatehooparena;

public class Court {
    private String id;
    private String name;
    private String location;
    private String imageUrl;
    private double pricePerHour;
    private double rating;
    private boolean isAvailable;
    private String type; // "solo", "half", "whole"

    public Court() {
    }

    public Court(String id, String name, String location, String imageUrl,
                 double pricePerHour, double rating, boolean isAvailable) {
        this(id, name, location, imageUrl, pricePerHour, rating, isAvailable, "solo");
    }

    public Court(String id, String name, String location, String imageUrl,
                 double pricePerHour, double rating, boolean isAvailable, String type) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
        this.pricePerHour = pricePerHour;
        this.rating = rating;
        this.isAvailable = isAvailable;
        this.type = type;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getImageUrl() { return imageUrl; }
    public double getPricePerHour() { return pricePerHour; }
    public double getRating() { return rating; }
    public boolean isAvailable() { return isAvailable; }
    public String getType() { return type; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setPricePerHour(double pricePerHour) { this.pricePerHour = pricePerHour; }
    public void setRating(double rating) { this.rating = rating; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public void setType(String type) { this.type = type; }
}