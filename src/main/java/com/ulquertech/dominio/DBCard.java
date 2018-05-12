package com.ulquertech.dominio;

import java.sql.ResultSet;

public class DBCard {

    private int id;
    private int level;
    private int race;
    private int atk;
    private int def;
    private int attribute;
    private int type;
    private String name;
    private String desc;

    public DBCard(ResultSet rs) {
        try {
            type = rs.getInt("type");
            id = rs.getInt("id");
            attribute = rs.getInt("attribute");
            name = rs.getString("name");
            level = rs.getInt("level");
            race = rs.getInt("race");
            atk = rs.getInt("atk");
            def = rs.getInt("def");
            desc = rs.getString("desc");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
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

    public int getRace() {
        return race;
    }

    public void setRace(int race) {
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

    public int getAttribute() {
        return attribute;
    }

    public void setAttribute(int attribute) {
        this.attribute = attribute;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
