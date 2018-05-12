package com.ulquertech.dominio;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class PuzzleCardBuilder implements Serializable {
    @Inject
    private CartasService cartasService;
    private List<PuzzleCard> puzzleCards = new ArrayList<>();
    private Map<Integer, Boolean> myM = new HashMap<>();
    private Map<Integer, Boolean> myS = new HashMap<>();
    private Map<Integer, Boolean> myP = new HashMap<>();
    private Map<Integer, Boolean> opM = new HashMap<>();
    private Map<Integer, Boolean> opS = new HashMap<>();
    private Map<Integer, Boolean> opP = new HashMap<>();
    private Integer masterRule = 3;

    public int getMasterRule() {
        return masterRule;
    }

    public void setMasterRule(Integer masterRule) {
        this.masterRule = masterRule;
    }

    public PuzzleCardBuilder() {
        //inicializa los mapas de zonas de monstruos, hechizos, y pendulo, para los dos usuarios
        //5 zonas de monstruo pricipales, mas 2 extra
        //5 zonas de magia, mas la de campo
        //2 zonas de pendulo
        initMap(myM, 7);
        initMap(myS, 6);
        initMap(myP, 2);
        initMap(opM, 7);
        initMap(opS, 6);
        initMap(opP, 2);
    }

    private void initMap(Map<Integer, Boolean> map, Integer largo) {
        for (int i = 0; i < largo; i++) {
            map.put(i, false);
        }
    }

    private ImageIcon setFieldIcon(Integer id, Integer user, String position, Dimension size) { //TODO: ver de mover esto a la ui
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("POS_FACEUP", 0);
        map.put("POS_FACEDOWN", 3);
        map.put("POS_FACEUP_ATTACK", 0);
        map.put("POS_FACEUP_DEFENCE", 1);
        map.put("POS_FACEDOWN_DEFENCE", 2);
        int pos = map.get(position);

        try {
            BufferedImage field = null;

            if (pos == 0) {
                if (user == 0) {
                    field = ImageIO.read(new File("pics/" + id + ".jpg"));
                    field = Scalr.resize(field, size.height, new BufferedImageOp[0]); //TODO: se podría reemplazar usando una clase de semantic para las imágenes
                } else if (user == 1) {
                    field = ImageIO.read(new File("pics/" + id + ".jpg"));
                    field = Scalr.resize(field, size.height, new BufferedImageOp[0]);
                    field = Scalr.rotate(field, Scalr.Rotation.CW_180, new BufferedImageOp[0]);
                }
            } else if (pos == 1) {
                if (user == 0) {
                    field = ImageIO.read(new File("pics/" + id + ".jpg"));
                    field = Scalr.resize(field, size.height, new BufferedImageOp[0]);
                    field = Scalr.rotate(field, Scalr.Rotation.CW_270, new BufferedImageOp[0]);
                } else if (user == 1) {
                    field = ImageIO.read(new File("pics/" + id + ".jpg"));
                    field = Scalr.resize(field, size.height, new BufferedImageOp[0]);
                    field = Scalr.rotate(field, Scalr.Rotation.CW_90, new BufferedImageOp[0]);
                }
            } else if (pos == 2) {
                if (user == 0) {
                    field = ImageIO.read(getClass().getResource("/fieldPics/cover.jpg"));
                    field = Scalr.resize(field, size.height, new BufferedImageOp[0]);
                    field = Scalr.rotate(field, Scalr.Rotation.CW_270, new BufferedImageOp[0]);
                } else if (user == 1) {
                    field = ImageIO.read(getClass().getResource("/fieldPics/cover.jpg"));
                    field = Scalr.resize(field, size.height, new BufferedImageOp[0]);
                    field = Scalr.rotate(field, Scalr.Rotation.CW_90, new BufferedImageOp[0]);
                }
            } else if (pos == 3) {
                if (user == 0) {
                    field = ImageIO.read(getClass().getResource("/fieldPics/cover.jpg"));
                    field = Scalr.resize(field, size.height, new BufferedImageOp[0]);
                } else if (user == 1) {
                    field = ImageIO.read(getClass().getResource("/fieldPics/cover.jpg"));
                    field = Scalr.resize(field, size.height, new BufferedImageOp[0]);
                    field = Scalr.rotate(field, Scalr.Rotation.CW_180, new BufferedImageOp[0]);
                }
            }

            return new ImageIcon(field); //TODO: Quitar swing
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return null;
    }

    public void addCard(PuzzleCard card, Integer masterRule, StringBuilder stringBuilder) {
        switch (card.getLocation()) {
            case "LOCATION_MZONE":
            case "LOCATION_SZONE":
                addToMonsterSpellZone(card, stringBuilder);
                break;
            case "LOCATION_PZONE":
                addToPendulumZONE(card, stringBuilder, masterRule);
                break;
            case "LOCATION_EXTRAM":
                addToExtraMonsterZone(card, stringBuilder, masterRule);
                break;
            case "LOCATION_FSPELL":
                addToFieldSpellZone(card, stringBuilder);
                break;
            case "LOCATION_OVERLAY":
                addToOverlay(card, stringBuilder);
                break;
            default:
                card.setZone(0);
                puzzleCards.add(card);
                break;
        }
    }

    private PuzzleCard createPuzzleCard(Integer id, Integer user, Integer zone, String location, String position) {
        return new PuzzleCard(id, user, zone, location, position);
    }

    public void removeCard(PuzzleCard card) {
        PuzzleCard cardToRemove = null;
        String actualLocation;
        switch (card.getLocation()) {
            case "LOCATION_EXTRAM":
            case "LOCATION_OVERLAY":
                actualLocation = "LOCATION_MZONE";
                break;
            case "LOCATION_FSPELL":
                actualLocation = "LOCATION_SZONE";
                break;
            default:
                actualLocation = card.getLocation();
        }

        //recupera la puzzleCard de la lista
        for (PuzzleCard puzzleCard : puzzleCards) {
            if (puzzleCard.getId() == card.getId() && puzzleCard.getUser() == card.getUser() && puzzleCard.getZone() == card.getZone() && puzzleCard.getLocation().equals(actualLocation) && puzzleCard.getPosition().equals(card.getPosition())) {
                cardToRemove = puzzleCard;
            }
        }

        Card carta = cartasService.getCartaByID(cardToRemove.getId());
        //si mi carta a remover es un XYZ, en la zona de monstruos, y no es una overlay unit
        if (carta.getType().contains("XYZ") && cardToRemove.getLocation().equals("LOCATION_MZONE") && !cardToRemove.getPosition().equals("POS_FACEUP")) {
            //revisa si tiene overlay units y las remueve
            for (PuzzleCard puzzleCard : puzzleCards) {
                //es una overlay unit de mi carta, si pertenece al mismo user, zone, location, y es una overlay unit
                if (puzzleCard.getUser() == card.getUser() && puzzleCard.getZone() == card.getZone() && puzzleCard.getLocation().equals(actualLocation) && puzzleCard.getPosition().equals("POS_FACEUP")) {
                    puzzleCards.remove(puzzleCard);
                }
            }
        }
        //remueve la carta en cuestion
        puzzleCards.remove(cardToRemove);
        Map<Integer, Boolean> map = getMapa(card.getUser(), actualLocation);
        if (map != null) map.put(card.getZone(), false);
    }

    private void addToMonsterSpellZone(PuzzleCard card, StringBuilder stringBuilder) {
        Map<Integer, Boolean> map = getMapa(card.getUser(), card.getLocation());

        if (!map.get(card.getZone())) {
            puzzleCards.add(card);
            map.put(card.getZone(), true);
        } else {
            stringBuilder.append("The selected zone is ocupied");
        }
    }

    private void addToPendulumZONE(PuzzleCard card, StringBuilder stringBuilder, Integer masterRule) {
        Map<Integer, Boolean> map;
        Integer secZone;
        if (masterRule == 4) {
            card.setLocation("LOCATION_SZONE");
            secZone = masterRule;
        } else if (masterRule == 3) {
            card.setLocation("LOCATION_PZONE");
            secZone = 2;
        } else {
            stringBuilder.append("Select master rule 3 or higher to play with pendulum zones");
            return;
        }
        map = getMapa(card.getUser(), card.getLocation());

        if (!map.get(0)) {
            card.setZone(0);
        } else if (!map.get(secZone)) {
            card.setZone(secZone);
        } else {
            stringBuilder.append("Both pendulum zones are ocupied");
            return;
        }
//        card.setZone(!map.get(0) ? 0 : (!map.get(secZone) ? secZone : null));

        puzzleCards.add(card);
        map.put(card.getZone(), true);
    }

    private void addToExtraMonsterZone(PuzzleCard card, StringBuilder stringBuilder, Integer masterRule) {
        Map<Integer, Boolean> map;
        if (masterRule == 4) {
            card.setLocation("LOCATION_MZONE");
            map = getMapa(card.getUser(), card.getLocation());
            if (!map.get(card.getZone())) {
                card.setPosition("POS_FACEUP_ATTACK");
                puzzleCards.add(card);
                map.put(card.getZone(), true);
            } else {
                stringBuilder.append("The selected Extra monster zone is ocupied");
            }
        } else {
            stringBuilder.append("Select master rule 4 to play with extra monster zones");
        }
    }

    private void addToFieldSpellZone(PuzzleCard card, StringBuilder stringBuilder) {
        card.setLocation("LOCATION_SZONE");
        Map<Integer, Boolean> map = getMapa(card.getUser(), card.getLocation());
        if (!map.get(5)) {
            card.setZone(5);
            puzzleCards.add(card);
            map.put(card.getZone(), true);
        } else {
            stringBuilder.append("The selected field spell zone is ocupied");
        }
    }

    private void addToOverlay(PuzzleCard card, StringBuilder stringBuilder) {
        card.setLocation("LOCATION_MZONE");
        if (isXYZMonsterIn(card)) {
            card.setPosition("POS_FACEUP");
            puzzleCards.add(card);
        } else {
            stringBuilder.append("There is no XYZ monster in the selected monster zone");
        }
    }

    private Boolean isXYZMonsterIn(PuzzleCard card) {
        Boolean existXYZ = false;
        for (PuzzleCard puzzleCard : puzzleCards) {
            Card carta = cartasService.getCartaByID(puzzleCard.getId());
            if (puzzleCard.getId() == card.getId() && puzzleCard.getUser() == card.getUser() && puzzleCard.getZone() == card.getZone() && puzzleCard.getLocation().equals("LOCATION_MZONE") && !puzzleCard.getPosition().equals("POS_FACEUP") && carta.getType().contains("XYZ")) {
                existXYZ = true;
            }
        }
        return existXYZ;
    }

    private Map<Integer, Boolean> getMapa(Integer user, String location) {
        Map<Integer, Boolean> map = new HashMap<>();
        //selecciona el mapa de la location indicada para ese user
        switch (user) {
            case 0:
                switch (location) {
                    case "LOCATION_MZONE":
                        map = myM;
                        break;
                    case "LOCATION_SZONE":
                        map = myS;
                        break;
                    case "LOCATION_PZONE":
                        map = myP;
                }
                break;
            case 1:
                switch (location) {
                    case "LOCATION_MZONE":
                        map = opM;
                        break;
                    case "LOCATION_SZONE":
                        map = opS;
                        break;
                    case "LOCATION_PZONE":
                        map = opP;
                        break;
                }
                break;
        }
        return map;
    }

    private List<String> adaptToNewMasterRule() {
        List<String> warnings = new ArrayList<>();
        List<PuzzleCard> cardsToCheck = new ArrayList<>(puzzleCards);

        //si la masterRule es menor a 4 y tengo cartas en las extra monster zones, las remuevo del puzzle
        if (masterRule < 4) {
            for (PuzzleCard puzzleCard : cardsToCheck) {
                if (puzzleCard.getLocation().equals("LOCATION_MZONE") && (puzzleCard.getZone() == 5 || puzzleCard.getZone() == 6) && !puzzleCard.getPosition().equals("POS_FACEUP")) {
                    removeCard(puzzleCard);
                }
            }
        }
        //remuevo las scalas de pendulo del puzzle e intento volver a agregar las
        StringBuilder stringBuilder = new StringBuilder();
        for (PuzzleCard puzzleCard : cardsToCheck) {
            Card card = cartasService.getCartaByID(puzzleCard.getId());
            if (card.getType().contains("Pendulum") && (puzzleCard.getLocation().equals("LOCATION_PZONE") || (puzzleCard.getLocation().equals("LOCATION_SZONE") && (puzzleCard.getZone() == 0 || puzzleCard.getZone() == 4)))) {
                removeCard(puzzleCard);
                addToPendulumZONE(puzzleCard,stringBuilder,masterRule);
            }
        }

        if (stringBuilder.length() > 0) {
            warnings.add(stringBuilder.toString()); //TODO: revisar que no esté mezclando cosas
        }

        return warnings;
    }

    private String createPuzzleMethod(DatosPuzzle datosPuzzle) {
        return Lua_creator.createLua(datosPuzzle, puzzleCards);
    }

}
