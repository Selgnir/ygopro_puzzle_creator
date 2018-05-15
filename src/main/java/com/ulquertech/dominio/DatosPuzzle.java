package com.ulquertech.dominio;

public class DatosPuzzle {
    private String puzzleName = "My puzzle";
    private String opponentName = "My opponent";
    private Integer userLP = 8000;
    private Integer opponentLP = 8000;
    private Integer masterRule = 4;

    public Integer getMasterRule() {
        return masterRule;
    }

    public void setMasterRule(Integer masterRule) {
        this.masterRule = masterRule;
    }

    public String getPuzzleName() {
        return puzzleName;
    }

    public void setPuzzleName(String puzzleName) {
        this.puzzleName = puzzleName;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public void setOpponentName(String opponentName) {
        this.opponentName = opponentName;
    }

    public int getUserLP() {
        return userLP;
    }

    public void setUserLP(int userLP) {
        this.userLP = userLP;
    }

    public int getOpponentLP() {
        return opponentLP;
    }

    public void setOpponentLP(int opponentLP) {
        this.opponentLP = opponentLP;
    }
}
