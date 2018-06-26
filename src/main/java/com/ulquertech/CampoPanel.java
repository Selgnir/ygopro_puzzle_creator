package com.ulquertech;

import com.ulquertech.dominio.PuzzleCard;

import java.util.ArrayList;
import java.util.List;

public abstract class CampoPanel extends AbstractPanel {
//    Las cartas que van a tener una imagen visible de primeras, van acá
    List<PuzzleCard> fieldUs = new ArrayList<>();
    List<PuzzleCard> fieldOp = new ArrayList<>();
//    Las cartas que van a tener su imagen oculta y van a ser accedidas por medio de un modal para cada lista, van acá
    List<PuzzleCard> handOp = new ArrayList<>();
    List<PuzzleCard> deckUs = new ArrayList<>();
    List<PuzzleCard> deckOp = new ArrayList<>();
    List<PuzzleCard> handUs = new ArrayList<>();
    List<PuzzleCard> gyOp = new ArrayList<>();
    List<PuzzleCard> extraUs = new ArrayList<>();
    List<PuzzleCard> extraOp = new ArrayList<>();
    List<PuzzleCard> gyUs = new ArrayList<>();
    List<PuzzleCard> banUs = new ArrayList<>();
    List<PuzzleCard> banOp = new ArrayList<>();

    CampoPanel(String id) {
        super(id);
    }

    public abstract void cargar();
}
