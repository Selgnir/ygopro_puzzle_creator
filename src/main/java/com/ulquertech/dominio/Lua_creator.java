package com.ulquertech.dominio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class Lua_creator {
    public Lua_creator() {
    }

    static String createLua(DatosPuzzle datosPuzzle, List<PuzzleCard> puzzleList) {
        String error = "";
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("single/" + datosPuzzle.getPuzzleName() + ".lua"));

            out.write("--" + datosPuzzle.getPuzzleName());
            out.newLine();
            out.write("Debug.SetAIName(" + datosPuzzle.getOponentName() + ")");
            out.newLine();
            out.write("Debug.ReloadFieldBegin(DUEL_ATTACK_FIRST_TURN+DUEL_SIMPLE_AI," + datosPuzzle.getMasterRule() + ")");
            out.newLine();
            out.write("Debug.SetPlayerInfo(0," + datosPuzzle.getUserLP() + ",0,0)");
            out.newLine();
            out.write("Debug.SetPlayerInfo(1," + datosPuzzle.getOponentLP() + ",0,0)");
            out.newLine();

            for (PuzzleCard puzzleCard : puzzleList) {
                out.write("Debug.AddCard(" +
                        puzzleCard.getId() +
                        "," + puzzleCard.getUser() +
                        "," + puzzleCard.getUser() +
                        "," + puzzleCard.getLocation() +
                        "," + puzzleCard.getZone() +
                        "," + puzzleCard.getPosition() +
                        ")");
                out.newLine();
            }

            out.write("Debug.ReloadFieldEnd()");
            out.newLine();
            out.write("Debug.ShowHint(Win this turn!)");
            out.newLine();
            out.write("aux.BeginPuzzle()");

            out.close();
        } catch (IOException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            error = "Puzzle creation abort";
        }
        return error;
    }
}
