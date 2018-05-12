package com.ulquertech.dominio;

import java.util.ArrayList;
import java.util.List;

public class Card {
    private int id;
    private String name;
    private String race = "";
    private String type;
    private String attribute = "";
    private int level;
    private int atk;
    private int def;
    private String desc;
    private List<String> arrows = new ArrayList<>();
    private int scale;
    private boolean monster;

    public List<String> getArrows() {
        return arrows;
    }

    public void setArrows(List<String> arrows) {
        this.arrows = arrows;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isMonster() {
        return monster;
    }

    public void setMonster(boolean monster) {
        this.monster = monster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
