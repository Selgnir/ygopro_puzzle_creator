package com.ulquertech.dominio;

public class PuzzleCard {
    private Integer id;
    private Integer user;
    private Integer zone;
    private String location;
    private String position;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public Integer getZone() {
        return zone;
    }

    public void setZone(Integer zone) {
        this.zone = zone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public PuzzleCard(Integer id, Integer user, Integer zone, String location, String position) {
        this.id = id;
        this.user = user;
        this.zone = zone;
        this.location = location;
        this.position = position;
    }

    public PuzzleCard() {}
}
