package com.ulquertech.dominio;

public class DatosPuzzle {
    private String puzzleName = "My puzzle";
    private String oponentName = "My oponent";
    private Integer userLP = 8000;
    private Integer oponentLP = 8000;
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

    public String getOponentName() {
        return oponentName;
    }

    public void setOponentName(String oponentName) {
        this.oponentName = oponentName;
    }

    public int getUserLP() {
        return userLP;
    }

    public void setUserLP(int userLP) {
        this.userLP = userLP;
    }

    public int getOponentLP() {
        return oponentLP;
    }

    public void setOponentLP(int oponentLP) {
        this.oponentLP = oponentLP;
    }
}
