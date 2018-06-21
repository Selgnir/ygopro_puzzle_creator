package com.ulquertech.dominio;

import com.ulquertech.BusquedaFiltroData;

import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

@ApplicationScoped
public class CartasService implements Serializable {
    private static Connection c;
    private static Statement stmnt;

    public CartasService() {
        stmnt = null;
        c = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:cards.cdb");
        } catch (Exception e) { //TODO: que lo levante el notifier con mensaje 'File cards.cdb not found'
            System.err.println(e.getClass().getName() + ": File cards.cdb not found. " + e.getMessage());
        }
    }

    public List<Card> getCartasByExample(BusquedaFiltroData busquedaFiltroData) { //TODO: hacer que sea una búsqueda dinámica a partir de los 2 caracteres
        //CartasService cartas = new CartasService();
        String text = "select d.id, t.name, d.level, d.race, d.atk, d.def, t.desc, d.attribute, d.type " +
                "from texts as t, datas as d " +
                "where (t.name like '%" +
                busquedaFiltroData.getCriterio() + "%' or t.desc like '%" + busquedaFiltroData.getCriterio() + "%') " +
                "and t.id = d.id " +
                "order by t.name";

        return doQuery(text);
    }

    public Card getCartaByID(int cardId) {
        //CartasService cartas = new CartasService();
        String text = "select d.id, t.name, d.level, d.race, d.atk, d.def, t.desc, d.attribute, d.type " +
                "from texts as t, datas as d " +
                "where d.id = " + cardId +
                "and t.id = d.id ";
        return doQuery(text).get(0);
    }

    private List<Card> doQuery(String text) {
        List<Card> cards = new ArrayList<>();
        try {
            stmnt = c.createStatement();
            ResultSet rs = stmnt.executeQuery(text);
            while (rs.next()) {
                Card card = toCard(new DBCard(rs));
                cards.add(card);
            }
            rs.close();
            stmnt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return cards;
    }

    private Card toCard(DBCard dbCard) {
        Card card = new Card();
        card.setId(dbCard.getId());
        card.setName(dbCard.getName());
        card.setDesc(dbCard.getDesc());
        cardType(dbCard.getType(), card);
        if (card.isMonster()) {
            card.setAtk(dbCard.getAtk());
            card.setRace(monsterRace(dbCard.getRace()));
            card.setAttribute(monsterAtt(dbCard.getAttribute()));
            monsterDef(dbCard.getDef(), card);
            monsterLevel(dbCard.getLevel(), card);
        }

        return card;
    }

    private void cardType(int type, Card card) {
        switch (type) {
            case 2:
                card.setType("[Spell]");
                break;
            case 4:
                card.setType("[Trap]");
                break;
            case 17:
                card.setType("[Normal Monster]");
                card.setMonster(true);
                break;
            case 33:
                card.setType("[Effect Monster]");
                card.setMonster(true);
                break;
            case 65:
                card.setType("[Fusion Monster]");
                card.setMonster(true);
                break;
            case 97:
                card.setType("[Fusion|Effect]");
                card.setMonster(true);
                break;
            case 129:
                card.setType("[Ritual Monster]");
                card.setMonster(true);
                break;
            case 130:
                card.setType("[Spell|Ritual]");
                break;
            case 161:
                card.setType("[Ritual|Effect]");
                card.setMonster(true);
                break;
            case 545:
                card.setType("[Spirit Monster]");
                card.setMonster(true);
                break;
            case 1057:
                card.setType("[Union Monster]");
                card.setMonster(true);
                break;
            case 2081:
                card.setType("[Gemini Monster]");
                card.setMonster(true);
                break;
            case 4113:
                card.setType("[Tuner|Normal]");
                card.setMonster(true);
                break;
            case 4129:
                card.setType("[Tuner|Effect]");
                card.setMonster(true);
                break;
            case 8193:
                card.setType("[Synchro Monster]");
                card.setMonster(true);
                break;
            case 8225:
                card.setType("[Synchro|Effect]");
                card.setMonster(true);
                break;
            case 12321:
                card.setType("[Synchro|Tuner|Effect]");
                card.setMonster(true);
                break;
            case 16401:
                card.setType("[Token]");
                card.setMonster(true);
                break;
            case 65538:
                card.setType("[Spell|Quick-Play]");
                break;
            case 131074:
                card.setType("[Spell|Continuous]");
                break;
            case 131076:
                card.setType("[Trap|Continuous]");
                break;
            case 262146:
                card.setType("[Spell|Equip]");
                break;
            case 524290:
                card.setType("[Spell|Field]");
                break;
            case 1048580:
                card.setType("[Trap|Counter]");
                break;
            case 2097185:
                card.setType("[Flip Effect Monster]");
                card.setMonster(true);
                break;
            case 4194337:
                card.setType("[Toon Monster]");
                card.setMonster(true);
                break;
            case 8388609:
                card.setType("[Xyz Monster]");
                card.setMonster(true);
                break;
            case 8388641:
                card.setType("[Xyz|Effect]");
                card.setMonster(true);
                break;
            case 16777233:
                card.setType("[Normal|Pendulum]");
                card.setMonster(true);
                break;
            case 16777249:
                card.setType("[Effect|Pendulum]");
                card.setMonster(true);
                break;
            case 16777280:
                card.setType("[Fusion|Pendulum]");
                card.setMonster(true);
                break;
            case 16777312:
                card.setType("[Fusion|Effect|Pendulum]");
                card.setMonster(true);
                break;
            case 16777344:
                card.setType("[Ritual|Pendulum]");
                card.setMonster(true);
                break;
            case 16777472:
                card.setType("[Ritual|Effect|Pendulum]");
                card.setMonster(true);
                break;
            case 16781329:
                card.setType("[Normal|Tuner|Pendulum]");
                card.setMonster(true);
                break;
            case 16781345:
                card.setType("[Effect|Tuner|Pendulum]");
                card.setMonster(true);
                break;
            case 16781376:
                card.setType("[Fusion|Effect|Tuner|Pendulum]");
                card.setMonster(true);
                break;
            case 16781408:
                card.setType("[Fusion|Tuner|Effect|Pendulum]");
                card.setMonster(true);
                break;
            case 16781568:
                card.setType("[Ritual|Tuner|Effect|Pendulum]");
                card.setMonster(true);
                break;
            case 16785408:
                card.setType("[Synchro|Pendulum]");
                card.setMonster(true);
                break;
            case 16785441:
                card.setType("[Synchro|Effect|Pendulum]");
                card.setMonster(true);
                break;
            case 16789504:
                card.setType("[Synchro|Tuner|Pendulum]");
                card.setMonster(true);
                break;
            case 16789536:
                card.setType("[Synchro|Effect|Tuner|Pendulum]");
                card.setMonster(true);
                break;
            case 25165825:
                card.setType("[Xyz|Pendulum]");
                card.setMonster(true);
                break;
            case 25165857:
                card.setType("[Xyz|Pendulum|Effect]");
                card.setMonster(true);
                break;
            case 67108865:
                card.setType("|Link|Normal]");
                card.setMonster(true);
                break;
            case 67108897:
                card.setType("[Link|Effect]");
                card.setMonster(true);
                break;
            default:
                card.setType("[Unknown Card Type]");
        }
    }

    private String monsterRace(int race) {
        String mType;
        Map<Integer, String> mTypes = new HashMap<>();
        mTypes.put(1, "Warrior");
        mTypes.put(2, "Spellcaster");
        mTypes.put(4, "Fairy");
        mTypes.put(8, "Fiend");
        mTypes.put(16, "Zombie");
        mTypes.put(32, "Machine");
        mTypes.put(64, "Aqua");
        mTypes.put(128, "Pyro");
        mTypes.put(256, "Rock");
        mTypes.put(512, "Winged-Beast");
        mTypes.put(1024, "Plant");
        mTypes.put(2048, "Insect");
        mTypes.put(4096, "Thunder");
        mTypes.put(8192, "Dragon");
        mTypes.put(16384, "Beast");
        mTypes.put(32768, "Beast-Warrior");
        mTypes.put(65536, "Dinosaur");
        mTypes.put(131072, "Fish");
        mTypes.put(262144, "Sea Serpent");
        mTypes.put(524288, "Reptile");
        mTypes.put(1048576, "Psychic");
        mTypes.put(2097152, "Divine-Beast");
        mTypes.put(4194304, "Creator God");
        mTypes.put(8388608, "Wyrm/Genryu");
        mTypes.put(16777216, "Cyverse");

        mType = mTypes.get(race);
        if (mType == null || mType.isEmpty()) {
            mType = "No monster Card";
        }

        return mType;
    }

    private String monsterAtt(int attribute) {
        String mAtt;
        switch (attribute) {
            case 1:
                mAtt = "Earth";
                break;
            case 2:
                mAtt = "Water";
                break;
            case 4:
                mAtt = "Fire";
                break;
            case 8:
                mAtt = "Wind";
                break;
            case 16:
                mAtt = "Light";
                break;
            case 32:
                mAtt = "Dark";
                break;
            case 64:
                mAtt = "Divine";
                break;
            default:
                mAtt = "No monster Card";
        }

        return mAtt;
    }

    private void monsterDef(int def, Card card) {
        if (card.getType().contains("Link")) {
            card.setArrows(linkMarker(def));
        } else {
            card.setDef(def);
        }
    }

    private void monsterLevel(int level, Card card) {
        if (card.getType().contains("Pendulum")) {
            levelAndScaleOf(level, card);
        } else {
            card.setLevel(level);
        }
    }

    private List<String> linkMarker(Integer arrows) {
        List<String> links = new ArrayList<>();
        List<String> orientations = Arrays.asList("N", "NE", "E", "NW", "W", "SE", "S", "SW");
        List<Integer> markers = Arrays.asList(128, 64, 32, 16, 8, 4, 2, 1);
        for (int i = 0; i < markers.size(); i++) {
            if (arrows >= markers.get(i)) {
                links.add(orientations.get(i));
                arrows -= markers.get(i);
            }
        }
        return links;
    }

    private void levelAndScaleOf(int level, Card card) {
        String hexScaleLevel = Integer.toHexString(level);
        String hexScale = hexScaleLevel.substring(0, 2);
        String hexLevel = hexScaleLevel.substring(4, 6);
        card.setLevel(Integer.parseInt(hexLevel, 16));
        card.setScale(Integer.parseInt(hexScale, 16));
    }
}
