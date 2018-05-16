package com.ulquertech;

import com.ulquertech.dominio.PuzzleCard;

import java.util.ArrayList;
import java.util.List;

public abstract class CampoPanel extends AbstractPanel {
//    Las cartas que van a tener una imagen visible de primeras, van acá
    private List<PuzzleCard> fieldUs = new ArrayList<>();
    private List<PuzzleCard> fieldOp = new ArrayList<>();
//    Las cartas que van a tener su imagen oculta y van a ser accedidas por medio de un modal para cada lista, van acá
    private List<PuzzleCard> handUs = new ArrayList<>();
    private List<PuzzleCard> handOp = new ArrayList<>();
    private List<PuzzleCard> deckUs = new ArrayList<>();
    private List<PuzzleCard> deckOp = new ArrayList<>();
    private List<PuzzleCard> gyUs = new ArrayList<>();
    private List<PuzzleCard> gyOp = new ArrayList<>();
    private List<PuzzleCard> extraUs = new ArrayList<>();
    private List<PuzzleCard> extraOp = new ArrayList<>();
    private List<PuzzleCard> banUs = new ArrayList<>();
    private List<PuzzleCard> banOp = new ArrayList<>();

    CampoPanel(String id) {
        super(id);
    }

    public abstract void cargar();
}
